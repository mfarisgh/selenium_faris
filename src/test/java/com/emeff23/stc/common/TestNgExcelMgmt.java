package com.emeff23.stc.common;

import java.io.FileInputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestNgExcelMgmt {

    private static final Logger LogThis = LogManager.getLogger(TestNgExcelMgmt.class.getName());

    private static XSSFSheet ExcelWSheet;

    private static XSSFWorkbook ExcelWBook;

    private static XSSFCell Cell;

    private static XSSFRow Row;

    //This method is to set the File path and to open the Excel file, Pass Excel Path and Sheetname as Arguments to this method

    public static void setExcelFile(String Path, String SheetName) {

        try {

            // Open the Excel file

            FileInputStream ExcelFile = new FileInputStream(Path);

            // Access the required test data sheet

            ExcelWBook = new XSSFWorkbook(ExcelFile);

            ExcelWSheet = ExcelWBook.getSheet(SheetName);

        } catch (Exception e) {

            LogThis.error("Exception e = " + e.getMessage());

        }

    }

    public static Object[][] getTableArray(String FilePath, String SheetName, int iTestCaseRow) {

        String[][] tabArray = null;

        try{

            FileInputStream ExcelFile = new FileInputStream(FilePath);

            // Access the required test data sheet

            ExcelWBook = new XSSFWorkbook(ExcelFile);

            ExcelWSheet = ExcelWBook.getSheet(SheetName);

            int startCol = 1;

            int ci=0,cj=0;

            int totalRows = 1;

            int totalCols = 1;

            tabArray=new String[totalRows][totalCols];

            for (int j=startCol;j<=totalCols;j++, cj++)

            {

                tabArray[ci][cj]=getCellData(iTestCaseRow,j);

                LogThis.debug("tabArray = " + tabArray[ci][cj]);

            }

        }

        catch (Exception e)

        {

            LogThis.error("Could not read the Excel sheet");

            LogThis.error("Exception e = " + e.getMessage());

        }

        return (tabArray);

    }

    //This method is to read the test data from the Excel cell, in this we are passing parameters as Row num and Col num

    public static String getCellData(int RowNum, int ColNum) {

        try{

            Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);

            String CellData = Cell.getStringCellValue();

            return CellData;

        } catch (Exception e) {

            LogThis.error("Exception e = " + e.getMessage());

            return "";

        }

    }

    public static String getTestCaseName(String sTestCase) {

        String value = sTestCase;

        try{

            int posi = value.indexOf("@");

            value = value.substring(0, posi);

            posi = value.lastIndexOf(".");

            value = value.substring(posi + 1);

            return value;

        } catch (Exception e) {

            LogThis.error("Exception e = " + e.getMessage());

            return "";

        }

    }

    public static int getRowContains(String sTestCaseName, int colNum) {

        int i;

        try {

            int rowCount = getRowUsed();

            for (i=0; i<rowCount; i++){

                if  (getCellData(i,colNum).equalsIgnoreCase(sTestCaseName)){

                    break;

                }

            }

            return i;

        } catch (Exception e) {

            LogThis.error("Exception e = " + e.getMessage());

            return -1;

        }

    }

    public static int getRowUsed() {

        try{

            int RowCount = ExcelWSheet.getLastRowNum();

            return RowCount;

        } catch (Exception e) {

            LogThis.error("Exception e = " + e.getMessage());

            return -1;

        }

    }

}
