package com.calvarez.carregistry.services;

import com.calvarez.carregistry.services.model.Brand;

import java.util.List;

public interface BrandService {
    List<Brand> getAll();

    Brand get(Integer id);

    Brand update(Brand item);

    Brand delete(Integer id);

    Brand add(Brand item);
}