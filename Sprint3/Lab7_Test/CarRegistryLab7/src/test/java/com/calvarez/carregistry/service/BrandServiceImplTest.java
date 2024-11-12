package com.calvarez.carregistry.service;

import com.calvarez.carregistry.repositories.BrandRepository;
import com.calvarez.carregistry.repositories.entities.BrandEntity;
import com.calvarez.carregistry.services.impl.BrandServiceImpl;
import com.calvarez.carregistry.services.model.Brand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BrandServiceImplTest {
    private BrandServiceImpl brandService;

    @Mock
    private BrandRepository brandRepository;

    @Test
    void test_get(){
        BrandEntity brandEntity = new BrandEntity(1, "Renault", 2, "Spain" );
        when(brandRepository.findById(1)).thenReturn(Optional.of(brandEntity));

        Brand result = brandService.get(1);
        assertEquals(result, new BrandEntity(1, "Renault", 2, "Spain"));
    }
}
