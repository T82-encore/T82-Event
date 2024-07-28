package com.T82.event.config.kafka;

import com.T82.event.dto.response.KafkaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventInfoProducer {
    private final KafkaTemplate<String, KafkaDto<Long>> kafkaTemplate;

    public void send(Long eventInfoId, String status){
        kafkaTemplate.send("eventInfoTopic",
                new KafkaDto<Long>(status, eventInfoId));
    }

}
