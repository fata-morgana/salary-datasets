package org.kopkaj.salarydataset.service;

import org.kopkaj.salarydataset.model.Gender;
import org.kopkaj.salarydataset.model.SalaryDataset;
import org.kopkaj.salarydataset.repository.SalaryRepository;
import org.kopkaj.salarydataset.service.model.SalaryDatasetField;
import org.kopkaj.salarydataset.service.model.SortOrder;
import org.kopkaj.salarydataset.service.model.SortParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.stream;

public class SalaryService {
    private static final Logger logger = LoggerFactory.getLogger(SalaryService.class);
    private final SalaryRepository repository;

    public SalaryService(SalaryRepository salaryRepository) {
        repository = salaryRepository;
    }

    public List<SalaryDataset> queryWithParameters(LinkedHashMap<String, String> queryParams) {
        Optional<Predicate<SalaryDataset>> predicates = buildFilterPredicate(queryParams).stream().reduce(Predicate::and);
        Optional<Comparator<SalaryDataset>> comparators = buildSortingComparator(queryParams.get("sort")).reduce(Comparator::thenComparing);
        return repository.
                getAll().
                stream().
                filter(predicates.orElse(other -> true)).
                sorted(comparators.orElse((a,b) -> 0)).
                map(data -> showOnlyFields(data, queryParams.get("fields"))).
                collect(Collectors.toList());
    }

    protected List<Predicate<SalaryDataset>> buildFilterPredicate(Map<String, String> queryParam) {
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

    protected Stream<Comparator<SalaryDataset>> buildSortingComparator(String sortParams) {
        if (sortParams == null || sortParams.isEmpty()) {
            return Stream.empty();
        }
        String[] paramArray = sortParams.split(",");
        Stream<SortParam> sortParamList = Arrays.stream(paramArray).
                map(SortParam::parse).
                filter(param -> param.field() != null || param.order() != null);
        return sortParamList.map(singleSortParam -> maybeSortDesc(buildComparatorFromSortField(singleSortParam.field()), singleSortParam.order()));
    }

    private Comparator<SalaryDataset> buildComparatorFromSortField(SalaryDatasetField sortField) {
        if(sortField == SalaryDatasetField.EMPLOYER) {
            return Comparator.comparing(SalaryDataset::employer);
        }
        else if(sortField == SalaryDatasetField.LOCATION) {
            return Comparator.comparing(SalaryDataset::location);
        }
        else if(sortField == SalaryDatasetField.JOB_TITLE) {
            return Comparator.comparing(SalaryDataset::jobTitle);
        }
        else if(sortField == SalaryDatasetField.YEARS_AT_EMPLOYER) {
            return Comparator.comparing(SalaryDataset::yearsAtEmployer);
        }
        else if(sortField == SalaryDatasetField.YEARS_OF_EXPERIENCE) {
            return Comparator.comparing(SalaryDataset::yearsOfExperience);
        }
        else if(sortField == SalaryDatasetField.SALARY) {
            return Comparator.comparing(SalaryDataset::salary);
        }
        else if(sortField== SalaryDatasetField.SIGNING_BONUS) {
            return Comparator.comparing(SalaryDataset::signingBonus);
        }
        else if(sortField == SalaryDatasetField.ANNUAL_BONUS) {
            return Comparator.comparing(SalaryDataset::annualBonus);
        }
        else if(sortField == SalaryDatasetField.ANNUAL_STOCK_VALUE_BONUS) {
            return Comparator.comparing(SalaryDataset::annualStockValueBonus);
        }
        else if(sortField == SalaryDatasetField.GENDER) {
            return Comparator.comparing(SalaryDataset::gender);
        }
        else {
            // noop comparator
            return Comparator.comparing((a) -> true);
        }
    }

    private Comparator<SalaryDataset> maybeSortDesc(Comparator<SalaryDataset> comparator, SortOrder sortOrder) {
        return sortOrder == SortOrder.DESC ? comparator.reversed() : comparator;
    }

    protected SalaryDataset showOnlyFields(SalaryDataset data, String showingFields) {
        if (showingFields == null || showingFields.trim().isEmpty()) {
            return data;
        }
        List<SalaryDatasetField> fields = Arrays.stream(showingFields.split(",")).map(SalaryDatasetField::parse).toList();
        return new SalaryDataset(
                fields.contains(SalaryDatasetField.TIMESTAMP) ? data.timestamp() : null,
                fields.contains(SalaryDatasetField.EMPLOYER) ? data.employer() : null,
                fields.contains(SalaryDatasetField.LOCATION) ? data.location() : null,
                fields.contains(SalaryDatasetField.JOB_TITLE) ? data.jobTitle() : null,
                fields.contains(SalaryDatasetField.YEARS_AT_EMPLOYER) ? data.yearsAtEmployer() : null,
                fields.contains(SalaryDatasetField.YEARS_OF_EXPERIENCE) ? data.yearsOfExperience() : null,
                fields.contains(SalaryDatasetField.SALARY) ? data.salary() : null,
                fields.contains(SalaryDatasetField.SIGNING_BONUS) ? data.signingBonus() : null,
                fields.contains(SalaryDatasetField.ANNUAL_BONUS) ? data.annualBonus() : null,
                fields.contains(SalaryDatasetField.ANNUAL_STOCK_VALUE_BONUS) ? data.annualStockValueBonus() : null,
                fields.contains(SalaryDatasetField.GENDER) ? data.gender() : null,
                fields.contains(SalaryDatasetField.ADDITIONAL_COMMENTS) ? data.additionalComment() : null
        );
    }
}