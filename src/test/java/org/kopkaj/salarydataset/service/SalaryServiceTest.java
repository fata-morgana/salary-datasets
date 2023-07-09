package org.kopkaj.salarydataset.service;

import org.junit.jupiter.api.Test;
import org.kopkaj.salarydataset.model.Gender;
import org.kopkaj.salarydataset.model.SalaryDataset;
import org.kopkaj.salarydataset.repository.SalaryRepository;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class SalaryServiceTest {

    private final SalaryRepository repository = Mockito.mock(SalaryRepository.class);
    private final SalaryService service = new SalaryService(repository);

    @Test
    public void testBuildFilterPredicate() {
        Map<String, String> predicateParams = new LinkedHashMap<>();
        predicateParams.put("salary[gt]", "100");
        predicateParams.put("salary[gte]", "100");
        predicateParams.put("salary[eq]", "100");
        predicateParams.put("salary[lte]", "100");
        predicateParams.put("salary[lt]", "100");

        predicateParams.put("job_title[gt]", "SalaryMan");
        predicateParams.put("job_title[gte]", "SalaryMan");
        predicateParams.put("job_title[eq]", "SalaryMan");
        predicateParams.put("job_title[lte]", "SalaryMan");
        predicateParams.put("job_title[lt]", "SalaryMan");

        List<Predicate<SalaryDataset>> predicates = service.buildFilterPredicate(predicateParams);

        Predicate<SalaryDataset> predicate0 = predicates.get(0);
        assertFalse(predicate0.test(buildSalaryDataset("99", "title", Gender.MALE)));
        assertFalse(predicate0.test(buildSalaryDataset("100", "title", Gender.MALE)));
        assertTrue(predicate0.test(buildSalaryDataset("101", "title", Gender.MALE)));

        Predicate<SalaryDataset> predicate1 = predicates.get(1);
        assertFalse(predicate1.test(buildSalaryDataset("99", "title", Gender.MALE)));
        assertTrue(predicate1.test(buildSalaryDataset("100", "title", Gender.MALE)));
        assertTrue(predicate1.test(buildSalaryDataset("101", "title", Gender.MALE)));

        Predicate<SalaryDataset> predicate2 = predicates.get(2);
        assertFalse(predicate2.test(buildSalaryDataset("99", "title", Gender.MALE)));
        assertTrue(predicate2.test(buildSalaryDataset("100", "title", Gender.MALE)));
        assertFalse(predicate2.test(buildSalaryDataset("101", "title", Gender.MALE)));

        Predicate<SalaryDataset> predicate3 = predicates.get(3);
        assertTrue(predicate3.test(buildSalaryDataset("99", "title", Gender.MALE)));
        assertTrue(predicate3.test(buildSalaryDataset("100", "title", Gender.MALE)));
        assertFalse(predicate3.test(buildSalaryDataset("101", "title", Gender.MALE)));

        Predicate<SalaryDataset> predicate4 = predicates.get(4);
        assertTrue(predicate4.test(buildSalaryDataset("99", "title", Gender.MALE)));
        assertFalse(predicate4.test(buildSalaryDataset("100", "title", Gender.MALE)));
        assertFalse(predicate4.test(buildSalaryDataset("101", "title", Gender.MALE)));

        Predicate<SalaryDataset> predicate5 = predicates.get(5);
        assertTrue(predicate5.test(buildSalaryDataset("100", "SalaryMao", Gender.MALE)));
        assertFalse(predicate5.test(buildSalaryDataset("100", "SalaryMan", Gender.MALE)));
        assertFalse(predicate5.test(buildSalaryDataset("100", "SalaryMam", Gender.MALE)));

        Predicate<SalaryDataset> predicate6 = predicates.get(6);
        assertTrue(predicate6.test(buildSalaryDataset("100", "SalaryMao", Gender.MALE)));
        assertTrue(predicate6.test(buildSalaryDataset("100", "SalaryMan", Gender.MALE)));
        assertFalse(predicate6.test(buildSalaryDataset("100", "SalaryMam", Gender.MALE)));

        Predicate<SalaryDataset> predicate7 = predicates.get(7);
        assertFalse(predicate7.test(buildSalaryDataset("100", "SalaryMao", Gender.MALE)));
        assertTrue(predicate7.test(buildSalaryDataset("100", "SalaryMan", Gender.MALE)));
        assertFalse(predicate7.test(buildSalaryDataset("100", "SalaryMam", Gender.MALE)));

        Predicate<SalaryDataset> predicate8 = predicates.get(8);
        assertFalse(predicate8.test(buildSalaryDataset("100", "SalaryMao", Gender.MALE)));
        assertTrue(predicate8.test(buildSalaryDataset("100", "SalaryMan", Gender.MALE)));
        assertTrue(predicate8.test(buildSalaryDataset("100", "SalaryMam", Gender.MALE)));

        Predicate<SalaryDataset> predicate9 = predicates.get(9);
        assertFalse(predicate9.test(buildSalaryDataset("100", "SalaryMao", Gender.MALE)));
        assertFalse(predicate9.test(buildSalaryDataset("100", "SalaryMan", Gender.MALE)));
        assertTrue(predicate9.test(buildSalaryDataset("100", "SalaryMam", Gender.MALE)));


        List<Predicate<SalaryDataset>> male1 = service.buildFilterPredicate(Map.of("gender", "M"));
        assertTrue(male1.get(0).test(buildSalaryDataset("100", "SalaryMan", Gender.MALE)));
        assertFalse(male1.get(0).test(buildSalaryDataset("100", "SalaryMan", Gender.FEMALE)));
        List<Predicate<SalaryDataset>> male2 = service.buildFilterPredicate(Map.of("gender", "MALE"));
        assertTrue(male2.get(0).test(buildSalaryDataset("100", "SalaryMan", Gender.MALE)));
        assertFalse(male2.get(0).test(buildSalaryDataset("100", "SalaryMan", Gender.FEMALE)));

        List<Predicate<SalaryDataset>> female1 = service.buildFilterPredicate(Map.of("gender", "F"));
        assertFalse(female1.get(0).test(buildSalaryDataset("100", "SalaryMan", Gender.MALE)));
        assertTrue(female1.get(0).test(buildSalaryDataset("100", "SalaryMan", Gender.FEMALE)));
        List<Predicate<SalaryDataset>> female2 = service.buildFilterPredicate(Map.of("gender", "FEMALE"));
        assertFalse(female2.get(0).test(buildSalaryDataset("100", "SalaryMan", Gender.MALE)));
        assertTrue(female2.get(0).test(buildSalaryDataset("100", "SalaryMan", Gender.FEMALE)));

        List<Predicate<SalaryDataset>> lgbtq = service.buildFilterPredicate(Map.of("gender", "LGBTQ"));
        assertFalse(lgbtq.get(0).test(buildSalaryDataset("100", "SalaryMan", Gender.MALE)));
        assertFalse(lgbtq.get(0).test(buildSalaryDataset("100", "SalaryMan", Gender.FEMALE)));
    }

    private SalaryDataset buildSalaryDataset(String salary, String jobTitle, Gender gender) {
        return new SalaryDataset(new Timestamp(System.currentTimeMillis()),
                "Employer",
                "Location",
                jobTitle,
                new BigDecimal("1"),
                new BigDecimal("1"),
                new BigDecimal(salary),
                new BigDecimal("0"),
                new BigDecimal("0"),
                new BigDecimal("0"),
                gender,
                "comment");
    }

    @Test
    public void testBuildSortComparator() {
        String sortParam = "location[asc],job_title[desc],years_of_experience[asc],years_at_employer[desc]," +
                "employer[asc],signing_bonus[desc],annual_bonus[asc],annual_stock_value_bonus[asc],salary[asc],gender[desc]";

        List<Comparator<SalaryDataset>> comparators = service.buildSortingComparator(sortParam).collect(Collectors.toList());

        SalaryDataset data1 = new SalaryDataset(
                new Timestamp(System.currentTimeMillis()),
                "employer1",
                "location1",
                "job_title1",
                new BigDecimal(1),
                new BigDecimal(1),
                new BigDecimal(10),
                new BigDecimal(1),
                new BigDecimal(1),
                new BigDecimal(1),
                Gender.MALE,
                "comment1"
        );

        SalaryDataset data2 = new SalaryDataset(
                new Timestamp(System.currentTimeMillis()),
                "employer2",
                "location2",
                "job_title2",
                new BigDecimal(2),
                new BigDecimal(2),
                new BigDecimal(20),
                new BigDecimal(2),
                new BigDecimal(2),
                new BigDecimal(2),
                Gender.FEMALE,
                "comment1"
        );

        Comparator<SalaryDataset> locationAsc = comparators.get(0);
        assertTrue(locationAsc.compare(data1, data2) < 0);
        Comparator<SalaryDataset> jobTitleDesc = comparators.get(1);
        assertTrue(jobTitleDesc.compare(data1, data2) > 0);
        Comparator<SalaryDataset> yearOfExperienceAsc = comparators.get(2);
        assertTrue(yearOfExperienceAsc.compare(data1, data2) < 0);
        Comparator<SalaryDataset> yearAtEmployerDesc = comparators.get(3);
        assertTrue(yearAtEmployerDesc.compare(data1, data2) > 0);
        Comparator<SalaryDataset> employerAsc = comparators.get(4);
        assertTrue(employerAsc.compare(data1, data2) < 0);
        Comparator<SalaryDataset> signingBonusDesc = comparators.get(5);
        assertTrue(signingBonusDesc.compare(data1, data2) > 0);
        Comparator<SalaryDataset> annualBonusAsc = comparators.get(6);
        assertTrue(annualBonusAsc.compare(data1, data2) < 0);
        Comparator<SalaryDataset> annualStockValueBonusAsc = comparators.get(7);
        assertTrue(annualStockValueBonusAsc.compare(data1, data2) < 0);
        Comparator<SalaryDataset> salaryAsc = comparators.get(8);
        assertTrue(salaryAsc.compare(data1, data2) < 0);
        Comparator<SalaryDataset> genderDesc = comparators.get(9);
        assertTrue(genderDesc.compare(data1, data2) > 0);
    }

    @Test
    public void testShowOnlyFields1() {
        String fields = "timestamp,employer,location,job_title,years_at_employer,years_of_experience";
        SalaryDataset data = buildSalaryDataset("10", "job_title", Gender.MALE);
        SalaryDataset result = service.showOnlyFields(data, fields);

        assertNotNull(result.timestamp());
        assertNotNull(result.employer());
        assertNotNull(result.location());
        assertNotNull(result.jobTitle());
        assertNotNull(result.yearsAtEmployer());
        assertNotNull(result.yearsOfExperience());

        assertNull(result.salary());
        assertNull(result.signingBonus());
        assertNull(result.annualBonus());
        assertNull(result.annualStockValueBonus());
        assertNull(result.gender());
        assertNull(result.additionalComment());
    }

    @Test
    public void testShowOnlyFields2() {
        String fields = "salary,signing_bonus,annual_bonus,annual_stock_value_bonus,gender,additional_comments";
        SalaryDataset data = buildSalaryDataset("10", "job_title", Gender.MALE);
        SalaryDataset result = service.showOnlyFields(data, fields);

        assertNull(result.timestamp());
        assertNull(result.employer());
        assertNull(result.location());
        assertNull(result.jobTitle());
        assertNull(result.yearsAtEmployer());
        assertNull(result.yearsOfExperience());

        assertNotNull(result.salary());
        assertNotNull(result.signingBonus());
        assertNotNull(result.annualBonus());
        assertNotNull(result.annualStockValueBonus());
        assertNotNull(result.gender());
        assertNotNull(result.additionalComment());
    }

    @Test
    public void testShowOnlyFieldsShowAll() {
        String fields = "";
        SalaryDataset data = buildSalaryDataset("10", "job_title", Gender.MALE);
        SalaryDataset result = service.showOnlyFields(data, fields);

        assertNotNull(result.timestamp());
        assertNotNull(result.employer());
        assertNotNull(result.location());
        assertNotNull(result.jobTitle());
        assertNotNull(result.yearsAtEmployer());
        assertNotNull(result.yearsOfExperience());

        assertNotNull(result.salary());
        assertNotNull(result.signingBonus());
        assertNotNull(result.annualBonus());
        assertNotNull(result.annualStockValueBonus());
        assertNotNull(result.gender());
        assertNotNull(result.additionalComment());
    }
}
