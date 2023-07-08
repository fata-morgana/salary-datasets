package org.kopkaj.salarydataset.helper;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SalaryDatasetFieldHelperTest {

    @Test
    public void testParseTimestamp() {
        String timestampStr = "3/21/16 12:54";
        Timestamp actual = SalaryDatasetFieldHelper.formatTimestamp(timestampStr);
        LocalDateTime actualDateTime = actual.toLocalDateTime();
        assertEquals( 2016, actualDateTime.getYear());
        assertEquals(Month.MARCH, actualDateTime.getMonth());
        assertEquals(21, actualDateTime.getDayOfMonth());
        assertEquals(12, actualDateTime.getHour());
        assertEquals(54, actualDateTime.getMinute());
    }

    @Test
    public void testParseExperienceInYear() {
        BigDecimal actual1 = SalaryDatasetFieldHelper.convertToYear("1");
        assertEquals(new BigDecimal("1.0"), actual1);
        BigDecimal actual2 = SalaryDatasetFieldHelper.convertToYear("<1");
        assertEquals(new BigDecimal("0.5"), actual2);
        BigDecimal actual3 = SalaryDatasetFieldHelper.convertToYear(">1");
        assertEquals(new BigDecimal("1.5"), actual3);
    }

    @Test
    public void testConvertMoneyField() {
        BigDecimal actual1 = SalaryDatasetFieldHelper.convertMoneyField("€30,000");
        assertEquals(new BigDecimal("30000"), actual1);
        BigDecimal actual2 = SalaryDatasetFieldHelper.convertMoneyField("115,000");
        assertEquals(new BigDecimal("115000"), actual2);
    }
}
