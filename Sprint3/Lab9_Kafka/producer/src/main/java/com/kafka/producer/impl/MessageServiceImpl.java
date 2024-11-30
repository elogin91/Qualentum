package com.kafka.producer.impl;

import com.kafka.dto.OfferDTO;
import com.kafka.producer.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    private KafkaTemplate<String, OfferDTO> kafkaTemplate;


    @KafkaListener(topics = "offert", groupId = "1")
    @Override
    public void write(OfferDTO offerDTO) {
        System.out.println(offerDTO);
        kafkaTemplate.send("offert", offerDTO);
    }
}
