package com.calvarez.carregistry.controllers.dtos;

public record CarResponse(String brand,
                          String model,
                          Integer milleage,
                          Double price,
                          Integer year,
                          String description,
                          String colour,
                          String fuelType,
                          Integer numDoors) {
}
