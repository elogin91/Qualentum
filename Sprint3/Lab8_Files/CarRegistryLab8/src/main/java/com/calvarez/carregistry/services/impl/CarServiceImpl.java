package com.calvarez.carregistry.services.impl;

import com.calvarez.carregistry.repositories.BrandRepository;
import com.calvarez.carregistry.repositories.CarRepository;
import com.calvarez.carregistry.repositories.entities.BrandEntity;
import com.calvarez.carregistry.repositories.entities.CarEntity;
import com.calvarez.carregistry.services.CarService;
import com.calvarez.carregistry.services.model.Car;
import com.calvarez.carregistry.services.model.CarInput;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    private final BrandRepository brandRepository;

    private final MapperService mapperService;

    public CarServiceImpl(CarRepository carRepository, BrandRepository brandRepository, MapperService mapperService) {
        this.carRepository = carRepository;
        this.brandRepository = brandRepository;
        this.mapperService = mapperService;
    }

    @Override
    public Optional <Car> get(Integer id) {
        return carRepository.findById(id).map(mapperService::serviceFromEntity);
    }

    @Override
    public CompletableFuture<List<Car>> getAll() {
        return CompletableFuture.completedFuture(carRepository.findAll().stream().map(mapperService::serviceFromEntity).toList());
    }

    @Override
    public Optional<Car> update(CarInput car) {
        Optional<BrandEntity> brand = brandRepository.findById(car.getBrandId());
        Optional<Car> carUpdated = Optional.empty();
        if (carRepository.findById(car.getId()).isPresent() && brand.isPresent()) {
            CarEntity carEntity = mapperService.entityFromService(car, brand.get());
            CarEntity carEntityUpdated = carRepository.save(carEntity);
            carUpdated = Optional.of(mapperService.serviceFromEntity(carEntityUpdated));
        }
        return carUpdated;
    }

    @Override
    public Optional <Car> delete(Integer id) {
        Optional <Car> car = carRepository.findById(id).map(mapperService::serviceFromEntity);
        if (car.isPresent()) {
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
