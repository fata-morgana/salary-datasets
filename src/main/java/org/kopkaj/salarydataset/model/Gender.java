package org.kopkaj.salarydataset.model;

public enum Gender {
    MALE ("Male"),
    FEMALE ("Female"),
    BLANK (""),
    NON_BINARY("Others");

    public final String description;

    Gender(String description) {
        this.description = description;
    }

    public static Gender parseFromString(String input) {
        if (BLANK.description.equalsIgnoreCase(input)) {
            return BLANK;
        }
        else if (MALE.description.equalsIgnoreCase(input)) {
            return MALE;
        }
        else if (FEMALE.description.equalsIgnoreCase(input)) {
            return FEMALE;
        }
        else return NON_BINARY; // fallback case. we need to support LGBTQ people
    }
}
