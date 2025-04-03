package com.hogar360.houses.houses.application.dto.response;

import java.util.List;

public record PagedLocationResponse(List<LocationResponse> content, long totalElements, int totalPages, int currentPage, int pageSize, boolean isFirst, boolean isLast) {
}
