package com.hogar360.houses.houses.domain.usecases;

import com.hogar360.houses.houses.domain.criteria.HouseSearchCriteria;
import com.hogar360.houses.houses.domain.exceptions.*;
import com.hogar360.houses.houses.domain.model.HouseModel;
import com.hogar360.houses.houses.domain.model.CategoryModel;
import com.hogar360.houses.houses.domain.model.LocationModel;
import com.hogar360.houses.houses.domain.ports.in.HouseServicePort;
import com.hogar360.houses.houses.domain.ports.out.HousePersistencePort;
import com.hogar360.houses.houses.domain.ports.out.CategoryPersistencePort;
import com.hogar360.houses.houses.domain.ports.out.LocationPersistencePort;
import com.hogar360.houses.houses.domain.utils.PageResult;
import com.hogar360.houses.houses.domain.utils.PublicationStatus;
import com.hogar360.houses.houses.domain.utils.constants.DomainConstants;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class HouseUseCase implements HouseServicePort {

    private final HousePersistencePort housePersistencePort;
    private final CategoryPersistencePort categoryPersistencePort;
    private final LocationPersistencePort locationPersistencePort;

    public HouseUseCase(HousePersistencePort housePersistencePort,
                        CategoryPersistencePort categoryPersistencePort,
                        LocationPersistencePort locationPersistencePort) {
        this.housePersistencePort = housePersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
        this.locationPersistencePort = locationPersistencePort;
    }

    @Override
    public void save(HouseModel houseModel, String role, Long userId) {
        validateRole(role);
        validateRequiredFields(houseModel);
        validateCategoryExists(houseModel.getCategory());
        validateLocationExists(houseModel.getLocation());
        validatePositiveValues(houseModel);
        validateActivePublicationDate(houseModel.getActivePublicationDate());

        PublicationStatus status = determineInitialStatus(houseModel.getActivePublicationDate());
        houseModel.setStatus(status);
        houseModel.setPublicationDate(LocalDate.now());

        houseModel.setPublisherId(userId);
        housePersistencePort.save(houseModel);
    }

    @Override
    public PageResult<HouseModel> searchHouses(HouseSearchCriteria criteria) {
        validatePageNumber(criteria.getPage());
        validatePageSize(criteria.getSize());
        validateSearchCriteria(criteria);
        return housePersistencePort.search(criteria);
    }

    @Override
    public Long findPublisherIdById(Long houseId) {
        return housePersistencePort.findPublisherIdById(houseId);
    }

    private void validateRole(String role) {
        if (!DomainConstants.ROLE_SELLER.equals(role)) {
            throw new ForbiddenException();
        }
    }

    private void validateRequiredFields(HouseModel houseModel) {
        Objects.requireNonNull(houseModel.getName(), DomainConstants.FIELD_NAME_NULL_MESSAGE);
        Objects.requireNonNull(houseModel.getDescription(), DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
        Objects.requireNonNull(houseModel.getCategory(), DomainConstants.FIELD_CATEGORY_NULL_MESSAGE);
        Objects.requireNonNull(houseModel.getPrice(), DomainConstants.FIELD_PRICE_NULL_MESSAGE);
        Objects.requireNonNull(houseModel.getLocation(), DomainConstants.FIELD_LOCATION_NULL_MESSAGE);
        Objects.requireNonNull(houseModel.getActivePublicationDate(), DomainConstants.FIELD_ACTIVE_PUBLICATION_DATE_NULL_MESSAGE);
    }

    private void validatePositiveValues(HouseModel houseModel) {
        if (houseModel.getBedrooms() < DomainConstants.MIN_BEDROOMS) {
            throw new HouseMinimumBedroomsRequiredException();
        }
        if (houseModel.getBathrooms() < DomainConstants.MIN_BATHROOMS) {
            throw new HouseMinimumBathroomsRequiredException();
        }
        if (houseModel.getPrice().compareTo(DomainConstants.MIN_PRICE) <= 0) {
            throw new HouseMinimumRequirePriceException();
        }
    }

    private void validateActivePublicationDate(LocalDate activePublicationDate) {
        LocalDate today = LocalDate.now();
        if (activePublicationDate.isBefore(today) ||
                ChronoUnit.DAYS.between(today, activePublicationDate) > DomainConstants.MAX_PUBLICATION_DAYS) {
            throw new InvalidPublicationDateException();
        }
    }

    private void validateCategoryExists(CategoryModel categoryModel) {
        Optional<CategoryModel> existingCategory = categoryPersistencePort.findById(categoryModel.getId());
        existingCategory.orElseThrow(HouseCategoryNotFoundException::new);
    }

    private void validateLocationExists(LocationModel locationModel) {
        Optional<LocationModel> existingLocation = locationPersistencePort.findById(locationModel.getId());
        existingLocation.orElseThrow(HouseLocationNotFoundException::new);
    }

    private PublicationStatus determineInitialStatus(LocalDate activePublicationDate) {
        return activePublicationDate.isAfter(LocalDate.now())
                ? PublicationStatus.PAUSED
                : PublicationStatus.PUBLISHED;
    }

    private void validatePageNumber(int page) {
        if (page < DomainConstants.DEFAULT_PAGE_NUMBER) {
            throw new PageNumberNegativeException();
        }
    }

    private void validatePageSize(int size) {
        if (size <= DomainConstants.DEFAULT_SIZE_NUMBER) {
            throw new PageSizeInvalidException();
        }
    }

    private void validateSearchCriteria(HouseSearchCriteria criteria) {
        if (criteria.getBedrooms() != null && criteria.getBedrooms() < DomainConstants.MIN_BEDROOMS) {
            throw new HouseMinimumBedroomsRequiredException();
        }
        if (criteria.getBathrooms() != null && criteria.getBathrooms() < DomainConstants.MIN_BATHROOMS) {
            throw new HouseMinimumBathroomsRequiredException();
        }
        if (criteria.getMinPrice() != null && criteria.getMinPrice().compareTo(DomainConstants.MIN_PRICE) <= 0) {
            throw new HouseMinimumRequirePriceException();
        }
        if (criteria.getSortDirection() != null) {
            String dir = criteria.getSortDirection().toLowerCase();
            if (!dir.equals(DomainConstants.SORT_DIRECTION_ASC) && !dir.equals(DomainConstants.SORT_DIRECTION_DESC)) {
                throw new InvalidSortDirectionException();
            }
        }
        validateSortBy(criteria.getSortBy());
    }

    private void validateSortBy(String sortBy) {
        if (sortBy == null) return;

        List<String> allowedFields = List.of(
                DomainConstants.SORT_BY_PRICE,
                DomainConstants.SORT_BY_BEDROOMS,
                DomainConstants.SORT_BY_BATHROOMS,
                DomainConstants.SORT_BY_PUBLICATION_DATE,
                DomainConstants.SORT_BY_CATEGORY
        );

        if (!allowedFields.contains(sortBy)) {
            throw new InvalidSortByFieldException();
        }
    }
}
