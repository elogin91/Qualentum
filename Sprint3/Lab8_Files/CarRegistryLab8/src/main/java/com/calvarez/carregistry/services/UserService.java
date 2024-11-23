package com.calvarez.carregistry.services;


import com.calvarez.carregistry.repositories.entities.UserEntity;

public interface UserService {

  public UserEntity save(UserEntity newUser);
}