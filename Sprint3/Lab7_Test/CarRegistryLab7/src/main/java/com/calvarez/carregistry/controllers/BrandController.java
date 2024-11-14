package com.calvarez.carregistry.controllers;

import com.calvarez.carregistry.controllers.dtos.BrandRequest;
import com.calvarez.carregistry.controllers.dtos.BrandResponse;
import com.calvarez.carregistry.services.BrandService;
import com.calvarez.carregistry.services.CarService;
import com.calvarez.carregistry.services.model.Brand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

@RestController
@Slf4j
@RequestMapping("/brand")
public class BrandController extends BaseController {

    protected BrandController(CarService carService, BrandService brandService) {
        super(carService, brandService);
    }

    @GetMapping("/")
    public ResponseEntity<Stream<BrandResponse>> getAllBrands() throws ExecutionException, InterruptedException {
        return brandService.getAll().thenApply(brands -> ResponseEntity.ok(brands.stream().map(this::dtoFromService))).get();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBrand(@PathVariable Integer id) {
        try {
            Optional<Brand> brand = brandService.get(id);
            if (brand.isPresent()) {
                BrandResponse brandResponse = dtoFromService(brand.get());
                return ResponseEntity.ok(brandResponse);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Something wrong getting a brand", e);
            return ResponseEntity.internalServerError().body(ERROR_MESSAGE);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBrand(@PathVariable Integer id, @RequestBody BrandRequest brandRequest) {
        try {
            Brand brand = serviceFromDto(id, brandRequest);
            Optional<Brand> brandUpdated = brandService.update(brand);
            if (brandUpdated.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(dtoFromService(brandUpdated.get()));
        } catch (Exception e) {
            log.error("Something wrong updating a brand", e);
            return ResponseEntity.internalServerError().body(ERROR_MESSAGE);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBrand(@PathVariable Integer id) {
        try {
            Optional<Brand> brand = brandService.delete(id);
            if (brand.isPresent()) {
                return ResponseEntity.ok(dtoFromService(brand.get()));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Something wrong deleting a brand", e);
            return ResponseEntity.internalServerError().body(ERROR_MESSAGE);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Object> addBrand(@RequestBody BrandRequest brandRequest) {
        try {
            Brand brand = serviceFromDto(null, brandRequest);
            Brand brandAdded = brandService.add(brand);
            return ResponseEntity.ok(dtoFromService(brandAdded));
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
