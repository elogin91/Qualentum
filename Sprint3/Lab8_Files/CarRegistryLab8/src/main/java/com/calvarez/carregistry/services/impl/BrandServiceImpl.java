package com.calvarez.carregistry.services.impl;

import com.calvarez.carregistry.repositories.BrandRepository;
import com.calvarez.carregistry.repositories.entities.BrandEntity;
import com.calvarez.carregistry.services.BrandService;
import com.calvarez.carregistry.services.model.Brand;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final MapperService mapperService;

    public BrandServiceImpl(BrandRepository brandRepository,MapperService mapperService) {
        this.brandRepository=brandRepository;
        this.mapperService=mapperService;
    }
    @Override
    public Optional<Brand> get(Integer id) {
        return brandRepository.findById(id).map(mapperService::serviceFromEntity);
    }

    @Override
    public CompletableFuture<List<Brand>> getAll() {
        return CompletableFuture.completedFuture(brandRepository.findAll().stream().map(mapperService::serviceFromEntity).toList());
    }

    @Override
    public Optional<Brand> update(Brand brand) {
        BrandEntity brandEntity = mapperService.entityFromService(brand);
        Brand brandUpdated = null;
        if (brandRepository.findById(brandEntity.getId()).isPresent()) {
            BrandEntity brandEntityUpdated = brandRepository.save(brandEntity);
            brandUpdated = mapperService.serviceFromEntity(brandEntityUpdated);
        }
        return Optional.ofNullable(brandUpdated);
    }

    @Override
    public Optional<Brand> delete(Integer id) {
        Brand brand = brandRepository.findById(id).map(mapperService::serviceFromEntity).orElse(null);
        if (brand != null) {
            brandRepository.deleteById(id);
        }
        return Optional.ofNullable(brand);
    }

    @Override
    public Brand add(Brand brand) {
        BrandEntity brandEntity = mapperService.entityFromService(brand);
        return mapperService.serviceFromEntity(brandRepository.save(brandEntity));
    }
}