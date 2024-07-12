package com.T82.event.controller;

import com.T82.event.dto.EventCreateDto;
import com.T82.event.dto.EventUpdateDto;
import com.T82.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/contents")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping("{eventInfoId}/events")
    public void createEvent(@PathVariable("eventInfoId") Long id
            ,@RequestBody EventCreateDto eventCreateDto){
        eventService.createEvent(id,eventCreateDto);
    }

    @PutMapping("{eventInfoId}/events/{eventId}")
    public void updateEvent(@PathVariable("eventInfoId") Long id
            ,@PathVariable("eventId") Long eventId
            ,@RequestBody EventUpdateDto eventUpdateDto){
        eventService.updateEvent(id,eventId,eventUpdateDto);
    }

    @DeleteMapping("{eventInfoId}/events/{eventId}")
    public void deleteEvent(@PathVariable("eventInfoId") Long id
            ,@PathVariable("eventId") Long eventId){
        eventService.deleteEvent(id,eventId);
    }


}
