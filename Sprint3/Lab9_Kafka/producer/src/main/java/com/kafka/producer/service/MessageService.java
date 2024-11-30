package com.kafka.producer.service;

import com.kafka.dto.OfferDTO;
import org.springframework.kafka.annotation.KafkaListener;

public interface MessageService {
    @KafkaListener(topics="offert", groupId="1")
    void write(OfferDTO offerDTO);
}
