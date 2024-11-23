package com.calvarez.carregistry.services;

import com.calvarez.carregistry.services.model.Brand;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface BrandService {
    @Async
    CompletableFuture<List<Brand>>getAll();
    Optional<Brand> get(Integer id);
    Optional<Brand> update(Brand item);
    Optional<Brand> delete(Integer id);
    Brand add(Brand item);
}
