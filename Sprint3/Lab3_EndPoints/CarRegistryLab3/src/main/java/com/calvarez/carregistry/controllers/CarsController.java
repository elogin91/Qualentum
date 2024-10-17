package com.calvarez.carregistry.controllers;

import com.calvarez.carregistry.controllers.dtos.CarRequest;
import com.calvarez.carregistry.services.Greetings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarsController {

    @Autowired
    Greetings greetings;

    @GetMapping("/cars")
    public void getCarData() {

        System.out.println("Bienvenida desde el RESTCONTROLLER: " + greetings.hellow);
    }

    @PostMapping("/")
    public ResponseEntity<?> addingCar(@RequestBody CarRequest carRequest){
        boolean isCarAdded = true;
        if(isCarAdded){
            return ResponseEntity.ok("Added new car: " + carRequest);
        }
        return  ResponseEntity.status(500).build();
    }
}
