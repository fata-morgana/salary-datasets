package org.kopkaj.salarydataset.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

// Support only sensible cases. Anyone is free to add functionalities to this class
public class SalaryDatasetFieldHelper {
    private static final Logger logger = LoggerFactory.getLogger(SalaryDatasetFieldHelper.class);
    public static Timestamp formatTimestamp(final String timestampStr) {
        if (timestampStr == null) {
            return null;
        }
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy HH:mm");
        try {
            return new Timestamp(dateFormat.parse(timestampStr).getTime());
        } catch (ParseException e) {
            logger.error("Unknown date format:"  + timestampStr);
            throw new RuntimeException(e);
        }
    }

    public static BigDecimal convertToYear(final String yearStr) {
        if (yearStr == null || yearStr.isEmpty()) {
            return new BigDecimal("0");
        }
        String yearStrValid = yearStr.trim().
                replace(",", ".");
        BigDecimal yearAdjustment = new BigDecimal("0.0");
        if (yearStrValid.startsWith("<")) {
            yearStrValid = yearStrValid.substring(1, yearStrValid.length());
            yearAdjustment = new BigDecimal("-0.5");
        }
        else if (yearStrValid.startsWith(">")) {
            yearStrValid = yearStrValid.substring(1, yearStrValid.length());
            yearAdjustment = new BigDecimal("0.5");
        }
        return new BigDecimal(yearStrValid).add(yearAdjustment);
    }

    public static BigDecimal convertMoneyField(final String moneyStr) {
        if (moneyStr == null || moneyStr.isEmpty()) {
            return new BigDecimal("0");
        }
        String parsedValue =  moneyStr.replaceAll("[^0-9\\.]", "");
        return new BigDecimal(parsedValue);
    }
}
