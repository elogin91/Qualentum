package com.calvarez.carregistry.services.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.calvarez.carregistry.repositories.entities.BrandEntity;
import com.calvarez.carregistry.repositories.entities.CarEntity;
import com.calvarez.carregistry.services.model.Brand;
import com.calvarez.carregistry.services.model.Car;
import com.calvarez.carregistry.services.model.CarInput;
import org.junit.jupiter.api.Test;

class MapperServiceTest {

    private final MapperService mapperService = new MapperService();

    @Test
    void testServiceFromEntityCar() {

        BrandEntity brandEntity = aBrandEntity();
        CarEntity carEntity = new CarEntity(1, brandEntity, "Corolla", 50000, 15000.00, 2020, "Sedan", "Red", "Gasoline", 4);
        Car car = mapperService.serviceFromEntity(carEntity);

        assertNotNull(car);
        assertEquals(carEntity.getId(), car.getId());
        assertEquals(carEntity.getModel(), car.getModel());
        assertEquals(carEntity.getMilleage(), car.getMilleage());
        assertEquals(carEntity.getPrice(), car.getPrice());
        assertEquals(carEntity.getYear(), car.getYear());
        assertEquals(carEntity.getDescription(), car.getDescription());
        assertEquals(carEntity.getColour(), car.getColour());
        assertEquals(carEntity.getFuelType(), car.getFuelType());
        assertEquals(carEntity.getNumDoors(), car.getNumDoors());


        assertNotNull(car.getBrand());
        assertEquals(carEntity.getBrand().getId(), car.getBrand().getId());
        assertEquals(carEntity.getBrand().getName(), car.getBrand().getName());
    }

    @Test
    void testEntityFromServiceCar() {

        BrandEntity brandEntity = aBrandEntity();
        CarInput carInput = new CarInput(1, 1, "Sedan", 100000, 10000.00, 2015, "Tiene un bollo","Red", "Gasoline", 4);

        CarEntity carEntity = mapperService.entityFromService(carInput, brandEntity);

        assertNotNull(carEntity);
        assertEquals(carInput.getId(), carEntity.getId());
        assertEquals(carInput.getModel(), carEntity.getModel());
        assertEquals(carInput.getMilleage(), carEntity.getMilleage());
        assertEquals(carInput.getPrice(), carEntity.getPrice());
        assertEquals(carInput.getYear(), carEntity.getYear());
        assertEquals(carInput.getDescription(), carEntity.getDescription());
        assertEquals(carInput.getColour(), carEntity.getColour());
        assertEquals(carInput.getFuelType(), carEntity.getFuelType());
        assertEquals(carInput.getNumDoors(), carEntity.getNumDoors());
    }

    @Test
    void testServiceFromEntityBrand() {

        BrandEntity brandEntity = aBrandEntity();

        Brand brand = mapperService.serviceFromEntity(brandEntity);

        assertNotNull(brand);
        assertEquals(brandEntity.getId(), brand.getId());
        assertEquals(brandEntity.getName(), brand.getName());
        assertEquals(brandEntity.getWarranty(), brand.getWarranty());
        assertEquals(brandEntity.getCountry(), brand.getCountry());
    }

    @Test
    void testEntityFromServiceBrand() {

        Brand brand = new Brand(1, "Toyota", 5, "Japan");

        BrandEntity brandEntity = mapperService.entityFromService(brand);

        assertNotNull(brandEntity);
        assertEquals(brand.getId(), brandEntity.getId());
        assertEquals(brand.getName(), brandEntity.getName());
        assertEquals(brand.getWarranty(), brandEntity.getWarranty());
        assertEquals(brand.getCountry(), brandEntity.getCountry());
    }

    private static BrandEntity aBrandEntity() {
        return new BrandEntity(1, "Toyota", 5, "Japan");
    }
}

