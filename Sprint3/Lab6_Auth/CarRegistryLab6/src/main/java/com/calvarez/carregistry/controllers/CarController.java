package com.calvarez.carregistry.controllers;

import com.calvarez.carregistry.controllers.dtos.CarRequest;
import com.calvarez.carregistry.controllers.dtos.CarResponse;
import com.calvarez.carregistry.services.BrandService;
import com.calvarez.carregistry.services.CarService;
import com.calvarez.carregistry.services.Greetings;
import com.calvarez.carregistry.services.model.Car;
import com.calvarez.carregistry.services.model.CarInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@RestController
@Slf4j
@RequestMapping("/car")
public class CarController extends Controller{

    @GetMapping("/")
    public ResponseEntity<?> getGreetings() throws ExecutionException, InterruptedException {
        System.out.println("Bienvenida desde el RESTCONTROLLER: " + greetings.hellow);
        return carService.getAll().thenApply(cars -> ResponseEntity.ok(cars.stream().map(this::dtoFromService))).get();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCar(@PathVariable Integer id) {
        try {
            Car car = carService.get(id);
            if (car != null) {
                CarResponse carResponse = dtoFromService(car);
                return ResponseEntity.ok(carResponse);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Something wrong getting a car", e);
            return ResponseEntity.internalServerError().body("Some error has occurred, sorry");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modifyCar(@PathVariable Integer id, @RequestBody CarRequest carRequest) {
        try {
            CarInput car = serviceFromDto(id, carRequest);
            Car carUpdated = carService.update(car);
            if (carUpdated == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(dtoFromService(carUpdated));
        } catch (Exception e) {
            log.error("Something wrong updating a car", e);
            return ResponseEntity.internalServerError().body("Some error has occurred, sorry");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable Integer id) {
        try {
            Car car = carService.delete(id);
            if (car != null) {
                CarResponse carResponse = dtoFromService(car);
                return ResponseEntity.ok(carResponse);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Something wrong deleting a car", e);
            return ResponseEntity.internalServerError().body("Some error has occurred, sorry");
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> addCar(@RequestBody CarRequest carRequest) {
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
}
