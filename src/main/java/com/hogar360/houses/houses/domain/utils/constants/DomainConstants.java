package com.hogar360.houses.houses.domain.utils.constants;

import java.math.BigDecimal;

public class DomainConstants {

    private DomainConstants() {
        throw new IllegalStateException("Utility class");
    }


    public static final String FIELD_NAME_NULL_MESSAGE = "Field 'name' cannot be null";
    public static final String FIELD_DESCRIPTION_NULL_MESSAGE = "Field 'description' cannot be null";
    public static final String FIELD_CATEGORY_NULL_MESSAGE = "Category cannot be null";
    public static final String FIELD_PRICE_NULL_MESSAGE = "Price cannot be null";
    public static final String FIELD_LOCATION_NULL_MESSAGE = "Location cannot be null";
    public static final String FIELD_ACTIVE_PUBLICATION_DATE_NULL_MESSAGE = "Active publication date cannot be null";

    public static final int MAX_SECTOR_LENGTH = 50;
    public static final int MAX_CATEGORY_NAME_LENGTH = 50;
    public static final int MAX_CATEGORY_DESCRIPTION_LENGTH = 90;

    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_SIZE_NUMBER = 10;

    public static final int MIN_BEDROOMS = 1;
    public static final int MIN_BATHROOMS = 1;
    public static final BigDecimal MIN_PRICE = BigDecimal.ZERO;
    public static final int MAX_PUBLICATION_DAYS = 30;
}
