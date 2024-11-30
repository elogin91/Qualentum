package com.kafka.dto;

import lombok.Data;

@Data
public class OfferDTO {
    String brand;
    String model;
    Integer year;
    Double milleage;
    Integer phone;
    String offer;
}
