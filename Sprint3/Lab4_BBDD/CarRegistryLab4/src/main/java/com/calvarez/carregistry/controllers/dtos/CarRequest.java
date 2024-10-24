package com.calvarez.carregistry.controllers.dtos;

import lombok.Data;

@Data
public class CarRequest {

    private Integer brand;
    private String model;
    private Integer milleage;
    private Double price;
    private Integer year;
    private String description;
    private String colour;
    private String fuelType;
    private Integer numDoors;
}
