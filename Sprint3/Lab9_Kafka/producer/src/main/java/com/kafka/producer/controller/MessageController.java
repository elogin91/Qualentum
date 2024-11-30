package com.kafka.producer.controller;

import com.kafka.dto.OfferDTO;
import com.kafka.producer.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    @Autowired
    MessageService messageService;

    @PostMapping("/message/")
    ResponseEntity<?> sendMessage(@RequestBody OfferDTO offerDTO){
        messageService.write(offerDTO);
        return ResponseEntity.ok().build();
    }
}
