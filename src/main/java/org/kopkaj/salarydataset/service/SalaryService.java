package org.kopkaj.salarydataset.service;

import org.kopkaj.salarydataset.model.Gender;
import org.kopkaj.salarydataset.model.SalaryDataset;
import org.kopkaj.salarydataset.repository.SalaryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class SalaryService {
    private final SalaryRepository repository;

    public SalaryService(SalaryRepository salaryRepository) {
        repository = salaryRepository;
    }



    public List<SalaryDataset> queryWithParameters(Map<String, String> queryParam) {
        return null;
    }

    protected List<Predicate<SalaryDataset>> buildPredicate(Map<String, String> queryParam) {
        List<Predicate<SalaryDataset>> predicates = new ArrayList<>();
        for(Map.Entry<String, String> entry : queryParam.entrySet()) {
            if (entry.getKey().equalsIgnoreCase("salary[gt]")) {
                predicates.add((dataset) -> dataset.salary().compareTo(new BigDecimal(entry.getValue())) > 0);
            }
            if (entry.getKey().equalsIgnoreCase("salary[gte]")) {
                predicates.add((dataset) -> dataset.salary().compareTo(new BigDecimal(entry.getValue())) >= 0);
            }
            if (entry.getKey().equalsIgnoreCase("salary[eq]")) {
                predicates.add((dataset) -> dataset.salary().compareTo(new BigDecimal(entry.getValue())) == 0);
            }
            if (entry.getKey().equalsIgnoreCase("salary[lte]")) {
                predicates.add((dataset) -> dataset.salary().compareTo(new BigDecimal(entry.getValue())) <= 0);
            }
            if (entry.getKey().equalsIgnoreCase("salary[lt]")) {
                predicates.add((dataset) -> dataset.salary().compareTo(new BigDecimal(entry.getValue())) < 0);
            }


            if (entry.getKey().equalsIgnoreCase("job_title[gt]")) {
                predicates.add((dataset) -> dataset.jobTitle().compareTo(entry.getValue()) > 0);
            }
            if (entry.getKey().equalsIgnoreCase("job_title[gte]")) {
                predicates.add((dataset) -> dataset.jobTitle().compareTo(entry.getValue()) >= 0);
            }
            if (entry.getKey().equalsIgnoreCase("job_title[eq]")) {
                predicates.add((dataset) -> dataset.jobTitle().compareTo(entry.getValue()) == 0);
            }
            if (entry.getKey().equalsIgnoreCase("job_title[lte]")) {
                predicates.add((dataset) -> dataset.jobTitle().compareTo(entry.getValue()) <= 0);
            }
            if (entry.getKey().equalsIgnoreCase("job_title[lt]")) {
                predicates.add((dataset) -> dataset.jobTitle().compareTo(entry.getValue()) < 0);
            }


            if (entry.getKey().equalsIgnoreCase("gender")) {
                if (entry.getValue().equalsIgnoreCase("Male") || entry.getValue().equalsIgnoreCase("M")) {
                    predicates.add((dataset) -> dataset.gender() == Gender.MALE);
                }
                if (entry.getValue().equalsIgnoreCase("Female") || entry.getValue().equalsIgnoreCase("F")) {
                    predicates.add((dataset) -> dataset.gender() == Gender.FEMALE);
                }
                else {
                    // Not support non-binary yet
                    predicates.add((dataset) -> false);
                }
            }
        }
        return predicates;
    }
}
