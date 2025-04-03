package com.hogar360.houses.houses.infraestructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sector", nullable = false, length = 50)
    private String sector;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private CityEntity city;
}
