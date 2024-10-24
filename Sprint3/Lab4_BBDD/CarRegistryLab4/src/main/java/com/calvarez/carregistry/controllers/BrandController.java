package com.calvarez.carregistry.controllers;

import com.calvarez.carregistry.controllers.dtos.BrandRequest;
import com.calvarez.carregistry.controllers.dtos.BrandResponse;
import com.calvarez.carregistry.services.BrandService;
import com.calvarez.carregistry.services.model.Brand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/")
    public ResponseEntity<?> getAllBrands() {
        return ResponseEntity.ok(brandService.getAll().stream().map(this::dtoFromService));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBrand(@PathVariable Integer id) {
        try {
            Brand brand = brandService.get(id);
            if (brand != null) {
                BrandResponse brandResponse = dtoFromService(brand);
                return ResponseEntity.ok(brandResponse);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Something wrong getting a brand", e);
            return ResponseEntity.internalServerError().body("Some error has occurred, sorry");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBrand(@PathVariable Integer id, @RequestBody BrandRequest brandRequest) {
        try {
            Brand brand = serviceFromDto(id, brandRequest);
            Brand brandUpdated = brandService.update(brand);
            if (brandUpdated == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(dtoFromService(brandUpdated));
        } catch (Exception e) {
            log.error("Something wrong updating a brand", e);
            return ResponseEntity.internalServerError().body("Some error has occurred, sorry");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBrand(@PathVariable Integer id) {
        try {
            Brand brand = brandService.delete(id);
            if (brand != null) {
                return ResponseEntity.ok(dtoFromService(brand));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Something wrong deleting a brand", e);
            return ResponseEntity.internalServerError().body("Some error has occurred, sorry");
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> addBrand(@RequestBody BrandRequest brandRequest) {
        try {
            Brand brand = serviceFromDto(null, brandRequest);
            Brand brandAdded = brandService.add(brand);
            return ResponseEntity.ok(dtoFromService(brandAdded));
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    private BrandResponse dtoFromService(Brand brand) {
        return new BrandResponse(
                brand.getName(),
                brand.getWarranty(),
                brand.getCountry()
        );
    }

    private static Brand serviceFromDto(Integer id, BrandRequest brandRequest) {
        return new Brand(
                id,
                brandRequest.getName(),
                brandRequest.getWarranty(),
                brandRequest.getCountry()

        );
    }

}
