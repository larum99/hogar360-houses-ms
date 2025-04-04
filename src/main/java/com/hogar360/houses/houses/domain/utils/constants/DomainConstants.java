package com.hogar360.houses.houses.domain.utils.constants;

public class DomainConstants {

    private DomainConstants() {
        throw new IllegalStateException("Utility class");
    }

    /*
    Validation messages
     */
    public static final String FIELD_NAME_NULL_MESSAGE = "Field 'name' can not be null";
    public static final String FIELD_DESCRIPTION_NULL_MESSAGE = "Field 'description' can not be null";

    public static final Integer MAX_SECTOR_LENGTH = 50;
    public static final Integer MAX_CATEGORY_NAME_LENGTH = 50;
    public static final Integer MAX_CATEGORY_DESCRIPTION_LENGTH = 90;
    public static final Integer DEFAULT_PAGE_NUMBER = 0;
    public static final Integer DEFAULT_SIZE_NUMBER = 0;
}
