package com.calvarez.carregistry.services;

import com.calvarez.carregistry.repositories.CarRepository;
import com.calvarez.carregistry.repositories.entities.CarEntity;
import com.calvarez.carregistry.services.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService{
    @Autowired
    private CarRepository carRepository;

    @Override
    public Car get(int id) {
         CarEntity carEntity = carRepository.get(id);
         Car car = null;
        if(carEntity != null){
            car = serviceFromEntity(carEntity);
        }
        return car;
    }

    @Override
    public Car update(Car car) {
        CarEntity carEntity = entityFromService(car);
        CarEntity carEntityUpdated = carRepository.update(carEntity);
        Car carUpdated = null;
        if(carEntityUpdated !=null) {
            carUpdated = serviceFromEntity(carEntityUpdated);
        }
        return carUpdated;
    }

    @Override
    public Car delete(Integer id) {
        CarEntity carEntity = carRepository.delete(id);
        Car car = null;
        if(carEntity != null){
            car = serviceFromEntity(carEntity);
        }
        return car;
    }

    @Override
    public Car add(Car car) {
        CarEntity carToDelete = entityFromService(car);
        CarEntity carEntity = carRepository.add(carToDelete);
        return serviceFromEntity(carEntity);
    }

    private static Car serviceFromEntity(CarEntity carEntity) {
        Car car;
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
        return car;
    }

    private static CarEntity entityFromService(Car car) {
        CarEntity carEntity = new CarEntity(
                car.getId(),
                car.getBrand(),
                car.getModel(),
                car.getMilleage(),
                car.getPrice(),
                car.getYear(),
                car.getDescription(),
                car.getColour(),
                car.getFuelType(),
                car.getNumDoors()
        );
        return carEntity;
    }
}
