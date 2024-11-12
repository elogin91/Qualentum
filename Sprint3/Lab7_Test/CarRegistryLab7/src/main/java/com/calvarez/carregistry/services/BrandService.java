package com.calvarez.carregistry.services;

import com.calvarez.carregistry.services.model.Brand;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface BrandService {
    @Async
    CompletableFuture<List<Brand>>getAll();
    Brand get(Integer id);
    Brand update(Brand item);
    Brand delete(Integer id);
    Brand add(Brand item);
}