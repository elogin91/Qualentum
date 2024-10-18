package com.calvarez.carregistry.repositories;

import com.calvarez.carregistry.repositories.entities.CarEntity;

public interface CarRepository {
    CarEntity get(int id);
    CarEntity add(CarEntity carEntity);
    CarEntity update(CarEntity car);
    CarEntity delete(Integer id);
}
