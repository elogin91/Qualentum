package com.calvarez.carregistry.repositories;

import com.calvarez.carregistry.repositories.entities.CarEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CarRepositoryInMemory implements CarRepository {
    private Map<Integer, CarEntity> cars = new HashMap<>();
    private Integer maxId = 0;

    public CarRepositoryInMemory() {
        this.add(new CarEntity(0, "Ford", "Fiesta", 17236, 1500.89, 2015, "It´s notably used", "Blue", "Gasoil", 5));
    }

    @Override
    public CarEntity get(int id) {
        CarEntity carEntity = cars.get(id);
        return carEntity;
    }

    @Override
    public CarEntity add(CarEntity carEntity) {
        carEntity.setId(getNextId());
        cars.put(carEntity.getId(), carEntity);
        return carEntity;
    }

    @Override
    public CarEntity update(CarEntity car) {
        if (cars.get(car.getId()) == null) {
            return null;
        }
        cars.put(car.getId(), car);
        return car;
    }

    @Override
    public CarEntity delete(Integer id) {
        return cars.remove(id);
    }

    private Integer getNextId() {
        return ++maxId;
    }
}
