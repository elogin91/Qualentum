package com.calvarez.carregistry.repositories;

import com.calvarez.carregistry.repositories.entities.CarEntity;

public interface CarRepository {
    CarEntity get(int id);
    void add(CarEntity carEntity);
}
