package org.kopkaj.salarydataset.service;

import org.junit.jupiter.api.Test;
import org.kopkaj.salarydataset.model.Gender;
import org.kopkaj.salarydataset.model.SalaryDataset;
import org.kopkaj.salarydataset.repository.SalaryRepository;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SalaryServiceTest {

    private SalaryRepository repository = Mockito.mock(SalaryRepository.class);
    private SalaryService service = new SalaryService(repository);

    @Test
    public void testBuildPredicate() {
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

        List<Predicate<SalaryDataset>> predicates = service.buildPredicate(predicateParams);

        Predicate<SalaryDataset> predicate0 = predicates.get(0);
        assertEquals(false, predicate0.test(buildForPredicateTest("99","title", Gender.MALE)));
        assertEquals(false, predicate0.test(buildForPredicateTest("100","title", Gender.MALE)));
        assertEquals(true, predicate0.test(buildForPredicateTest("101","title", Gender.MALE)));

        Predicate<SalaryDataset> predicate1 = predicates.get(1);
        assertEquals(false, predicate1.test(buildForPredicateTest("99","title", Gender.MALE)));
        assertEquals(true, predicate1.test(buildForPredicateTest("100","title", Gender.MALE)));
        assertEquals(true, predicate1.test(buildForPredicateTest("101","title", Gender.MALE)));

        Predicate<SalaryDataset> predicate2 = predicates.get(2);
        assertEquals(false, predicate2.test(buildForPredicateTest("99","title", Gender.MALE)));
        assertEquals(true, predicate2.test(buildForPredicateTest("100","title", Gender.MALE)));
        assertEquals(false, predicate2.test(buildForPredicateTest("101","title", Gender.MALE)));

        Predicate<SalaryDataset> predicate3 = predicates.get(3);
        assertEquals(true, predicate3.test(buildForPredicateTest("99","title", Gender.MALE)));
        assertEquals(true, predicate3.test(buildForPredicateTest("100","title", Gender.MALE)));
        assertEquals(false, predicate3.test(buildForPredicateTest("101","title", Gender.MALE)));

        Predicate<SalaryDataset> predicate4 = predicates.get(4);
        assertEquals(true, predicate4.test(buildForPredicateTest("99","title", Gender.MALE)));
        assertEquals(false, predicate4.test(buildForPredicateTest("100","title", Gender.MALE)));
        assertEquals(false, predicate4.test(buildForPredicateTest("101","title", Gender.MALE)));

        Predicate<SalaryDataset> predicate5 = predicates.get(5);
        assertEquals(true, predicate5.test(buildForPredicateTest("100","SalaryManz", Gender.MALE)));
        assertEquals(false, predicate5.test(buildForPredicateTest("100","SalaryMan", Gender.MALE)));
        assertEquals(false, predicate5.test(buildForPredicateTest("100","SalaryMa", Gender.MALE)));

        Predicate<SalaryDataset> predicate6 = predicates.get(6);
        assertEquals(true, predicate6.test(buildForPredicateTest("100","SalaryManz", Gender.MALE)));
        assertEquals(true, predicate6.test(buildForPredicateTest("100","SalaryMan", Gender.MALE)));
        assertEquals(false, predicate6.test(buildForPredicateTest("100","SalaryMa", Gender.MALE)));

        Predicate<SalaryDataset> predicate7 = predicates.get(7);
        assertEquals(false, predicate7.test(buildForPredicateTest("100","SalaryManz", Gender.MALE)));
        assertEquals(true, predicate7.test(buildForPredicateTest("100","SalaryMan", Gender.MALE)));
        assertEquals(false, predicate7.test(buildForPredicateTest("100","SalaryMa", Gender.MALE)));

        Predicate<SalaryDataset> predicate8 = predicates.get(8);
        assertEquals(false, predicate8.test(buildForPredicateTest("100","SalaryManz", Gender.MALE)));
        assertEquals(true, predicate8.test(buildForPredicateTest("100","SalaryMan", Gender.MALE)));
        assertEquals(true, predicate8.test(buildForPredicateTest("100","SalaryMa", Gender.MALE)));

        Predicate<SalaryDataset> predicate9 = predicates.get(9);
        assertEquals(false, predicate9.test(buildForPredicateTest("100","SalaryManz", Gender.MALE)));
        assertEquals(false, predicate9.test(buildForPredicateTest("100","SalaryMan", Gender.MALE)));
        assertEquals(true, predicate9.test(buildForPredicateTest("100","SalaryMa", Gender.MALE)));



        List<Predicate<SalaryDataset>> male1 = service.buildPredicate(Map.of("gender", "M"));
        assertEquals(true, male1.get(0).test(buildForPredicateTest("100","SalaryMan", Gender.MALE)));
        assertEquals(false, male1.get(0).test(buildForPredicateTest("100","SalaryMan", Gender.FEMALE)));
        List<Predicate<SalaryDataset>> male2 = service.buildPredicate(Map.of("gender", "MALE"));
        assertEquals(true, male2.get(0).test(buildForPredicateTest("100","SalaryMan", Gender.MALE)));
        assertEquals(false, male2.get(0).test(buildForPredicateTest("100","SalaryMan", Gender.FEMALE)));


        List<Predicate<SalaryDataset>> female1 = service.buildPredicate(Map.of("gender", "F"));
        assertEquals(false, female1.get(0).test(buildForPredicateTest("100","SalaryMan", Gender.MALE)));
        assertEquals(true, female1.get(0).test(buildForPredicateTest("100","SalaryMan", Gender.FEMALE)));
        List<Predicate<SalaryDataset>> female2 = service.buildPredicate(Map.of("gender", "FEMALE"));
        assertEquals(false, female2.get(0).test(buildForPredicateTest("100","SalaryMan", Gender.MALE)));
        assertEquals(true, female2.get(0).test(buildForPredicateTest("100","SalaryMan", Gender.FEMALE)));

        List<Predicate<SalaryDataset>> lgbtq = service.buildPredicate(Map.of("gender", "LGBTQ"));
        assertEquals(false, lgbtq.get(0).test(buildForPredicateTest("100","SalaryMan", Gender.MALE)));
        assertEquals(false, lgbtq.get(0).test(buildForPredicateTest("100","SalaryMan", Gender.FEMALE)));
    }

    private SalaryDataset buildForPredicateTest(String salary, String jobTitle, Gender gender) {
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
}
