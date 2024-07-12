package com.T82.event.service;

import com.T82.event.dto.EventCreateDto;
import com.T82.event.dto.EventUpdateDto;


public interface EventService {
    void createEvent(Long id, EventCreateDto eventCreateDto);

    void updateEvent(Long id, Long eventId, EventUpdateDto eventUpdateDto);


}
