package com.emeff23.stc.common;

import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import com.emeff23.stc.pomartifacts.common.WebDriverHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class FileMgmt {

    private static final Logger LogThis = LogManager.getLogger(FileMgmt.class.getName());

    public static Map<byte[], String> saveScreenshot(String screenshotName) throws Exception {

        Map<byte[], String> screenshotObj = new HashMap<>();

        final byte[] screenshot = ((TakesScreenshot) WebDriverHelper.getDriver()).getScreenshotAs(OutputType.BYTES);

        String screenshotPath = CentralVars.ProjectPath + "/img/"
                + DateTimeMgmt.getTodayDateTimeStr(CentralVars.DateTimeFilePattern) + "_"
                + screenshotName + ".png";

        FileMgmt.byteToFile(screenshot, screenshotPath);

        LogThis.info("Saved screenshot as : " + screenshotPath);

        screenshotObj.put(screenshot, screenshotPath);

        return screenshotObj;

    }

    // Method to convert a file to Base64 encoded string
    public static String fileToBase64(File file) {

        String base64String = "";
        try {

            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] fileBytes = new byte[(int) file.length()];
            fileInputStream.read(fileBytes);
            base64String = Base64.getEncoder().encodeToString(fileBytes);
            fileInputStream.close();

        } catch (IOException e) {

            LogThis.error("IOException e = " + e.getMessage());

        }
        return base64String;

    }

    // Method to convert a Base64 encoded string to a file
    public static void base64ToFile(String base64String, String filePath) {

        try {

            byte[] fileBytes = Base64.getDecoder().decode(base64String);
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            fileOutputStream.write(fileBytes);
            fileOutputStream.close();

        } catch (IOException e) {

            LogThis.error("IOException e = " + e.getMessage());

        }

    }

    public static void byteToFile(byte[] bytes, String filePath) {

        FileOutputStream fos = null;
        try {
            File file = new File(filePath);
            fos = new FileOutputStream(file);
            fos.write(bytes);

            LogThis.info("Byte array converted to file successfully at : " + filePath);

        } catch (IOException e) {

            LogThis.error("IOException e = " + e.getMessage());

        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {

                    LogThis.error("IOException e = " + e.getMessage());

                }
            }
        }

    }

}
