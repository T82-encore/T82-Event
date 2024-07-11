package com.T82.event.controller;

import com.T82.event.dto.EventCreateDto;
import com.T82.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/contents")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping("{eventInfoId}/events")
    public void CreateEvent(@PathVariable("eventInfoId") Long id
            ,@RequestBody EventCreateDto eventCreateDto){
        eventService.createEvent(id,eventCreateDto);
    }


}
