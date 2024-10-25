package com.calvarez.carregistry.services.impl;

import com.calvarez.carregistry.repositories.BrandRepository;
import com.calvarez.carregistry.repositories.CarRepository;
import com.calvarez.carregistry.repositories.entities.BrandEntity;
import com.calvarez.carregistry.repositories.entities.CarEntity;
import com.calvarez.carregistry.services.CarService;
import com.calvarez.carregistry.services.model.Car;
import com.calvarez.carregistry.services.model.CarInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private MapperService mapperService;


    @Override
    public Car get(Integer id) {
        return carRepository.findById(id).map(mapperService::serviceFromEntity).orElse(null);
    }

    @Override
    public List<Car> getAll() {
        return carRepository.findAll().stream().map(mapperService::serviceFromEntity).toList();
    }

    @Override
    public Car update(CarInput car) {
        Optional<BrandEntity> brand = brandRepository.findById(car.getBrandId());
        Car carUpdated = null;
        if (carRepository.findById(car.getId()).isPresent() && brand.isPresent()) {
            CarEntity carEntity = mapperService.entityFromService(car, brand.get());
            CarEntity carEntityUpdated = carRepository.save(carEntity);
            carUpdated = mapperService.serviceFromEntity(carEntityUpdated);
        }
        return carUpdated;
    }

    @Override
    public Car delete(Integer id) {
        Car car = carRepository.findById(id).map(mapperService::serviceFromEntity).orElse(null);
        if (car != null) {
            carRepository.deleteById(id);
        }
        return car;
    }

    @Override
    public Car add(CarInput car) throws BrandNotFoundException {
        BrandEntity brand = brandRepository.findById(car.getBrandId()).orElseThrow(BrandNotFoundException::new);
        CarEntity carEntity = mapperService.entityFromService(car, brand);
        return mapperService.serviceFromEntity(carRepository.save(carEntity));
    }
}
