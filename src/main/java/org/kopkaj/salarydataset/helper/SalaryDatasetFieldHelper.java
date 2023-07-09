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
        if (yearStr == null) {
            return new BigDecimal("0");
        }
        String yearStrValid = sanitizeDecimalValue(yearStr);
        if (yearStrValid.isEmpty()) {
            return new BigDecimal("0");
        }
        return adjust(yearStrValid);
    }

    public static BigDecimal convertMoneyField(final String moneyStr) {
        if (moneyStr == null) {
            return new BigDecimal("0");
        }
        String moneyStrValid =  sanitizeDecimalValue(moneyStr);
        if (moneyStrValid.isEmpty()) {
            return new BigDecimal("0");
        }
        return adjust(moneyStrValid);
    }

    //
    private static String sanitizeDecimalValue(final String value) {
        String cleanedValue = value.trim() .replaceAll("[^0-9\\.\\<\\>\\,]", "");
        int lastCommaIdx = cleanedValue.lastIndexOf(",");
        int lastDotIdx = cleanedValue.lastIndexOf(".");
        if (lastCommaIdx > 0 && lastCommaIdx > lastDotIdx && lastDotIdx > 0) {
            // euro style number
            cleanedValue = cleanedValue.
                    replace(".", "").
                    replace(",", ".");
        }
        int dotOccurrence = 0;
        for (int i = 0; i < cleanedValue.length(); i++) {
            if (cleanedValue.charAt(i) == '.') {
                dotOccurrence++;
            }
        }
        if (dotOccurrence > 1) {
            // look like a string
            return "";
        }
        return cleanedValue.replace(",", "");
    }

    private static BigDecimal adjust(final String cleanedValue) {
        String adjustValue = cleanedValue;
        BigDecimal valueAdjustment = new BigDecimal("0.0");
        if (adjustValue.startsWith("<")) {
            adjustValue = cleanedValue.substring(1, cleanedValue.length());
            valueAdjustment = new BigDecimal("-0.5");
        }
        else if (adjustValue.startsWith(">")) {
            adjustValue = cleanedValue.substring(1, cleanedValue.length());
            valueAdjustment = new BigDecimal("0.5");
        }
        return new BigDecimal(adjustValue).add(valueAdjustment);
    }
}
