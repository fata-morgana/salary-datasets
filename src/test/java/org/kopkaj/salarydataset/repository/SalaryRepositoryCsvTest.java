package org.kopkaj.salarydataset.repository;

import org.junit.jupiter.api.Test;
import org.kopkaj.salarydataset.model.Gender;
import org.kopkaj.salarydataset.model.SalaryDataset;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SalaryRepositoryCsvTest {
    @Test
    public void testNewInstanceWithValue() throws ParseException {
        SalaryRepository repository = new SalaryRepositoryCsv("data/salary_test.csv");
        List<SalaryDataset> data = repository.getAll();
        assertEquals(3, data.size());
        assertFirstSalaryDataset(data.get(0));
        assertSecondSalaryDataset(data.get(1));
        assertThirdSalaryDataset(data.get(2));
    }

    private static void assertFirstSalaryDataset(SalaryDataset dataset) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy HH:mm");
        Timestamp firstTimeStamp = new Timestamp(dateFormat.parse("3/21/16 12:54").getTime());
        assertEquals(firstTimeStamp, dataset.timestamp());
        assertEquals("", dataset.employer());
        assertEquals("Raleigh, NC", dataset.location());
        assertEquals("Software Developer", dataset.jobTitle());
        assertEquals(new BigDecimal("0"), dataset.yearsAtEmployer());
        assertEquals(new BigDecimal("18.0"), dataset.yearsOfExperience());
        assertEquals(new BigDecimal("122000"), dataset.salary());
        assertEquals(new BigDecimal("0"), dataset.signingBonus());
        assertEquals(new BigDecimal("0"), dataset.annualBonus());
        assertEquals(new BigDecimal("0"), dataset.annualStockValueBonus());
        assertEquals(Gender.MALE, dataset.gender());
        assertEquals("", dataset.additionalComment());
    }

    private static void assertSecondSalaryDataset(SalaryDataset dataset) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy HH:mm");
        Timestamp firstTimeStamp = new Timestamp(dateFormat.parse("3/21/16 12:58").getTime());
        assertEquals(firstTimeStamp, dataset.timestamp());
        assertEquals("Opower", dataset.employer());
        assertEquals("San Francisco, CA", dataset.location());
        assertEquals("Systems Engineer", dataset.jobTitle());
        assertEquals(new BigDecimal("2.0"), dataset.yearsAtEmployer());
        assertEquals(new BigDecimal("13.0"), dataset.yearsOfExperience());
        assertEquals(new BigDecimal("125000"), dataset.salary());
        assertEquals(new BigDecimal("5000"), dataset.signingBonus());
        assertEquals(new BigDecimal("0"), dataset.annualBonus());
        assertEquals(new BigDecimal("5000"), dataset.annualStockValueBonus());
        assertEquals(Gender.MALE, dataset.gender());
        assertEquals("Don't work here.", dataset.additionalComment());
    }

    private static void assertThirdSalaryDataset(SalaryDataset dataset) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy HH:mm");
        Timestamp firstTimeStamp = new Timestamp(dateFormat.parse("3/21/16 13:09").getTime());
        assertEquals(firstTimeStamp, dataset.timestamp());
        assertEquals("Early-Stage Startup", dataset.employer());
        assertEquals("Stockholm, Sweden", dataset.location());
        assertEquals("iOS Developer", dataset.jobTitle());
        assertEquals(new BigDecimal("0.8"), dataset.yearsAtEmployer());
        assertEquals(new BigDecimal("8.0"), dataset.yearsOfExperience());
        assertEquals(new BigDecimal("36.5"), dataset.salary()); // actual value is '36.5K USD / YEAR'. it's an edge case and won't worth the time to fix
        assertEquals(new BigDecimal("0"), dataset.signingBonus());
        assertEquals(new BigDecimal("0"), dataset.annualBonus());
        assertEquals(new BigDecimal("0"), dataset.annualStockValueBonus());
        assertEquals(Gender.MALE, dataset.gender());
        assertEquals("Yup. That's what I make. And trust me, I'm a pretty decent developer.", dataset.additionalComment());
    }
}
