package com.calvarez.carregistry.services.impl;

import com.calvarez.carregistry.repositories.BrandRepository;
import com.calvarez.carregistry.repositories.entities.BrandEntity;
import com.calvarez.carregistry.services.model.Brand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BrandServiceImplTest {
    private static final int BRAND_ID1 = 1;
    private static final int BRAND_ID2 = 2;
    private static final int BRAND_ID3 = 3;

    @InjectMocks
    private BrandServiceImpl brandService;

    @Mock
    private BrandRepository brandRepository;
    @Mock
    private MapperService mapperService;

    @Test
    void getReturnsBrandWhenItExists(){
        BrandEntity brandEntity = aBrandEntity();
        when(brandRepository.findById(BRAND_ID1)).thenReturn(Optional.of(brandEntity));
        Brand brand = aBrand();
        when(mapperService.serviceFromEntity(brandEntity)).thenReturn(brand);

        Optional<Brand> result = brandService.get(BRAND_ID1);

       assertEquals(Optional.of(brand), result);
//        assertEquals(new Brand(2, "asdf", 3, "asdf"), result);
    }

    @Test
    void getReturnsBrandWhenItDoesntExist(){
        BrandEntity brandEntity = aBrandEntity();
        when(brandRepository.findById(BRAND_ID1)).thenReturn(Optional.empty());

        Optional<Brand> result = brandService.get(BRAND_ID1);

        assertTrue(result.isEmpty());
    }

    @Test
    void getReturnsAllBrands() throws ExecutionException, InterruptedException {
        List<BrandEntity> listOFBrandsEntity = aListOfBrandsEntity();
        when(brandRepository.findAll()).thenReturn(listOFBrandsEntity);
        List<Brand> listOfBrands = aListOfBrands();
        when(mapperService.serviceFromEntity(listOFBrandsEntity.get(0))).thenReturn(listOfBrands.get(0));
        when(mapperService.serviceFromEntity(listOFBrandsEntity.get(1))).thenReturn(listOfBrands.get(1));
        when(mapperService.serviceFromEntity(listOFBrandsEntity.get(2))).thenReturn(listOfBrands.get(2));

        List<Brand> result = brandService.getAll().get();

        assertEquals(listOfBrands, result);
    }

    @Test
    void updateReturnsBrandWhenItExists(){
        Brand brandInput = aBrand();
        BrandEntity brandEntityInput = aBrandEntity();
        when(mapperService.entityFromService(brandInput)).thenReturn(brandEntityInput);
        BrandEntity brandEntityPersisted = aBrandEntity2();
        when(brandRepository.findById(brandEntityInput.getId())).thenReturn(Optional.of(brandEntityPersisted));
        BrandEntity brandEntityUpdated = aBrandEntity3();
        when(brandRepository.save(brandEntityInput)).thenReturn(brandEntityUpdated);
        Brand brandOutput = aBrand3();
        when(mapperService.serviceFromEntity(brandEntityUpdated)).thenReturn(brandOutput);

        Optional<Brand> result = brandService.update(brandInput);

        assertEquals(Optional.of(brandOutput), result);
    }

    @Test
    void updateReturnsBrandWhenItDoesntExist(){
        Brand brandInput = aBrand();
        BrandEntity brandEntityInput = aBrandEntity();
        when(mapperService.entityFromService(brandInput)).thenReturn(brandEntityInput);
        when(brandRepository.findById(brandEntityInput.getId())).thenReturn(Optional.empty());

        Optional<Brand> result = brandService.update(brandInput);

        assertTrue(result.isEmpty());
    }

    @Test
    void deleteReturnsBrandWhenItExists(){
        BrandEntity brandEntity = aBrandEntity();
        when(brandRepository.findById(BRAND_ID1)).thenReturn(Optional.of(brandEntity));
        Brand brand = aBrand();
        when(mapperService.serviceFromEntity(brandEntity)).thenReturn(brand);

        Optional<Brand> result = brandService.delete(BRAND_ID1);

        verify(brandRepository).deleteById(BRAND_ID1);
        assertEquals(Optional.of(brand), result);
    }

    @Test
    void deleteReturnsBrandWhenItDoesntExists(){
        BrandEntity brandEntity = aBrandEntity();
        when(brandRepository.findById(BRAND_ID1)).thenReturn(Optional.empty());

        Optional<Brand> result = brandService.delete(BRAND_ID1);

        verify(brandRepository, never()).deleteById(BRAND_ID1);
        assertTrue(result.isEmpty());
    }

    @Test
    void createReturnsBrand(){
        Brand brand = aBrand();
        BrandEntity brandEntity = aBrandEntity();
        when(mapperService.entityFromService(brand)).thenReturn(brandEntity);
        BrandEntity brandEntityOutput = aBrandEntity2();
        Brand brandOutput = aBrand2();
        when(brandRepository.save(brandEntity)).thenReturn(brandEntityOutput);
        when(mapperService.serviceFromEntity(brandEntityOutput)).thenReturn(brandOutput);

        Brand result = brandService.add(brand);

        assertEquals(brandOutput, result);
    }

    private List<Brand> aListOfBrands() {
        List<Brand> listOfBrands = new ArrayList<>();
        listOfBrands.add(aBrand());
        listOfBrands.add(aBrand2());
        listOfBrands.add(aBrand3());
        return listOfBrands;
    }

    private List<BrandEntity> aListOfBrandsEntity() {
        List<BrandEntity> listOFBrandsEntity = new ArrayList<>();
        listOFBrandsEntity.add(aBrandEntity());
        listOFBrandsEntity.add(aBrandEntity2());
        listOFBrandsEntity.add(aBrandEntity3());
        return listOFBrandsEntity;
    }

    private static Brand aBrand() {
        return new Brand(BRAND_ID1, "Renault", 2, "Spain");
    }
    private static Brand aBrand2() {
        return new Brand(BRAND_ID2, "Renault", 2, "Spain");
    }
    private static Brand aBrand3() {
        return new Brand(BRAND_ID3, "Renault", 2, "Spain");
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
