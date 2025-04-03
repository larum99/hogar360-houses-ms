package com.hogar360.houses.houses.infraestructure.exceptionshandlers;

public class ExceptionConstants {
    private ExceptionConstants(){}

    public static final String CATEGORY_NAME_MAX_SIZE_MESSAGE = "The name of the category can not exceed 50 characters";
    public static final String CATEGORY_DESCRIPTION_MAX_SIZE_MESSAGE = "The description of the category can not exceed 90 characters";
    public static final String CATEGORY_EXISTS_EXCEPTION = "The category already exists";

    public static final String DEPARTMENT_NOT_FOUND_EXCEPTION = "The department does not exist";
    public static final String CITY_NOT_FOUND_EXCEPTION = "The city does not exist in the specified department";
    public static final String LOCATION_SECTOR_MAX_SIZE_MESSAGE = "The sector cannot exceed 50 characters";

}
