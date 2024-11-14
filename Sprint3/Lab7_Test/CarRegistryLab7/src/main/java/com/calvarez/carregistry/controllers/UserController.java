package com.calvarez.carregistry.controllers;


import com.calvarez.carregistry.controllers.dtos.LoginRequest;
import com.calvarez.carregistry.controllers.dtos.LoginResponse;
import com.calvarez.carregistry.controllers.dtos.SingUpRequest;
import com.calvarez.carregistry.services.impl.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<LoginResponse> singup(@RequestBody SingUpRequest request) {
       try {
           return ResponseEntity.ok(authenticationService.signup(request));
       }
       catch (Exception e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
       }
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }
}
