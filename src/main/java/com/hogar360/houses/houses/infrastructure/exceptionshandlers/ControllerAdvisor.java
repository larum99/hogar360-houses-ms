package com.hogar360.houses.houses.infrastructure.exceptionshandlers;

import com.hogar360.houses.houses.domain.exceptions.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler(CategoryNameMaxSizeExceededException.class)
    public ResponseEntity<ExceptionResponse> handleNameMaxSizeExceededException(CategoryNameMaxSizeExceededException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(ExceptionConstants.CATEGORY_NAME_MAX_SIZE_MESSAGE,
                LocalDateTime.now()));
    }

    @ExceptionHandler(CategoryDescriptionMaxSizeExceededException.class)
    public ResponseEntity<ExceptionResponse> handleDescriptionMaxSizeExceededException(CategoryDescriptionMaxSizeExceededException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(ExceptionConstants.CATEGORY_DESCRIPTION_MAX_SIZE_MESSAGE,
                LocalDateTime.now()));
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleCategoryAlreadyExistsException(CategoryAlreadyExistsException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(ExceptionConstants.CATEGORY_EXISTS_EXCEPTION,
                LocalDateTime.now()));
    }

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleDepartmentNotFoundException(DepartmentNotFoundException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(ExceptionConstants.DEPARTMENT_NOT_FOUND_EXCEPTION,
                LocalDateTime.now()));
    }

    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleCityNotFoundException(CityNotFoundException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(ExceptionConstants.CITY_NOT_FOUND_EXCEPTION,
                LocalDateTime.now()));
    }

    @ExceptionHandler(LocationSectorMaxSizeExceededException.class)
    public ResponseEntity<ExceptionResponse> handleLocationSectorMaxSizeExceededException(LocationSectorMaxSizeExceededException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(ExceptionConstants.LOCATION_SECTOR_MAX_SIZE_MESSAGE,
                LocalDateTime.now()));
    }

    @ExceptionHandler(PageNumberNegativeException.class)
    public ResponseEntity<ExceptionResponse> handlePageNumberNegativeException(PageNumberNegativeException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(ExceptionConstants.PAGE_NUMBER_NEGATIVE_EXCEPTION,
                LocalDateTime.now()));
    }

    @ExceptionHandler(PageSizeInvalidException.class)
    public ResponseEntity<ExceptionResponse> handlePageSizeInvalidException(PageSizeInvalidException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(ExceptionConstants.PAGE_SIZE_INVALID_EXCEPTION,
                LocalDateTime.now()));
    }

    @ExceptionHandler(HouseCategoryNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleHouseCategoryNotFoundException(HouseCategoryNotFoundException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(ExceptionConstants.HOUSE_CATEGORY_NOT_FOUND_EXCEPTION,
                LocalDateTime.now()));
    }

    @ExceptionHandler(HouseLocationNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleHouseLocationNotFoundException(HouseLocationNotFoundException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(ExceptionConstants.HOUSE_LOCATION_NOT_FOUND_EXCEPTION,
                LocalDateTime.now()));
    }

    @ExceptionHandler(HouseMinimumBathroomsRequiredException.class)
    public ResponseEntity<ExceptionResponse> handleHouseMinimumBathroomsRequiredException(HouseMinimumBathroomsRequiredException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(ExceptionConstants.HOUSE_MINIMUM_BATHROOMS_REQUIRED_EXCEPTION,
                LocalDateTime.now()));
    }

    @ExceptionHandler(HouseMinimumBedroomsRequiredException.class)
    public ResponseEntity<ExceptionResponse> handleHouseMinimumBedroomsRequiredException(HouseMinimumBedroomsRequiredException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(ExceptionConstants.HOUSE_MINIMUM_BEDROOMS_REQUIRED_EXCEPTION,
                LocalDateTime.now()));
    }

    @ExceptionHandler(HouseMinimumRequirePriceException.class)
    public ResponseEntity<ExceptionResponse> handleHouseMinimumRequirePriceException(HouseMinimumRequirePriceException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(ExceptionConstants.HOUSE_MINIMUM_PRICE_REQUIRED_EXCEPTION,
                LocalDateTime.now()));
    }

    @ExceptionHandler(InvalidPublicationDateException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidPublicationDateException(InvalidPublicationDateException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(ExceptionConstants.INVALID_PUBLICATION_DATE_EXCEPTION,
                LocalDateTime.now()));
    }

}
