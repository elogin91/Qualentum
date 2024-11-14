package com.calvarez.carregistry.controllers;

import com.calvarez.carregistry.controllers.dtos.CarRequest;
import com.calvarez.carregistry.repositories.entities.BrandEntity;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
public class BrandControllerTest {
    private static final int BRAND_ID1 = 1;
    private static final int BRAND_ID2 = 2;
    private static final int BRAND_ID3 = 3;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    BrandController brandController;

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
    void getAllBrandsReturnsBrands() throws Exception {
        List<Brand> brands = Arrays.asList(aBrand(), aBrand2());
        CompletableFuture<List<Brand>> futureBrands = CompletableFuture.completedFuture(brands);

        when(brandService.getAll()).thenReturn(futureBrands);

        mockMvc.perform(get("/brand/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(brands.get(0).getId()))
                .andExpect(jsonPath("$[1].id").value(brands.get(1).getId()));
    }

    @Test
    void getBrandReturnsBrandWhenBrandExists() throws Exception {
        Brand brand = aBrand();
        when(brandService.get(brand.getId())).thenReturn(Optional.of(brand));
        String brandJson = objectMapper.writeValueAsString(brand);

        mockMvc.perform(get("/brand/" + BRAND_ID1))
                .andExpect(status().isOk())
                .andExpect(content().json(brandJson));
    }

    @Test
    void getBrandReturnsNotFoundWhenBrandDoesNotExist() throws Exception {
        int nonExistentBrandId = 999;

        when(brandService.get(nonExistentBrandId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/brand/" + nonExistentBrandId))
                .andExpect(status().isNotFound());
    }

    @Test
    void modifyBrandReturnsUpdatedBrandWhenExists() throws Exception {
        Brand brand = aBrand();
        Brand brandInput = aBrand();

        when(brandService.update(brandInput)).thenReturn(Optional.of(brand));

        String brandRequestJson = objectMapper.writeValueAsString(brandInput);
        String expectedBrandJson = objectMapper.writeValueAsString(brand);

        mockMvc.perform(put("/brand/" + BRAND_ID1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(brandRequestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedBrandJson));
    }

    @Test
    void modifyBrandReturnsUpdatedBrandWhenDoesntExists() throws Exception {
        Brand brandInput = aBrand();

        when(brandService.update(brandInput)).thenReturn(Optional.empty());

        String brandRequestJson = objectMapper.writeValueAsString(brandInput);

        mockMvc.perform(put("/brand/" + BRAND_ID1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(brandRequestJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteBrandReturnsDeletedBrandWhenExists() throws Exception {
        Brand brand = aBrand();

        when(brandService.delete(BRAND_ID1)).thenReturn(Optional.of(brand));

        String expectedBrandJson = objectMapper.writeValueAsString(brand);

        mockMvc.perform(delete("/brand/" + BRAND_ID1))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedBrandJson));
    }

    @Test
    void deleteBrandReturnsNotFoundWhenBrandDoesNotExist() throws Exception {

        when(brandService.delete(BRAND_ID1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/brand/" + BRAND_ID1))
                .andExpect(status().isNotFound());
    }

    @Test
    void addBrandReturnsAddedBrandWhenSuccessful() throws Exception {
        Brand brandInput = aBrandInputWithoutId();
        Brand brand = aBrand();

        when(brandService.add(brandInput)).thenReturn(brand);

        String brandRequestJson = objectMapper.writeValueAsString(brandInput);
        String expectedBrandJson = objectMapper.writeValueAsString(brand);


        mockMvc.perform(post("/brand/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(brandRequestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedBrandJson));
    }


    private  Brand aBrand() {
        return new Brand(BRAND_ID1, "Renault", 2, "Spain");
    }
    private  Brand aBrand2() {
        return new Brand(BRAND_ID2, "Renault", 2, "Spain");
    }
    private  Brand aBrandInputWithoutId() {
        return new Brand(null, "Renault", 2, "Spain");
    }

    private static BrandEntity aBrandEntity() {
        return new BrandEntity(BRAND_ID1, "Renault", 2, "Spain");
    }
    private static BrandEntity aBrandEntity2() {
        return new BrandEntity(BRAND_ID2, "Renault", 2, "Spain");
    }
    private static BrandEntity aBrandEntity3() {
        return new BrandEntity(BRAND_ID3, "Renault", 2, "Spain");
    }
}
