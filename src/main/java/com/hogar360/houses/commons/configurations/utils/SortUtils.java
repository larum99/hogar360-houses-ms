package com.hogar360.houses.commons.configurations.utils;

import org.springframework.data.domain.Sort;

public class SortUtils {

    private SortUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static Sort createSort(String sortBy, String sortDirection) {
        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "id";
        }

        sortBy = mapNestedFields(sortBy);

        Sort sort = Sort.by(sortBy);
        if ("desc".equalsIgnoreCase(sortDirection)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        return sort;
    }

    public static Sort createSort(String sortBy, boolean orderAsc) {
        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "id";
        }

        sortBy = mapNestedFields(sortBy);

        return orderAsc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
    }

    private static String mapNestedFields(String sortBy) {
        return switch (sortBy.toLowerCase()) {
            case "category" -> "category.name";
            case "city" -> "location.city.cityName";
            case "department" -> "location.city.department.departmentName";
            default -> sortBy;
        };
    }
}
