package com.calvarez.carregistry.services.impl;

import com.calvarez.carregistry.repositories.BrandRepository;
import com.calvarez.carregistry.repositories.CarRepository;
import com.calvarez.carregistry.repositories.entities.CarEntity;
import com.calvarez.carregistry.services.CarService;
import com.calvarez.carregistry.services.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public Car get(Integer id) {
        return carRepository.findById(id).map(CarServiceImpl::serviceFromEntity).orElse(null);
    }

    @Override
    public List<Car> getAll() {
        return carRepository.findAll().stream().map(CarServiceImpl::serviceFromEntity).toList();
    }

    @Override
    public Car update(Car car) {
        CarEntity carEntity = entityFromService(car);
        Car carUpdated = null;
        if (carRepository.findById(carEntity.getId()).isPresent() && brandRepository.findById(carEntity.getBrand()).isPresent()) {
            CarEntity carEntityUpdated = carRepository.save(carEntity);
            carUpdated = serviceFromEntity(carEntityUpdated);
        }
        return carUpdated;
    }

    @Override
    public Car delete(Integer id) {
        Car car = carRepository.findById(id).map(CarServiceImpl::serviceFromEntity).orElse(null);
        if (car != null) {
            carRepository.deleteById(id);
        }
        return car;
    }

    @Override
    public Car add(Car car) throws Exception {
        CarEntity carEntity = entityFromService(car);
        if(brandRepository.findById(carEntity.getBrand()).isPresent()){
            return serviceFromEntity(carRepository.save(carEntity));
        }
        throw new Exception("The brand doesn't  exist");
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
        return new CarEntity(
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
    }
}
