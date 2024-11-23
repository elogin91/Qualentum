package com.calvarez.carregistry.services;

import com.calvarez.carregistry.services.model.Brand;
import com.calvarez.carregistry.services.model.UserImage;

import java.util.Optional;

public interface UserImageService {
    Optional<UserImage> getImage(Long id);
    boolean addImage(UserImage image);
}
