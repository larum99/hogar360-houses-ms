package com.hogar360.houses.houses.domain.model;

import com.hogar360.houses.houses.domain.utils.PublicationStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class HouseModel {

    private Long id;
    private String name;
    private String description;
    private CategoryModel category;
    private int bedrooms;
    private int bathrooms;
    private BigDecimal price;
    private LocationModel location;
    private LocalDate publicationDate;
    private LocalDate activePublicationDate;
    private PublicationStatus status;
    private Long publisherId;

    public HouseModel() {}

    public HouseModel(Long id, String name, String description, CategoryModel category,
                      int bedrooms, int bathrooms, BigDecimal price, LocationModel location,
                      LocalDate publicationDate, LocalDate activePublicationDate, PublicationStatus status, Long publisherId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.price = price;
        this.location = location;
        this.publicationDate = publicationDate;
        this.activePublicationDate = activePublicationDate;
        this.status = status;
        this.publisherId = publisherId;  // Inicialización del publisherId
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryModel getCategory() {
        return category;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }

    public int getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(LocationModel location) {
        this.location = location;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public LocalDate getActivePublicationDate() {
        return activePublicationDate;
    }

    public void setActivePublicationDate(LocalDate activePublicationDate) {
        this.activePublicationDate = activePublicationDate;
    }

    public PublicationStatus getStatus() {
        return status;
    }

    public void setStatus(PublicationStatus status) {
        this.status = status;
    }

    // Getter y setter para publisherId
    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }
}
