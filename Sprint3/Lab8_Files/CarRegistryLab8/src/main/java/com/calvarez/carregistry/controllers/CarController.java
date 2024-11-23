package com.calvarez.carregistry.controllers;

import com.calvarez.carregistry.controllers.dtos.CarRequest;
import com.calvarez.carregistry.controllers.dtos.CarResponse;
import com.calvarez.carregistry.services.BrandService;
import com.calvarez.carregistry.services.CarService;
import com.calvarez.carregistry.services.model.Car;
import com.calvarez.carregistry.services.model.CarInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;


@RestController
@Slf4j
@RequestMapping("/car")
public class CarController extends BaseController {

    protected CarController(CarService carService, BrandService brandService) {
        super(carService, brandService);
    }

    @GetMapping("/")
    public ResponseEntity<Stream<CarResponse>> getAllCars() throws ExecutionException, InterruptedException {
        return carService.getAll().thenApply(cars -> ResponseEntity.ok(cars.stream().map(this::dtoFromService))).get();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCar(@PathVariable Integer id) {
        try {
            Optional<Car> car = carService.get(id);
            if (car.isPresent()) {
                CarResponse carResponse = dtoFromService(car.get());
                return ResponseEntity.ok(carResponse);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Something wrong getting a car", e);
            return ResponseEntity.internalServerError().body(ERROR_MESSAGE);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> modifyCar(@PathVariable Integer id, @RequestBody CarRequest carRequest) {
        try {
            CarInput car = serviceFromDto(id, carRequest);
            Optional <Car> carUpdated = carService.update(car);
            if (carUpdated.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(dtoFromService(carUpdated.get()));
        } catch (Exception e) {
            log.error("Something wrong updating a car", e);
            return ResponseEntity.internalServerError().body(ERROR_MESSAGE);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCar(@PathVariable Integer id) {
        try {
            Optional<Car> car = carService.delete(id);
            if (car.isPresent()) {
                CarResponse carResponse = dtoFromService(car.get());
                return ResponseEntity.ok(carResponse);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Something wrong deleting a car", e);
            return ResponseEntity.internalServerError().body(ERROR_MESSAGE);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Object> addCar(@RequestBody CarRequest carRequest) {
        try {
            CarInput car = serviceFromDto(null, carRequest);
            Car carAdded = carService.add(car);
            return ResponseEntity.ok(dtoFromService(carAdded));
        } catch (CarService.BrandNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("Something wrong adding a car", e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
    @PostMapping("/importAll")
    public ResponseEntity<String> importAllCars(@RequestParam(value="file") MultipartFile file) {
        if (file.isEmpty()) {
            log.error("File is empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if(file.getOriginalFilename() == null || !file.getOriginalFilename().endsWith(".csv")) {
            log.error("File type is invalid");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if(carService.importCAr(file) != null) {
            return ResponseEntity.ok("Coches añadidos correctamente");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/exportAll")
    public ResponseEntity<Object> exportAllCars() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "car.csv");

        byte[] csvBytes = carService.allCarsCsv().getBytes();

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }
}
