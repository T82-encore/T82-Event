package com.T82.event.service;

import com.T82.event.dto.EventCreateDto;

public interface EventService {
    void createEvent(Long id, EventCreateDto eventCreateDto);
}
