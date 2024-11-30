package com.kafka.consumer.service;

import com.kafka.dto.OfferDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConsumerService {

    @KafkaListener(topics = "offert")
    public void cosume(ConsumerRecord<String, OfferDTO> offerDTOConsumerRecord){

        OfferDTO offer = offerDTOConsumerRecord.value();

        String formattedMessage = String.format(
                "🚗 NEW OFFER 🚗\n\n" +
                        "Brand: %s\n" +
                        "Model: %s\n" +
                        "Year: %d\n" +
                        "Mileage: %.2f km\n\n" +
                        "📞 Contact: %s\n" +
                        "🎁 Special Offer: %s\n\n" +
                        "Don't miss this opportunity!",
                offer.getBrand(),
                offer.getModel(),
                offer.getYear(),
                offer.getMilleage(),
                offer.getPhone(),
                offer.getOffer()
        );

        log.info("{}", formattedMessage);

    }
}
