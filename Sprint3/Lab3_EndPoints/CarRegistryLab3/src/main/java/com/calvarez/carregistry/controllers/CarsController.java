package com.calvarez.carregistry.controllers;

import com.calvarez.carregistry.controllers.dtos.CarRequest;
import com.calvarez.carregistry.controllers.dtos.CarResponse;
import com.calvarez.carregistry.repositories.entities.CarEntity;
import com.calvarez.carregistry.services.CarService;
import com.calvarez.carregistry.services.Greetings;
import com.calvarez.carregistry.services.model.Car;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@Slf4j
public class CarsController {

    @Autowired
    private CarService carService;

    @Autowired
    private Greetings greetings;

    @GetMapping("/")
    public ResponseEntity<?> getGreetings() {
        System.out.println("Bienvenida desde el RESTCONTROLLER: " + greetings.hellow);
        return  ResponseEntity.ok(200 + " Bienvenida desde el RESTCONTROLLER: "+ greetings.hellow);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCar(@PathVariable Integer id){
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
    public ResponseEntity<?> modifyCar(@PathVariable Integer id, @RequestBody CarRequest carRequest){
        try{
            Car car = new Car(
                    id,
                    carRequest.getBrand(),
                    carRequest.getModel(),
                    carRequest.getMilleage(),
                    carRequest.getPrice(),
                    carRequest.getYear(),
                    carRequest.getDescription(),
                    carRequest.getColour(),
                    carRequest.getFuelType(),
                    carRequest.getNumDoors()
            );
            Car carUpdated = carService.update(car);
            if (carUpdated == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(dtoFromService(carUpdated));
        } catch (Exception e) {
            log.error("Something wrong getting a car", e);
            return ResponseEntity.internalServerError().body("Some error has occurred, sorry");
        }
    }
/*
    @PostMapping("/")
    public ResponseEntity<?> addingCar(@RequestBody CarRequest carRequest){
        boolean isCarAdded = true;
        if(isCarAdded){
            return ResponseEntity.ok("Added new car: " + carRequest);
        }
        return  ResponseEntity.status(500).build();
    }

   @PutMapping("/{id}")
    public ResponseEntity<?> modifingCar(@PathVariable Integer id, @RequestBody CarRequest carRequest){
        boolean isCarModified = true;
        CarEntity carEntity = new CarEntity(1, "Ford","Fiesta", 2323123, 14000.99, 2010, "Good condiction", "Red", "Diesel", 5);
        if(carEntity.getId() != id){
            return  ResponseEntity.status(404).build();
        }
        if(isCarModified){
            return ResponseEntity.ok("Modified a car: " + id);
        }
        return  ResponseEntity.status(500).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletingCar(@PathVariable Integer id){
        boolean isCarDeleted = true;
        CarRequest carToDelete = new CarRequest();
        if(isCarDeleted){
            return ResponseEntity.ok("Deleted a car: " + id);
        }
        return  ResponseEntity.status(404).build();
    }
 */

    private CarResponse dtoFromService(Car car) {
        return new CarResponse(
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
