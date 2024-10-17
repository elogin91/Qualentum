package com.calvarez.carregistry.services;

import com.calvarez.carregistry.services.model.Car;

public interface CarService {
    Car get(int id);
    Car update(Car car);
}
