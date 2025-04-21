package com.hogar360.houses.houses.infrastructure.exceptionshandlers;

public class ExceptionConstants {
    private ExceptionConstants(){}

    public static final String CATEGORY_NAME_MAX_SIZE_MESSAGE = "The name of the category can not exceed 50 characters";
    public static final String CATEGORY_DESCRIPTION_MAX_SIZE_MESSAGE = "The description of the category can not exceed 90 characters";
    public static final String CATEGORY_EXISTS_EXCEPTION = "The category already exists";

    public static final String DEPARTMENT_NOT_FOUND_EXCEPTION = "The department does not exist";
    public static final String CITY_NOT_FOUND_EXCEPTION = "The city does not exist in the specified department";
    public static final String LOCATION_SECTOR_MAX_SIZE_MESSAGE = "The sector cannot exceed 50 characters";

    public static final String PAGE_NUMBER_NEGATIVE_EXCEPTION = "Page number cannot be negative.";
    public static final String PAGE_SIZE_INVALID_EXCEPTION = "Size must be greater than zero.";

    public static final String HOUSE_CATEGORY_NOT_FOUND_EXCEPTION = "The house category was not found";
    public static final String HOUSE_LOCATION_NOT_FOUND_EXCEPTION = "The house location was not found";
    public static final String HOUSE_MINIMUM_BATHROOMS_REQUIRED_EXCEPTION = "The house must have at least the minimum number of bathrooms";
    public static final String HOUSE_MINIMUM_BEDROOMS_REQUIRED_EXCEPTION = "The house must have at least the minimum number of bedrooms";
    public static final String HOUSE_MINIMUM_PRICE_REQUIRED_EXCEPTION = "The house price must meet the minimum requirements";
    public static final String INVALID_PUBLICATION_DATE_EXCEPTION = "The publication date is invalid";

    public static final String INVALID_SORT_BY_FIELD_EXCEPTION = "The sorting field is invalid";
    public static final String INVALID_SORT_DIRECTION_EXCEPTION = "The sorting direction is invalid";

    public static final String FORBIDDEN_MESSAGE = "Access denied: insufficient permissions.";
}
