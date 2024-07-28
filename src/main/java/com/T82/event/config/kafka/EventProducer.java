package com.T82.event.config.kafka;

import com.T82.event.dto.response.EventDto;
import com.T82.event.dto.response.KafkaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventProducer {
    private final KafkaTemplate<String, EventDto> kafkaTemplate;

    public void send(String status, EventDto eventDto) {
        kafkaTemplate.send("eventTopic", eventDto);
    }
}
