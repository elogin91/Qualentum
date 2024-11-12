package com.calvarez.carregistry.controllers;

import com.calvarez.carregistry.controllers.dtos.BrandRequest;
import com.calvarez.carregistry.controllers.dtos.BrandResponse;
import com.calvarez.carregistry.controllers.dtos.CarRequest;
import com.calvarez.carregistry.controllers.dtos.CarResponse;
import com.calvarez.carregistry.services.BrandService;
import com.calvarez.carregistry.services.CarService;
import com.calvarez.carregistry.services.Greetings;
import com.calvarez.carregistry.services.model.Brand;
import com.calvarez.carregistry.services.model.Car;
import com.calvarez.carregistry.services.model.CarInput;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class Controller {

    @Autowired
    protected CarService carService;

    @Autowired
    protected Greetings greetings;

    @Autowired
    protected BrandService brandService;

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
        CarInput car = new CarInput(
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
        return car;
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
