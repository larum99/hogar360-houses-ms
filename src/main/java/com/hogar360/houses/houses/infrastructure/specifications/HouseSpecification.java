package com.hogar360.houses.houses.infrastructure.specifications;

import com.hogar360.houses.houses.infrastructure.entities.HouseEntity;
import com.hogar360.houses.houses.domain.utils.PublicationStatus;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class HouseSpecification {

    private HouseSpecification() {
        throw new IllegalStateException("Utility class");
    }

    public static Specification<HouseEntity> hasSector(String sector) {
        return (root, query, criteriaBuilder) ->
                sector == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(SpecificationsConstansts.LOCATION).get(SpecificationsConstansts.SECTOR)), "%" + sector.toLowerCase() + "%");
    }

    public static Specification<HouseEntity> hasCity(String city) {
        return (root, query, criteriaBuilder) ->
                city == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(SpecificationsConstansts.LOCATION).get(SpecificationsConstansts.CITY).get(SpecificationsConstansts.CITY_NAME)), "%" + city.toLowerCase() + "%");
    }

    public static Specification<HouseEntity> hasDepartment(String department) {
        return (root, query, criteriaBuilder) ->
                department == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(SpecificationsConstansts.LOCATION).get(SpecificationsConstansts.CITY).get(SpecificationsConstansts.DEPARTMENT).get(SpecificationsConstansts.DEPARTMENT_NAME)), "%" + department.toLowerCase() + "%");
    }

    public static Specification<HouseEntity> hasCategory(String category) {
        return (root, query, criteriaBuilder) ->
                category == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(SpecificationsConstansts.CATEGORY).get(SpecificationsConstansts.CATEGORY_NAME)), "%" + category.toLowerCase() + "%");
    }

    public static Specification<HouseEntity> hasBedrooms(Integer bedrooms) {
        return (root, query, criteriaBuilder) ->
                bedrooms == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get(SpecificationsConstansts.BEDROOMS), bedrooms);
    }

    public static Specification<HouseEntity> hasBathrooms(Integer bathrooms) {
        return (root, query, criteriaBuilder) ->
                bathrooms == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get(SpecificationsConstansts.BATHROOMS), bathrooms);
    }

    public static Specification<HouseEntity> hasPrice(BigDecimal price, BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) -> {
            if (price != null) {
                return criteriaBuilder.equal(root.get(SpecificationsConstansts.PRICE), price);
            } else if (minPrice != null && maxPrice != null) {
                return criteriaBuilder.between(root.get(SpecificationsConstansts.PRICE), minPrice, maxPrice);
            } else if (minPrice != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get(SpecificationsConstansts.PRICE), minPrice);
            } else if (maxPrice != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get(SpecificationsConstansts.PRICE), maxPrice);
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }

    public static Specification<HouseEntity> hasStatus(PublicationStatus status) {
        return (root, query, criteriaBuilder) ->
                status == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get(SpecificationsConstansts.STATUS), status);
    }

    public static Specification<HouseEntity> hasPublisher(Long publisherId) {
        return (root, query, criteriaBuilder) ->
                publisherId == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get(SpecificationsConstansts.PUBLISHER_ID), publisherId);
    }
}
