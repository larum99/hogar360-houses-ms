package com.hogar360.houses.houses.infraestructure.exceptionshandlers;

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
}
