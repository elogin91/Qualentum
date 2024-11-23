package com.calvarez.carregistry.repositories;

import com.calvarez.carregistry.repositories.entities.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<BrandEntity, Integer> {
}
