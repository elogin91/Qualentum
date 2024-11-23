package com.calvarez.carregistry.services.impl;


import com.calvarez.carregistry.repositories.UserRepository;
import com.calvarez.carregistry.repositories.entities.UserEntity;
import com.calvarez.carregistry.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserEntity save(UserEntity newUser) {
        return userRepository.save(newUser);
    }
}