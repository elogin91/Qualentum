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

    @Override
    public Brand get(Integer id) {
        return brandRepository.findById(id).map(BrandServiceImpl::serviceFromEntity).orElse(null);
    }

    @Override
    public List<Brand> getAll() {
        return brandRepository.findAll().stream().map(BrandServiceImpl::serviceFromEntity).toList();
    }

    @Override
    public Brand update(Brand brand) {
        BrandEntity brandEntity = entityFromService(brand);
        Brand brandUpdated = null;
        if (brandRepository.findById(brandEntity.getId()).isPresent()) {
            BrandEntity brandEntityUpdated = brandRepository.save(brandEntity);
            brandUpdated = serviceFromEntity(brandEntityUpdated);
        }
        return brandUpdated;
    }

    @Override
    public Brand delete(Integer id) {
        Brand brand = brandRepository.findById(id).map(BrandServiceImpl::serviceFromEntity).orElse(null);
        if (brand != null) {
            brandRepository.deleteById(id);
        }
        return brand;
    }

    @Override
    public Brand add(Brand brand) {
        BrandEntity brandEntity = entityFromService(brand);
        return serviceFromEntity(brandRepository.save(brandEntity));
    }

    private static Brand serviceFromEntity(BrandEntity brandEntity) {
        Brand brand;
        brand = new Brand(
                brandEntity.getId(),
                brandEntity.getName(),
                brandEntity.getWarranty(),
                brandEntity.getCountry()
        );
        return brand;
    }

    private static BrandEntity entityFromService(Brand brand) {
        return new BrandEntity(
                brand.getId(),
                brand.getName(),
                brand.getWarranty(),
                brand.getCountry()
        );
    }
}
