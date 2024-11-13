package com.calvarez.carregistry.controllers;

import com.calvarez.carregistry.controllers.dtos.CarRequest;
import com.calvarez.carregistry.repositories.entities.UserEntity;
import com.calvarez.carregistry.services.BrandService;
import com.calvarez.carregistry.services.CarService;
import com.calvarez.carregistry.services.impl.AuthenticationService;
import com.calvarez.carregistry.services.impl.JwtService;
import com.calvarez.carregistry.services.impl.UserServiceImpl;
import com.calvarez.carregistry.services.model.Brand;
import com.calvarez.carregistry.services.model.Car;
import com.calvarez.carregistry.services.model.CarInput;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
public class CarControllerTest {
    private static final int BRAND_ID1 = 1;
    private static final int BRAND_ID2 = 2;
    private static final int BRAND_ID3 = 3;
    private static final int CAR_ID1 = 1;
    private static final int CAR_ID2 = 2;
    private static final int CAR_ID3 = 3;
    private static final String USER_NAME = "username";
    private static final String TOKEN = "token";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private CarController carController;

    @MockBean
    private JwtService jwtService;
    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private UserServiceImpl userServiceImpl;
    @MockBean
    private CarService carService;
    @MockBean
    private BrandService brandService;


    @Test
    void getAllCarsReturnsCars() throws Exception {
        List<Car> cars = Arrays.asList(aCar(), aCar2());
        CompletableFuture<List<Car>> futureCars = CompletableFuture.completedFuture(cars);

        when(carService.getAll()).thenReturn(futureCars);

        mockMvc.perform(get("/car/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(cars.get(0).getId()))
                .andExpect(jsonPath("$[1].id").value(cars.get(1).getId()));
    }

    @Test
    void getCarReturnsCarWhenCarExists() throws Exception {
        Car car = aCar();
        when(carService.get(car.getId())).thenReturn(Optional.of(car));
        String carJson = objectMapper.writeValueAsString(car);

        mockMvc.perform(get("/car/" + car.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(carJson));
    }

    @Test
    void getCarReturnsNotFoundWhenCarDoesNotExist() throws Exception {
        int nonExistentCarId = 999;

        when(carService.get(nonExistentCarId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/car/" + nonExistentCarId))
                .andExpect(status().isNotFound());
    }

    @Test
    void modifyCarReturnsUpdatedCarWhenExists() throws Exception {
        CarRequest carRequest = aCarRequest();
        CarInput carInput = aCarInput();
        Car car = aCar();

        when(carService.update(carInput)).thenReturn(Optional.of(car));

        String carRequestJson = objectMapper.writeValueAsString(carRequest);
        String expectedCarJson = objectMapper.writeValueAsString(car);

        mockMvc.perform(put("/car/" + CAR_ID1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carRequestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedCarJson));
    }

    @Test
    void modifyCarReturnsNotFoundWhenCarDoesNotExist() throws Exception {
        CarRequest carRequest = aCarRequest();
        CarInput carInput = aCarInput();

        when(carService.update(carInput)).thenReturn(Optional.empty());

        String carRequestJson = objectMapper.writeValueAsString(carRequest);

        mockMvc.perform(put("/car/" + CAR_ID1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carRequestJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteCarReturnsDeletedCarWhenExists() throws Exception {
        Car car = aCar();

        when(carService.delete(CAR_ID1)).thenReturn(Optional.of(car));

        String expectedCarJson = objectMapper.writeValueAsString(car);

        mockMvc.perform(delete("/car/" + CAR_ID1))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedCarJson));
    }

    @Test
    void deleteCarReturnsNotFoundWhenCarDoesNotExist() throws Exception {

        when(carService.delete(CAR_ID1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/car/" + CAR_ID1))
                .andExpect(status().isNotFound());
    }

    @Test
    void addCarReturnsAddedCarWhenSuccessful() throws Exception {
        CarRequest carRequest = aCarRequest();
        CarInput carInput = aCarInputWithoutId();
        Car car = aCar();

        when(carService.add(carInput)).thenReturn(car);

        String carRequestJson = objectMapper.writeValueAsString(carRequest);
        String expectedCarJson = objectMapper.writeValueAsString(car);


        mockMvc.perform(post("/car/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carRequestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedCarJson));
    }

    @Test
    void addCarReturnsBadRequestWhenBrandNotFound() throws Exception {
        CarRequest carRequest = aCarRequest();
        CarInput carInput = aCarInputWithoutId();

        when(carService.add(carInput)).thenThrow(new CarService.BrandNotFoundException());

        String carRequestJson = objectMapper.writeValueAsString(carRequest);

        mockMvc.perform(post("/car/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carRequestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Brand not found"));
    }


    private CarRequest aCarRequest() {
        return new CarRequest( 1, "Clio", 209000, 10000.00, 2015, "Semi-nuevo", "Rojo", "Diesel", 5);
    }
    private CarInput aCarInput() {
        return new CarInput(CAR_ID1, 1, "Clio", 209000, 10000.00, 2015, "Semi-nuevo", "Rojo", "Diesel", 5);
    }
    private CarInput aCarInputWithoutId() {
        return new CarInput(null, 1, "Clio", 209000, 10000.00, 2015, "Semi-nuevo", "Rojo", "Diesel", 5);
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
}
