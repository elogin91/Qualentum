package com.calvarez.carregistry.controllers.dtos;

import lombok.Data;

@Data
public class BrandRequest {
    private String name;
    private Integer warranty;
    private String country;
}
