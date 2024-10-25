package com.calvarez.carregistry.services.impl;

import com.calvarez.carregistry.repositories.entities.BrandEntity;
import com.calvarez.carregistry.repositories.entities.CarEntity;
import com.calvarez.carregistry.services.model.Brand;
import com.calvarez.carregistry.services.model.Car;
import com.calvarez.carregistry.services.model.CarInput;
import org.springframework.stereotype.Service;

@Service
public class MapperService {
    Car serviceFromEntity(CarEntity carEntity) {
        Car car;
        car = new Car(
                carEntity.getId(),
                serviceFromEntity(carEntity.getBrand()),
                carEntity.getModel(),
                carEntity.getMilleage(),
                carEntity.getPrice(),
                carEntity.getYear(),
                carEntity.getDescription(),
                carEntity.getColour(),
                carEntity.getFuelType(),
                carEntity.getNumDoors()
        );
        return car;
    }

     CarEntity entityFromService(CarInput car, BrandEntity brand) {
        return new CarEntity(
                car.getId(),
                brand,
                car.getModel(),
                car.getMilleage(),
                car.getPrice(),
                car.getYear(),
                car.getDescription(),
                car.getColour(),
                car.getFuelType(),
                car.getNumDoors()
        );
    }

     Brand serviceFromEntity(BrandEntity brandEntity) {
        Brand brand;
        brand = new Brand(
                brandEntity.getId(),
                brandEntity.getName(),
                brandEntity.getWarranty(),
                brandEntity.getCountry()
        );
        return brand;
    }

     BrandEntity entityFromService(Brand brand) {
        return new BrandEntity(
                brand.getId(),
                brand.getName(),
                brand.getWarranty(),
                brand.getCountry()
        );
    }
}
