package com.hogar360.houses.houses.domain.model;

public class LocationModel {
    private Long id;
    private CityModel city;
    private String sector;

    public LocationModel() {
    }

    public LocationModel(Long id, CityModel city, String sector) {
        this.id = id;
        this.city = city;
        this.sector = sector;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CityModel getCity() {
        return city;
    }

    public void setCity(CityModel city) {
        this.city = city;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
}
