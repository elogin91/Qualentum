package com.calvarez.carregistry.services;

import com.calvarez.carregistry.services.model.Car;
import com.calvarez.carregistry.services.model.CarInput;

import java.util.List;

public interface CarService {
    List<Car> getAll();

    Car get(Integer id);

    Car update(CarInput item);

    Car delete(Integer id);

    Car add(CarInput item) throws BrandNotFoundException;

    class BrandNotFoundException extends Exception {
        public BrandNotFoundException() {
            super("Brand not found");
        }
    }
}