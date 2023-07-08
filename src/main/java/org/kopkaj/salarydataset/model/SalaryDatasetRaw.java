package org.kopkaj.salarydataset.model;

import com.opencsv.bean.CsvBindByName;

public class SalaryDatasetRaw {
    @CsvBindByName(column = "Timestamp")
    private String timestamp;
    @CsvBindByName(column = "Employer")
    private String employer;
    @CsvBindByName(column = "Location")
    private String location;
    @CsvBindByName(column = "Job Title")
    private String jobTitle;
    @CsvBindByName(column = "Years at Employer")
    private String yearsAtEmployer;
    @CsvBindByName(column = "Years of Experience")
    private String yearsAtExperience;
    @CsvBindByName(column = "Salary")
    private String salary;
    @CsvBindByName(column = "Signing Bonus")
    private String signingBonus;
    @CsvBindByName(column = "Annual Bonus")
    private String annualBonus;
    @CsvBindByName(column = "Annual Stock Value/Bonus")
    private String annualStockValueBonus;
    @CsvBindByName(column = "Gender")
    private String gender;
    @CsvBindByName(column = "Additional Comments")
    private String additionalComment;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getYearsAtEmployer() {
        return yearsAtEmployer;
    }

    public void setYearsAtEmployer(String yearsAtEmployer) {
        this.yearsAtEmployer = yearsAtEmployer;
    }

    public String getYearsAtExperience() {
        return yearsAtExperience;
    }

    public void setYearsAtExperience(String yearsAtExperience) {
        this.yearsAtExperience = yearsAtExperience;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getSigningBonus() {
        return signingBonus;
    }

    public void setSigningBonus(String signingBonus) {
        this.signingBonus = signingBonus;
    }

    public String getAnnualBonus() {
        return annualBonus;
    }

    public void setAnnualBonus(String annualBonus) {
        this.annualBonus = annualBonus;
    }

    public String getAnnualStockValueBonus() {
        return annualStockValueBonus;
    }

    public void setAnnualStockValueBonus(String annualStockValueBonus) {
        this.annualStockValueBonus = annualStockValueBonus;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAdditionalComment() {
        return additionalComment;
    }

    public void setAdditionalComment(String additionalComment) {
        this.additionalComment = additionalComment;
    }
}