package com.calvarez.carregistry.repositories;

import com.calvarez.carregistry.repositories.entities.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CarRepository extends JpaRepository<CarEntity, Integer> {


}
