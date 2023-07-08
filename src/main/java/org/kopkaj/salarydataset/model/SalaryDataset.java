package org.kopkaj.salarydataset.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.kopkaj.salarydataset.helper.SalaryDatasetFieldHelper;

public record SalaryDataset(
        Timestamp timestamp,
        String employer,
        String location,
        String jobTitle,
        BigDecimal yearsAtEmployer,
        BigDecimal yearsAtExperience,
        BigDecimal salary,
        BigDecimal signingBonus,
        BigDecimal annualBonus,
        BigDecimal annualStockValueBonus,
        Gender gender,
        String additionalComment
) {
    public SalaryDataset(SalaryDatasetRaw raw) {
        this(SalaryDatasetFieldHelper.formatTimestamp(raw.getTimestamp()),
                raw.getEmployer(),
                raw.getLocation(),
                raw.getJobTitle(),
                SalaryDatasetFieldHelper.convertToYear(raw.getYearsAtEmployer()),
                SalaryDatasetFieldHelper.convertToYear(raw.getYearsAtExperience()),
                SalaryDatasetFieldHelper.convertMoneyField(raw.getSalary()),
                SalaryDatasetFieldHelper.convertMoneyField(raw.getSigningBonus()),
                SalaryDatasetFieldHelper.convertMoneyField(raw.getAnnualBonus()),
                SalaryDatasetFieldHelper.convertMoneyField(raw.getAnnualStockValueBonus()),
                Gender.parseFromString(raw.getGender()),
                raw.getAdditionalComment());
    }

}
