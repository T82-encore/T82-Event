package com.T82.event.service;

import com.T82.event.dto.request.EventCreateDto;
import com.T82.event.dto.request.EventUpdateDto;
import com.T82.event.dto.response.EventGetEarliestOpenTicket;
import com.T82.event.dto.response.EventGetInfoList;

import java.util.List;


public interface EventService {
    void createEvent(Long id, EventCreateDto eventCreateDto);

    void updateEvent(Long id, Long eventId, EventUpdateDto eventUpdateDto);

    void deleteEvent(Long id, Long eventId);

    List<EventGetEarliestOpenTicket> getEarliestOpenEventInfo();

    List<EventGetInfoList> getInfoList(Long eventInfoId);
}
