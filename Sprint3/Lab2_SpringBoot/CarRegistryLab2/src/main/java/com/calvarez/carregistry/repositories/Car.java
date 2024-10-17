package com.calvarez.carregistry.repositories;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Car {

    private String licenseNumber;
    private String brand;
    private String model;
    private Integer year;
}
