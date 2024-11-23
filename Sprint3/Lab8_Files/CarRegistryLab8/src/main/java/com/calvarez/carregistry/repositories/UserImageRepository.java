package com.calvarez.carregistry.repositories;


import com.calvarez.carregistry.repositories.entities.UserImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserImageRepository extends JpaRepository<UserImageEntity, Integer> {
    Optional<UserImageEntity> findByUserId(Long userId);
}
