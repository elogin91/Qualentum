package com.calvarez.carregistry.services.impl;

import com.calvarez.carregistry.repositories.BrandRepository;
import com.calvarez.carregistry.repositories.CarRepository;
import com.calvarez.carregistry.repositories.entities.BrandEntity;
import com.calvarez.carregistry.repositories.entities.CarEntity;
import com.calvarez.carregistry.services.CarService;
import com.calvarez.carregistry.services.model.Brand;
import com.calvarez.carregistry.services.model.Car;
import com.calvarez.carregistry.services.model.CarInput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {
    private static final int BRAND_ID1 = 1;
    private static final int BRAND_ID2 = 2;
    private static final int BRAND_ID3 = 3;
    private static final int CAR_ID1 = 1;
    private static final int CAR_ID2 = 2;
    private static final int CAR_ID3 = 3;

    @InjectMocks
    private BrandServiceImpl brandService;
    @InjectMocks
    private CarServiceImpl carService;

    @Mock
    private BrandRepository brandRepository;
    @Mock
    private MapperService mapperService;
    @Mock
    private CarRepository carRepository;

    @Test
    void getReturnsCarWhenItExists(){
        CarEntity carEntity = aCarEntity();
        when(carRepository.findById(CAR_ID1)).thenReturn(Optional.of(carEntity));
        Car car = aCar();
        when(mapperService.serviceFromEntity(carEntity)).thenReturn(car);

        Optional<Car> result = carService.get(CAR_ID1);

        assertEquals(Optional.of(car), result);
    }

    @Test
    void getReturnsCarWhenItDoesntExist(){
        when(carRepository.findById(CAR_ID1)).thenReturn(Optional.empty());

        Optional<Car> result = carService.get(CAR_ID1);

        assertTrue(result.isEmpty());
    }

    @Test
    void getReturnsAllCars() throws ExecutionException, InterruptedException {
        List<CarEntity> listOFCarsEntity = aListOfCarsEntity();
        when(carRepository.findAll()).thenReturn(listOFCarsEntity);
        List<Car> listOfCars = aListOfCars();
        when(mapperService.serviceFromEntity(listOFCarsEntity.get(0))).thenReturn(listOfCars.get(0));
        when(mapperService.serviceFromEntity(listOFCarsEntity.get(1))).thenReturn(listOfCars.get(1));
        when(mapperService.serviceFromEntity(listOFCarsEntity.get(2))).thenReturn(listOfCars.get(2));

        List<Car> result = carService.getAll().get();

        assertEquals(listOfCars, result);
    }

    @Test
    void updateReturnsCarWhenItExists(){
        CarInput carInput = aCarInput();
        BrandEntity brandEntity = aBrandEntity();
        when(brandRepository.findById(BRAND_ID1)).thenReturn(Optional.of(brandEntity));
        CarEntity carEntityInput = aCarEntity();
        CarEntity carEntityPersisted = aCarEntity2();
        when(carRepository.findById(carEntityInput.getId())).thenReturn(Optional.of(carEntityPersisted));
        when(mapperService.entityFromService(carInput, brandEntity)).thenReturn(carEntityInput);
        CarEntity carEntityUpdated = aCarEntity3();
        when(carRepository.save(carEntityInput)).thenReturn(carEntityUpdated);
        Car carOutput = aCar3();
        when(mapperService.serviceFromEntity(carEntityUpdated)).thenReturn(carOutput);

        Optional<Car> result = carService.update(carInput);

        assertEquals(Optional.of(carOutput), result);
    }

    @Test
    void updateReturnsCarWhenItDoesntExist(){
        CarInput carInput = aCarInput();
        BrandEntity brandEntity = aBrandEntity();
        when(brandRepository.findById(BRAND_ID1)).thenReturn(Optional.of(brandEntity));
        CarEntity carEntityInput = aCarEntity();
        when(carRepository.findById(carEntityInput.getId())).thenReturn(Optional.empty());

        Optional<Car> result = carService.update(carInput);

        assertTrue(result.isEmpty());
    }

    @Test
    void deleteReturnsCarWhenItExists(){
        CarEntity carEntity = aCarEntity();
        when(carRepository.findById(CAR_ID1)).thenReturn(Optional.of(carEntity));
        Car car = aCar();
        when(mapperService.serviceFromEntity(carEntity)).thenReturn(car);

        Optional<Car> result = carService.delete(CAR_ID1);

        verify(carRepository).deleteById(CAR_ID1);
        assertEquals(Optional.of(car), result);
    }

    @Test
    void deleteReturnsCarWhenItDoesntExists(){
        when(carRepository.findById(CAR_ID1)).thenReturn(Optional.empty());

        Optional<Car> result = carService.delete(CAR_ID1);

        verify(carRepository, never()).deleteById(CAR_ID1);

        assertTrue(result.isEmpty());
    }

    @Test
    void createReturnsCarWhenBrandExists() throws CarService.BrandNotFoundException {
        CarInput carInput = aCarInput();
        BrandEntity brandEntity = aBrandEntity();
        when(brandRepository.findById(BRAND_ID1)).thenReturn(Optional.of(brandEntity));
        CarEntity carEntityInput = aCarEntity();
        when(mapperService.entityFromService(carInput, brandEntity)).thenReturn(carEntityInput);
        CarEntity carEntityPersisted = aCarEntity2();
        when(carRepository.save(carEntityInput)).thenReturn(carEntityPersisted);
        Car carCreated = aCar();
        when(mapperService.serviceFromEntity(carEntityPersisted)).thenReturn(carCreated);

        Car result = carService.add(carInput);

        assertEquals(carCreated, result);
    }

    @Test
    void createReturnsCarWhenBrandDoesntExists() {
        CarInput carInput = aCarInput();
        when(brandRepository.findById(BRAND_ID1)).thenReturn(Optional.empty());

        assertThrows(CarService.BrandNotFoundException.class, () -> carService.add(carInput));

    }

    private List<Car> aListOfCars() {
        List<Car> listOfCars = new ArrayList<>();
        listOfCars.add(aCar());
        listOfCars.add(aCar2());
        listOfCars.add(aCar3());
        return listOfCars;
    }

    private List<CarEntity> aListOfCarsEntity() {
        List<CarEntity> listOFCarsEntity = new ArrayList<>();
        listOFCarsEntity.add(aCarEntity());
        listOFCarsEntity.add(aCarEntity2());
        listOFCarsEntity.add(aCarEntity3());
        return listOFCarsEntity;
    }

    private CarInput aCarInput() {
        return new CarInput(CAR_ID1, 1, "Clio", 209000, 10000.00, 2015, "Semi-nuevo", "Rojo", "Diesel", 5);
    }
    private Car aCar() {
        return new Car(CAR_ID1, aBrand(), "Clio", 209000, 10000.00, 2015, "Semi-nuevo", "Rojo", "Diesel", 5);
    }
    private Car aCar2() {
        return new Car(CAR_ID2, aBrand2(), "Clio", 209000, 10000.00, 2015, "Semi-nuevo", "Rojo", "Diesel", 5);
    }
    private Car aCar3() {
        return new Car(CAR_ID3, aBrand3(), "Clio", 209000, 10000.00, 2015, "Semi-nuevo", "Rojo", "Diesel", 5);
    }

    private Brand aBrand() {
        return new Brand(BRAND_ID1, "Renault", 2, "Spain");
    }
    private Brand aBrand2() {
        return new Brand(BRAND_ID2, "Renault", 2, "Spain");
    }
    private Brand aBrand3() {
        return new Brand(BRAND_ID3, "Renault", 2, "Spain");
    }

    private CarEntity aCarEntity() {
        return new CarEntity(CAR_ID1, aBrandEntity(), "Clio", 209000, 10000.00, 2015, "Semi-nuevo", "Rojo", "Diesel", 5);
    }
    private CarEntity aCarEntity2() {
        return new CarEntity(CAR_ID2, aBrandEntity2(), "Clio", 209000, 10000.00, 2015, "Semi-nuevo", "Rojo", "Diesel", 5);
    }
    private CarEntity aCarEntity3() {
        return new CarEntity(CAR_ID3, aBrandEntity3(), "Clio", 209000, 10000.00, 2015, "Semi-nuevo", "Rojo", "Diesel", 5);
    }

    private BrandEntity aBrandEntity() {
        return new BrandEntity(BRAND_ID1, "Renault", 2, "Spain");
    }
    private BrandEntity aBrandEntity2() {
        return new BrandEntity(BRAND_ID2, "Renault", 2, "Spain");
    }
    private BrandEntity aBrandEntity3() {
        return new BrandEntity(BRAND_ID3, "Renault", 2, "Spain");
    }

}
