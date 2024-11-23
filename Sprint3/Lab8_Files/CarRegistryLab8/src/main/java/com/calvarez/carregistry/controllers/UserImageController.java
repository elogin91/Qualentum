package com.calvarez.carregistry.controllers;

import com.calvarez.carregistry.services.BrandService;
import com.calvarez.carregistry.services.CarService;
import com.calvarez.carregistry.services.UserImageService;
import com.calvarez.carregistry.services.model.UserImage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/userImage")
public class UserImageController extends BaseController{
    private final UserImageService userImageService;

    protected UserImageController(CarService carService, BrandService brandService, UserImageService userImageService) {
        super(carService, brandService);
        this.userImageService = userImageService;
    }

    @PostMapping(value="/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@PathVariable Long id, @RequestParam(value="file") MultipartFile file) {
        if(file.isEmpty()) {
            log.error("Image is empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if(file.getOriginalFilename() == null || !file.getOriginalFilename().endsWith(".jpg")) {
            log.error("Image type is invalid");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        try {
            UserImage userImage = new UserImage(id, file.getBytes());
            boolean created = userImageService.addImage(userImage);
            if(!created) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
            }
            return ResponseEntity.ok("Imagen añadida correctamente");
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al almacenar la imagen.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> dowloadFile(@PathVariable Long id) {
        Optional<UserImage> userImage = userImageService.getImage(id);
        if (userImage.isPresent()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(userImage.get().getImage());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Imagen no encontrada.");
        }
    }
}
