package org.kopkaj.salarydataset.service.model;

public enum SortField {
    EMPLOYER ("employer"),
    LOCATION ("location"),
    JOB_TITLE ("job_title"),
    YEARS_AT_EMPLOYER ("years_at_employer"),
    YEARS_OF_EXPERIENCE ("years_of_experience"),
    SALARY ("salary"),
    SIGNING_BONUS ("signing_bonus"),
    ANNUAL_BONUS ("annual_bonus"),
    ANNUAL_STOCK_VALUE_BONUS ("annual_stock_value_bonus"),
    GENDER ("gender");

    private final String fieldName;
    SortField(String fieldName) {
        this.fieldName = fieldName;
    }

    public static SortField parse(final String fieldName) {
        if (fieldName.equalsIgnoreCase(EMPLOYER.fieldName)) {
            return EMPLOYER;
        }
        else if (fieldName.equalsIgnoreCase(LOCATION.fieldName)) {
            return LOCATION;
        }
        else if (fieldName.equalsIgnoreCase(JOB_TITLE.fieldName)) {
            return JOB_TITLE;
        }
        else if (fieldName.equalsIgnoreCase(YEARS_AT_EMPLOYER.fieldName)) {
            return YEARS_AT_EMPLOYER;
        }
        else if (fieldName.equalsIgnoreCase(YEARS_OF_EXPERIENCE.fieldName)) {
            return YEARS_OF_EXPERIENCE;
        }
        else if (fieldName.equalsIgnoreCase(SALARY.fieldName)) {
            return SALARY;
        }
        else if (fieldName.equalsIgnoreCase(SIGNING_BONUS.fieldName)) {
            return SIGNING_BONUS;
        }
        else if (fieldName.equalsIgnoreCase(ANNUAL_BONUS.fieldName)) {
            return ANNUAL_BONUS;
        }
        else if (fieldName.equalsIgnoreCase(ANNUAL_STOCK_VALUE_BONUS.fieldName)) {
            return ANNUAL_STOCK_VALUE_BONUS;
        }
        else if (fieldName.equalsIgnoreCase(GENDER.fieldName)) {
            return GENDER;
        }
        else {
            // skip sorting this field is fieldName is invalid. should not throw 500 error if fieldName is misspelled
            return null;
        }
    }
}
