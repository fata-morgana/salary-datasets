package org.kopkaj.salarydataset.model;

public enum Gender {
    MALE ("Male"),
    FEMALE ("Female"),
    NON_BINARY("Others");

    public final String description;

    Gender(String description) {
        this.description = description;
    }

    public static Gender parseFromString(String input) {
        if (MALE.description.equalsIgnoreCase(input)) {
            return MALE;
        }
        else if (FEMALE.description.equalsIgnoreCase(input)) {
            return FEMALE;
        }
        else return NON_BINARY; // fallback case. also need to support LGBTQ people
    }
}
