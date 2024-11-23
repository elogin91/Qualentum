package com.calvarez.carregistry.services.impl;

import com.calvarez.carregistry.repositories.BrandRepository;
import com.calvarez.carregistry.repositories.CarRepository;
import com.calvarez.carregistry.repositories.entities.BrandEntity;
import com.calvarez.carregistry.repositories.entities.CarEntity;
import com.calvarez.carregistry.services.CarService;
import com.calvarez.carregistry.services.model.Car;
import com.calvarez.carregistry.services.model.CarInput;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    private final BrandRepository brandRepository;

    private final MapperService mapperService;

    private final String[] HEADERS = {"id", "brand", "model", "milleage", "price", "year", "description", "colour", "fuelType","numDoors"};

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

    @Override
    public List<Car> importCAr(MultipartFile file) {
        List<CarEntity> carEntityList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));
             CSVParser csvParser = new CSVParser(reader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                CarEntity carEntity = new CarEntity();
                carEntity.setBrand(brandRepository.findById(Integer.valueOf(csvRecord.get("brandID"))).orElseThrow());
                carEntity.setModel(csvRecord.get("model"));
                carEntity.setMilleage(Integer.valueOf(csvRecord.get("milleage")));
                carEntity.setPrice(Double.valueOf(csvRecord.get("price")));
                carEntity.setYear(Integer.valueOf(csvRecord.get("year")));
                carEntity.setDescription(csvRecord.get("description"));
                carEntity.setColour(csvRecord.get("colour"));
                carEntity.setFuelType(csvRecord.get("fuelType"));
                carEntity.setNumDoors(Integer.valueOf(csvRecord.get("numDoors")));

                carEntityList.add(carEntity);
            }
            carEntityList = carRepository.saveAll(carEntityList);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return carEntityList.stream().map(mapperService::serviceFromEntity).toList();
    }

    @Override
    public String allCarsCsv() {
        List<CarEntity> carEntityList = carRepository.findAll();
        StringBuilder csvContent = new StringBuilder();
        csvContent.append(String.join(",", HEADERS)).append("\n");

        for (CarEntity carEntity : carEntityList) {
            csvContent.append(carEntity.getId()).append(",");
            csvContent.append(carEntity.getBrand().getName()).append(",");
            csvContent.append(carEntity.getModel()).append(",");
            csvContent.append(carEntity.getMilleage()).append(",");
            csvContent.append(carEntity.getPrice()).append(",");
            csvContent.append(carEntity.getYear()).append(",");
            csvContent.append(carEntity.getDescription()).append(",");
            csvContent.append(carEntity.getColour()).append(",");
            csvContent.append(carEntity.getFuelType()).append(",");
            csvContent.append(carEntity.getNumDoors()).append("\n");
        }

        return csvContent.toString();
    }
}
