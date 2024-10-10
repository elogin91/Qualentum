package com.calvarez.carregistry.services;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Greetings {

    @Value("${property.greetings}")
    public String hellow;

    @PostConstruct
    public void postConstruct() {
        System.out.println(hellow);
    }
}
