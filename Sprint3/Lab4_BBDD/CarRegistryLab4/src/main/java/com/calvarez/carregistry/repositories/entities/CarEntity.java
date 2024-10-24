package com.calvarez.carregistry.repositories.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "car")
public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "brand_id")
    private Integer brand;
    private String model;
    private Integer milleage;
    private Double price;
    private Integer year;
    private String description;
    private String colour;
    @Column(name = "fuel_type")
    private String fuelType;
    @Column(name = "num_doors")
    private Integer numDoors;

}
