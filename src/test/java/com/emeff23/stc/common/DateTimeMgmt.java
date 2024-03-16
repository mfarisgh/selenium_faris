package com.emeff23.stc.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class DateTimeMgmt {

    private static final Logger LogThis = LogManager.getLogger(DateTimeMgmt.class.getName());

    public static String getTodayDateTimeStr(String pattern) {

        try {

            // Get current date and time
            Date currentDate = new Date();

            // Create a SimpleDateFormat object with the specified pattern
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

            // Format the current date and time according to the pattern
            String formattedDateTime = dateFormat.format(currentDate);

            // Print the formatted date and time
            LogThis.debug("Formatted Date and Time: " + formattedDateTime);

            return formattedDateTime;

        } catch (Exception e) {

            LogThis.error("Exception e = " + e.getMessage());

            return "";

        }

    }

}
