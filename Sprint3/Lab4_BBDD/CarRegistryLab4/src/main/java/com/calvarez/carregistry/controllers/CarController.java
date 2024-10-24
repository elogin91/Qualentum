package com.calvarez.carregistry.controllers;

import com.calvarez.carregistry.controllers.dtos.CarRequest;
import com.calvarez.carregistry.controllers.dtos.CarResponse;
import com.calvarez.carregistry.services.CarService;
import com.calvarez.carregistry.services.Greetings;
import com.calvarez.carregistry.services.model.Car;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private Greetings greetings;

    @GetMapping("/")
    public ResponseEntity<?> getGreetings() {
        System.out.println("Bienvenida desde el RESTCONTROLLER: " + greetings.hellow);

        return ResponseEntity.ok(carService.getAll().stream().map(this::dtoFromService));
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
            Car car = serviceFromDto(id, carRequest);
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
            Car car = serviceFromDto(null, carRequest);
            Car carAdded = carService.add(car);
/*            if(carAdded == null){
                return ResponseEntity.badRequest().body("The brand doesn't exist.");
            }*/
            return ResponseEntity.ok(dtoFromService(carAdded));
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

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

    private static Car serviceFromDto(Integer id, CarRequest carRequest) {
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
        return car;
    }
}
