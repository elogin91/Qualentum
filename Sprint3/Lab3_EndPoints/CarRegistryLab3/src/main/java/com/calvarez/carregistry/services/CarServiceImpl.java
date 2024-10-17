package com.calvarez.carregistry.services;

import com.calvarez.carregistry.repositories.CarRepository;
import com.calvarez.carregistry.repositories.entities.CarEntity;
import com.calvarez.carregistry.services.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CarServiceImpl implements CarService{
    @Autowired
    private CarRepository carRepository;

    @Override
    public Car get(int id) {
         CarEntity carEntity = carRepository.get(id);
         Car car = null;
        if(carEntity != null){
           car = new Car(
                    carEntity.getId(),
                    carEntity.getBrand(),
                    carEntity.getModel(),
                    carEntity.getMilleage(),
                    carEntity.getPrice(),
                    carEntity.getYear(),
                    carEntity.getDescription(),
                    carEntity.getColour(),
                    carEntity.getFuelType(),
                    carEntity.getNumDoors()
            );
        }
        return car;
    }

    @Override
    public Car update(Car car) {
        return null;
    }
}
