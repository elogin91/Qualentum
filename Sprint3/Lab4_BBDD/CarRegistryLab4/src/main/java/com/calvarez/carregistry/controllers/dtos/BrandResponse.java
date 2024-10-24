package com.calvarez.carregistry.controllers.dtos;

public record BrandResponse(
        String name,
        Integer warranty,
        String country
) {
}
