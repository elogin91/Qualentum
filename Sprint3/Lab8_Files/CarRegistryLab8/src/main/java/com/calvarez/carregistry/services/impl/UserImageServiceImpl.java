package com.calvarez.carregistry.services.impl;

import com.calvarez.carregistry.repositories.UserImageRepository;
import com.calvarez.carregistry.repositories.UserRepository;
import com.calvarez.carregistry.repositories.entities.UserEntity;
import com.calvarez.carregistry.repositories.entities.UserImageEntity;
import com.calvarez.carregistry.services.UserImageService;
import com.calvarez.carregistry.services.model.UserImage;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserImageServiceImpl implements UserImageService {
    @Autowired
    UserImageRepository userImageRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public Optional<UserImage> getImage(Long id) {
        Optional <UserEntity> userEntity= userRepository.findById(id);
        if (userEntity.isEmpty()) {
            return Optional.empty();
        }
        Optional<UserImageEntity> userImageEntity = userImageRepository.findByUserId(id);
        return userImageEntity.map(entity -> new UserImage(entity.getUserId(), entity.getImage()));
    }

    @Override
    public boolean addImage(UserImage image) {
        Optional <UserEntity> userEntity= userRepository.findById(image.getUserId());
        if (userEntity.isPresent()) {
            UserImageEntity userImageEntity = new UserImageEntity(null, image.getUserId(), image.getImage());
            userImageRepository.save(userImageEntity);
            return true;
        }
        return false;
    }

}
