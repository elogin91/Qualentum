package com.calvarez.carregistry.services.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserImage {
    private Long userId;
    private byte[] image;
}
