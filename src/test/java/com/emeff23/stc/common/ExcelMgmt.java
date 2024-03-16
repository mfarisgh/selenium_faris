package com.emeff23.stc.common;

import org.apache.logging.log4j.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.math.BigDecimal;

public class ExcelMgmt {

    private static final Logger LogThis = LogManager.getLogger(ExcelMgmt.class.getName());
    static String PROJ_PATH = System.getProperty("user.dir");
    static DateFormat EXCEL_DISP_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    //Create or Save to Excel
    public static void createSaveExcel(String excelFileName, String sheetName, String[] multiColumnHeader, String[] multiColumnData) throws IOException, InvalidFormatException {

        String filePath = PROJ_PATH + "/" + excelFileName;
        System.out.println("filePath = " + filePath);

        File file = new File(filePath);
        XSSFWorkbook excelFile;
        XSSFSheet sheet;
        if (file.exists()) {
            //existingFile.delete()
            System.out.println("Using existing file: " + file.getName());
            excelFile = new XSSFWorkbook(file);

            // Check if the sheet already exists
            boolean sheetExists = excelFile.getSheet(sheetName) != null;
            // If the sheet exists, use it
            if (sheetExists) {
                sheet = excelFile.getSheet(sheetName);
            } else {
                // Otherwise, create a new sheet
                sheet = excelFile.createSheet(sheetName);
                XSSFRow headerRow = sheet.createRow(0);
                int headerColCount = 0;
                for(String singleHeaderCol : multiColumnHeader) {
                    XSSFCell headerCell = headerRow.createCell(headerColCount++);
                    headerCell.setCellValue(singleHeaderCol);
                }
            }
        }
        else {
            System.out.println("Creating new file.");
            excelFile = new XSSFWorkbook();

            sheet = excelFile.createSheet(sheetName);
            XSSFRow headerRow = sheet.createRow(0);
            int headerColCount = 0;
            for(String singleHeaderCol : multiColumnHeader) {
                XSSFCell headerCell = headerRow.createCell(headerColCount++);
                headerCell.setCellValue(singleHeaderCol);
            }
        }

        // Get the last row number that contains existing data
        int lastRowNum = sheet.getLastRowNum();
        XSSFRow newDataRow = sheet.createRow(lastRowNum + 1);
        int dataColCount = 0;
        for(String singleColumnData : multiColumnData) {
            XSSFCell singleColumnCell = newDataRow.createCell(dataColCount++);
            singleColumnCell.setCellValue(singleColumnData);
        }

        FileOutputStream fileOutputStream;
        if (file.exists()) {
            fileOutputStream = new FileOutputStream(file, true);
            System.out.println("Excel file " + excelFileName + " is updated !");
        }
        else {
            fileOutputStream = new FileOutputStream(file);
            System.out.println("Excel file " + excelFileName + " is created !");
        }
        excelFile.write(fileOutputStream);
        excelFile.close();
        fileOutputStream.close();
    }

    //Find Test Data From Excel
    public static String findTestDataFromExcel(String file, String sheetName, int colNum, int rowNum) throws Exception {

        String dataResult = "";

        String filePath = PROJ_PATH + "/" + file;
        System.out.println("filePath = " + filePath);

        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            int sheetNumber = workbook.getSheetIndex(sheetName);
            System.out.println("sheetName = " + sheetName);
            XSSFSheet sheet = workbook.getSheetAt(sheetNumber);
            System.out.println("sheetNumber = " + sheetNumber);

            Row row = sheet.getRow(rowNum);
            System.out.println("rowNum = " + rowNum);

            try {
                // Check if the row and cell exist
                if (row != null) {
                    Cell cell = row.getCell(colNum);
                    System.out.println("colNum = " + colNum);

                    try {
                        // Check if the cell exists
                        if (cell != null) {
                            // Determine the cell type and get the cell value
                            switch (cell.getCellType()) {
                                case STRING:
                                    dataResult = cell.getStringCellValue();
                                    System.out.println("String value = " + dataResult);
                                    break;
                                case NUMERIC:
                                    if (DateUtil.isCellDateFormatted(cell)) {
                                        dataResult = String.valueOf(new SimpleDateFormat("dd/MM/yyyy").format(cell.getDateCellValue()));
                                        System.out.println("Date value = " + dataResult);
                                    } else {
                                        String rawDataResult = String.valueOf(new DataFormatter().formatCellValue(cell));
                                        System.out.println("rawDataResult = " + rawDataResult);
                                        if (rawDataResult.contains("E") || rawDataResult.contains("e")) {
                                            dataResult = String.valueOf(new BigDecimal(rawDataResult).intValue());
                                        } else {
                                            dataResult = rawDataResult;
                                        }
                                        System.out.println("Numeric value = " + dataResult);
                                    }
                                    break;
                                case BOOLEAN:
                                    dataResult = String.valueOf(cell.getBooleanCellValue());
                                    System.out.println("Boolean value = " + dataResult);
                                    break;
                                default:
                                    System.out.println("Value is N/A"); // Return an empty string for other cell types
                            }
                        }
                    }
                    catch (Exception ec) {
                        //ec.printStackTrace();
                        System.out.println("Exception ec : " + ec.getMessage());
                    }
                }
            }
            catch (Exception er) {
                //er.printStackTrace();
                System.out.println("Exception er : " + er.getMessage());
            }

            workbook.close();
            fis.close();
        }
        catch (Exception ioe)
        {
            //ioe.printStackTrace();
            System.out.println("Exception ioe : " + ioe.getMessage());
        }
        return dataResult;
    }

    //Find Multi Column Test Data From Excel
    public static List<String> findMultiColumnTestDataFromExcel(String file, String sheetName, int[] multiCols, int rowNum) throws Exception {

        List<String> dataResultList = new ArrayList<>();

        String filePath = PROJ_PATH + "/" + file;
        System.out.println("filePath = " + filePath);

        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            int sheetNumber = workbook.getSheetIndex(sheetName);
            System.out.println("sheetName = " + sheetName);
            XSSFSheet sheet = workbook.getSheetAt(sheetNumber);
            System.out.println("sheetNumber = " + sheetNumber);

            Row row = sheet.getRow(rowNum);
            System.out.println("rowNum = " + rowNum);

            try {
                // Check if the row and cell exist
                if (row != null) {

                    Map<Integer, Exception> multiEc = new LinkedHashMap<>();

                    String dataResult = "";

                    for (int colNum : multiCols) {

                        Cell cell = row.getCell(colNum);
                        System.out.println("colNum : " + colNum);

                        try {
                            // Check if the cell exists
                            if (cell != null) {
                                // Determine the cell type and get the cell value
                                switch (cell.getCellType()) {
                                    case STRING:
                                        dataResult = cell.getStringCellValue();
                                        System.out.println("String value : " + dataResult);
                                        break;
                                    case NUMERIC:
                                        dataResult = String.valueOf(cell.getNumericCellValue());
                                        System.out.println("Numeric value : " + dataResult);
                                        break;
                                    case BOOLEAN:
                                        dataResult = String.valueOf(cell.getBooleanCellValue());
                                        System.out.println("Boolean value : " + dataResult);
                                        break;
                                    default:
                                        System.out.println("Value is N/A"); // Return an empty string for other cell types
                                }
                                dataResultList.add(dataResult);
                            }
                        }
                        catch (Exception ec) {
                            multiEc.put(colNum, ec);
                        }
                    }

                    // Check if any exceptions occurred
                    if (!multiEc.isEmpty()) {
                        // Handle or log the exceptions

                        for (int colNum : multiEc.keySet()) {
                            Exception colEc = multiEc.get(colNum);
                            //colEc.printStackTrace();
                            LogThis.error("Exception ec colNum " + colNum + " : " + colEc.getMessage());
                            System.out.println("Exception ec colNum " + colNum + " : " + colEc.getMessage());
                        }
                    }
                }
            }
            catch (Exception er) {
                //er.printStackTrace();
                System.out.println("Exception er : " + er.getMessage());
            }

            workbook.close();
            fis.close();
        }
        catch (Exception ioe)
        {
            //ioe.printStackTrace();
            System.out.println("Exception ioe : " + ioe.getMessage());
        }

        if (!dataResultList.isEmpty()) {
            return dataResultList;
        } else {
            return null;
        }
    }

    //Write To Test Data In Excel
    public static String writeToTestDataInExcel(String file, String sheetName, int colNum, int rowNum, Object newValue) throws Exception {

        String exceptionString, exceptionECString, exceptionERString, exceptionIOEString;
        exceptionString = exceptionECString = exceptionERString = exceptionIOEString = "";

        String filePath = PROJ_PATH + "/" + file;
        System.out.println("filePath = " + filePath);

        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            int sheetNumber = workbook.getSheetIndex(sheetName);
            System.out.println("sheetName = " + sheetName);
            XSSFSheet sheet = workbook.getSheetAt(sheetNumber);
            System.out.println("sheetNumber = " + sheetNumber);

            Row row = sheet.getRow(rowNum);
            System.out.println("rowNum = " + rowNum);

            try {
                // Check if the row and cell exist
                if (row != null) {
                    Cell cell = row.getCell(colNum);
                    System.out.println("colNum = " + colNum);

                    try {
                        if (newValue != null) {
                            // Check if the cell exists
                            if (cell != null) {
                                String newCellValue;
                                // Determine the cell type and get the cell value
                                switch (cell.getCellType()) {
                                    case STRING:
                                        System.out.println("Current String value : " + cell.getStringCellValue());
                                        if(newValue instanceof String) {
                                            newCellValue = (String) newValue;
                                            System.out.println("New String value : " + newCellValue);
                                            cell.setCellValue(newCellValue);
                                        }
                                        break;
                                    case NUMERIC:
                                        System.out.println("Current Numeric value : " + String.valueOf(cell.getNumericCellValue()));
                                        if(newValue instanceof Integer) {
                                            newCellValue = String.valueOf((Integer) newValue);
                                            System.out.println("New Numeric value : " + newCellValue);
                                            cell.setCellValue(newCellValue);
                                        }
                                        break;
                                    case BOOLEAN:
                                        System.out.println("Current Boolean value : " + String.valueOf(cell.getBooleanCellValue()));
                                        if(newValue instanceof Boolean) {
                                            newCellValue = String.valueOf((Boolean) newValue);
                                            System.out.println("New Boolean value : " + newCellValue);
                                            cell.setCellValue(newCellValue);
                                        }
                                        break;
                                    default:
                                        System.out.println("Current value is N/A"); // Return an empty string for other cell types
                                }
                            }
                        }
                    }
                    catch (Exception ec) {
                        //ec.printStackTrace();
                        exceptionECString = "Exception ec : " + ec.getMessage();
                        System.out.println("exceptionECString = " + exceptionECString);
                    }
                }
            }
            catch (Exception er) {
                //er.printStackTrace();
                exceptionERString = "Exception er : " + er.getMessage();
                System.out.println("exceptionERString = " + exceptionERString);
            }

            FileOutputStream fos = new FileOutputStream(filePath);
            workbook.write(fos);
            fos.close();
            workbook.close();
            fis.close();
        }
        catch (Exception ioe)
        {
            //ioe.printStackTrace();
            exceptionIOEString = "Exception ioe : " + ioe.getMessage();
            System.out.println("exceptionIOEString = " + exceptionIOEString);
        }

        if ((exceptionECString == "") && (exceptionERString == "") && (exceptionIOEString == "")) {
            return exceptionString;
        } else {
            return exceptionECString + " \n" + exceptionERString + " \n" + exceptionIOEString;
        }
    }

    //Write To Multi Column Test Data In Excel
    public static String writeToMultiColumnTestDataInExcel(String file, String sheetName, Map<Integer, Object> multiColValues, int rowNum) throws Exception {

        String exceptionString, exceptionECString, exceptionERString, exceptionIOEString;
        exceptionString = exceptionECString = exceptionERString = exceptionIOEString = "";

        String filePath = PROJ_PATH + "/" + file;
        System.out.println("filePath = " + filePath);

        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            int sheetNumber = workbook.getSheetIndex(sheetName);
            System.out.println("sheetName = " + sheetName);
            XSSFSheet sheet = workbook.getSheetAt(sheetNumber);
            System.out.println("sheetNumber = " + sheetNumber);

            Row row = sheet.getRow(rowNum);
            System.out.println("rowNum = " + rowNum);

            try {
                // Check if the row and cell exist
                if (row != null) {

                    Map<Integer, Exception> multiEc = new LinkedHashMap<>();

                    for (int colNum : multiColValues.keySet()) {

                        Cell cell = row.getCell(colNum);
                        System.out.println("colNum : " + colNum);

                        try {

                            Object newColValue = multiColValues.get(colNum);

                            if (newColValue != null) {
                                // Check if the cell exists
                                if (cell != null) {
                                    String newCellValue;
                                    // Determine the cell type and get the cell value
                                    switch (cell.getCellType()) {
                                        case STRING:
                                            System.out.println("Current String value : " + cell.getStringCellValue());
                                            if(newColValue instanceof String) {
                                                newCellValue = (String) newColValue;
                                                System.out.println("New String value : " + newCellValue);
                                                cell.setCellValue(newCellValue);
                                            }
                                            break;
                                        case NUMERIC:
                                            System.out.println("Current Numeric value : " + String.valueOf(cell.getNumericCellValue()));
                                            if(newColValue instanceof Integer) {
                                                newCellValue = String.valueOf((Integer) newColValue);
                                                System.out.println("New Numeric value : " + newCellValue);
                                                cell.setCellValue(newCellValue);
                                            }
                                            break;
                                        case BOOLEAN:
                                            System.out.println("Current Boolean value : " + String.valueOf(cell.getBooleanCellValue()));
                                            if(newColValue instanceof Boolean) {
                                                newCellValue = String.valueOf((Boolean) newColValue);
                                                System.out.println("New Boolean value : " + newCellValue);
                                                cell.setCellValue(newCellValue);
                                            }
                                            break;
                                        default:
                                            System.out.println("Current value is N/A"); // Return an empty string for other cell types
                                    }
                                }
                            }
                        }
                        catch (Exception ec) {
                            multiEc.put(colNum, ec);
                        }
                    }

                    // Check if any exceptions occurred
                    if (!multiEc.isEmpty()) {
                        // Handle or log the exceptions

                        StringBuilder exceptionECStringBuilder = new StringBuilder();

                        for (int colNum : multiEc.keySet()) {

                            Exception colEc = multiEc.get(colNum);

                            //colEc.printStackTrace();

                            String singleExceptionECString = "Exception ec colNum " + colNum + " : " + colEc.getMessage();
                            exceptionECStringBuilder.append(singleExceptionECString + " \n");
                            System.out.println("singleExceptionECString = " + singleExceptionECString);
                        }
                        exceptionECString = exceptionECStringBuilder.toString();
                    }
                }
            }
            catch (Exception er) {
                //er.printStackTrace();
                exceptionERString = "Exception er : " + er.getMessage();
                System.out.println("exceptionERString = " + exceptionERString);
            }

            FileOutputStream fos = new FileOutputStream(filePath);
            workbook.write(fos);
            fos.close();
            workbook.close();
            fis.close();
        }
        catch (Exception ioe)
        {
            //ioe.printStackTrace();
            exceptionIOEString = "Exception ioe : " + ioe.getMessage();
            System.out.println("exceptionIOEString = " + exceptionIOEString);
        }

        if ((exceptionECString == "") && (exceptionERString == "") && (exceptionIOEString == "")) {
            return exceptionString;
        } else {
            return exceptionECString + " \n" + exceptionERString + " \n" + exceptionIOEString;
        }
    }
}
