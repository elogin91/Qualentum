package com.calvarez.carregistry.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SingUpRequest {

    String name;
    String surname;
    String email;
    String password;

}
