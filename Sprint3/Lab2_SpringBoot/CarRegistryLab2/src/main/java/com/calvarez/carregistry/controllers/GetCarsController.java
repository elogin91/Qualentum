package com.calvarez.carregistry.controllers;

import com.calvarez.carregistry.repositories.Car;
import com.calvarez.carregistry.services.Greetings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetCarsController {

    @Autowired
    Greetings greetings;

    @GetMapping("/cars")
    public void getCarData() {
        Car car = new Car("1234ABC", "Ford", "Fiesta", 2021);

        System.out.println("Bienvenida desde el RESTCONTROLLER: " + greetings.hellow);
        System.out.println("Coches disponibles: " + car);
    }
}
