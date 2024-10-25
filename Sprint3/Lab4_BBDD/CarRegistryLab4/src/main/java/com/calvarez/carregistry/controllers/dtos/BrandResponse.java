package com.calvarez.carregistry.controllers.dtos;

public record BrandResponse(
        Integer id,
        String name,
        Integer warranty,
        String country
) {
}
