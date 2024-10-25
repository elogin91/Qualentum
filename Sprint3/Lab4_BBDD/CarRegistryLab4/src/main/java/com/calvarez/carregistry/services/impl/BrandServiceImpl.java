package com.calvarez.carregistry.services.impl;

import com.calvarez.carregistry.repositories.BrandRepository;
import com.calvarez.carregistry.repositories.entities.BrandEntity;
import com.calvarez.carregistry.services.BrandService;
import com.calvarez.carregistry.services.model.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private MapperService mapperService;

    @Override
    public Brand get(Integer id) {
        return brandRepository.findById(id).map(mapperService::serviceFromEntity).orElse(null);
    }

    @Override
    public List<Brand> getAll() {
        return brandRepository.findAll().stream().map(mapperService::serviceFromEntity).toList();
    }

    @Override
    public Brand update(Brand brand) {
        BrandEntity brandEntity = mapperService.entityFromService(brand);
        Brand brandUpdated = null;
        if (brandRepository.findById(brandEntity.getId()).isPresent()) {
            BrandEntity brandEntityUpdated = brandRepository.save(brandEntity);
            brandUpdated = mapperService.serviceFromEntity(brandEntityUpdated);
        }
        return brandUpdated;
    }

    @Override
    public Brand delete(Integer id) {
        Brand brand = brandRepository.findById(id).map(mapperService::serviceFromEntity).orElse(null);
        if (brand != null) {
            brandRepository.deleteById(id);
        }
        return brand;
    }

    @Override
    public Brand add(Brand brand) {
        BrandEntity brandEntity = mapperService.entityFromService(brand);
        return mapperService.serviceFromEntity(brandRepository.save(brandEntity));
    }
}