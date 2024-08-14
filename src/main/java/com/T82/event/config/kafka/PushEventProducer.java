package com.T82.event.config.kafka;

import com.T82.event.dto.response.PushEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PushEventProducer {
    private final KafkaTemplate<String, PushEventDto> kafkaTemplate;

    public void send(String status, PushEventDto pushEventDto) {
        kafkaTemplate.send("pushEventTopic", pushEventDto);
    }
}
