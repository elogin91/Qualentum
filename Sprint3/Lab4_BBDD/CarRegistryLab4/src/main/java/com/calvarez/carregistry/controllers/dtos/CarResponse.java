package com.calvarez.carregistry.controllers.dtos;

public record CarResponse(Integer id,
                          BrandResponse brand,
                          String model,
                          Integer milleage,
                          Double price,
                          Integer year,
                          String description,
                          String colour,
                          String fuelType,
                          Integer numDoors) {
}
