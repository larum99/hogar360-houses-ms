package com.hogar360.houses.houses.infrastructure.specifications;

import com.hogar360.houses.houses.domain.criteria.HouseSearchCriteria;
import com.hogar360.houses.houses.infrastructure.entities.HouseEntity;
import com.hogar360.houses.houses.domain.utils.PublicationStatus;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class HouseSpecification {

    private HouseSpecification() {
        throw new UnsupportedOperationException("No instanciar esta clase");
    }

    public static Specification<HouseEntity> hasSector(String sector) {
        return (root, query, criteriaBuilder) ->
                sector == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("location").get("sector")), "%" + sector.toLowerCase() + "%");
    }

    public static Specification<HouseEntity> hasCity(String city) {
        return (root, query, criteriaBuilder) ->
                city == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("location").get("city").get("name")), "%" + city.toLowerCase() + "%");
    }

    public static Specification<HouseEntity> hasDepartment(String department) {
        return (root, query, criteriaBuilder) ->
                department == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("location").get("city").get("department").get("name")), "%" + department.toLowerCase() + "%");
    }

    public static Specification<HouseEntity> hasCategory(String category) {
        return (root, query, criteriaBuilder) ->
                category == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("category").get("name")), "%" + category.toLowerCase() + "%");
    }

    public static Specification<HouseEntity> hasBedrooms(Integer bedrooms) {
        return (root, query, criteriaBuilder) ->
                bedrooms == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("bedrooms"), bedrooms);
    }

    public static Specification<HouseEntity> hasBathrooms(Integer bathrooms) {
        return (root, query, criteriaBuilder) ->
                bathrooms == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("bathrooms"), bathrooms);
    }

    public static Specification<HouseEntity> hasMinPrice(BigDecimal minPrice) {
        return (root, query, criteriaBuilder) ->
                minPrice == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<HouseEntity> hasMaxPrice(BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) ->
                maxPrice == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public static Specification<HouseEntity> hasPrice(BigDecimal price, BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) -> {
            if (price != null) {
                return criteriaBuilder.equal(root.get("price"), price);
            } else if (minPrice != null && maxPrice != null) {
                return criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
            } else if (minPrice != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
            } else if (maxPrice != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }

    public static Specification<HouseEntity> isPublished() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), PublicationStatus.PUBLISHED);
    }
}
