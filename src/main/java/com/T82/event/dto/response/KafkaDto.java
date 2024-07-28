package com.T82.event.dto.response;

public record KafkaDto<T>(String status, T data) {
}
