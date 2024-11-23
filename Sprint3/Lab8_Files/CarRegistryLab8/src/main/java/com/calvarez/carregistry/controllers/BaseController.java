package com.calvarez.carregistry.controllers;

import com.calvarez.carregistry.controllers.dtos.BrandRequest;
import com.calvarez.carregistry.controllers.dtos.BrandResponse;
import com.calvarez.carregistry.controllers.dtos.CarRequest;
import com.calvarez.carregistry.controllers.dtos.CarResponse;
import com.calvarez.carregistry.services.BrandService;
import com.calvarez.carregistry.services.CarService;

import com.calvarez.carregistry.services.model.Brand;
import com.calvarez.carregistry.services.model.Car;
import com.calvarez.carregistry.services.model.CarInput;


public abstract class BaseController {

    protected static final String ERROR_MESSAGE = "Some error has occurred, sorry";
    protected CarService carService;
    protected BrandService brandService;

    protected BaseController(CarService carService, BrandService brandService) {
        this.carService = carService;
        this.brandService = brandService;
    }

    protected CarResponse dtoFromService(Car car) {
        return new CarResponse(
                car.getId(),
                dtoFromService(car.getBrand()),
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

    protected CarInput serviceFromDto(Integer id, CarRequest carRequest) {
        return new CarInput(
                id,
                carRequest.getBrand(),
                carRequest.getModel(),
                carRequest.getMilleage(),
                carRequest.getPrice(),
                carRequest.getYear(),
                carRequest.getDescription(),
                carRequest.getColour(),
                carRequest.getFuelType(),
                carRequest.getNumDoors()
        );
    }
    protected BrandResponse dtoFromService(Brand brand) {
        return new BrandResponse(
                brand.getId(),
                brand.getName(),
                brand.getWarranty(),
                brand.getCountry()
        );
    }

    protected Brand serviceFromDto(Integer id, BrandRequest brandRequest) {
        return new Brand(
                id,
                brandRequest.getName(),
                brandRequest.getWarranty(),
                brandRequest.getCountry()

        );
    }
}
