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

    public HouseModel(){

    }

    public HouseModel(Long id, String name, String description, CategoryModel category,
                      int bedrooms, int bathrooms, BigDecimal price, LocationModel location,
                      LocalDate publicationDate, LocalDate activePublicationDate, PublicationStatus status) {

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
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public CategoryModel getCategory() {
        return category;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    public int getBathrooms() {
        return bathrooms;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocationModel getLocation() {
        return location;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }

    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }

    public void setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setLocation(LocationModel location) {
        this.location = location;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setActivePublicationDate(LocalDate activePublicationDate) {
        this.activePublicationDate = activePublicationDate;
    }

    public void setStatus(PublicationStatus status) {
        this.status = status;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public LocalDate getActivePublicationDate() {
        return activePublicationDate;
    }

    public PublicationStatus getStatus() {
        return status;
    }
}
