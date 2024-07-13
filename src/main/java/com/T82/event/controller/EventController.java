package com.T82.event.controller;

import com.T82.event.dto.request.EventCreateDto;
import com.T82.event.dto.request.EventUpdateDto;
import com.T82.event.dto.response.EventGetEarliestOpenTicket;
import com.T82.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    /**
    * 오늘 날짜 이후로 가까운 이벤트 정보 리스트 10개 보내기
    **/
    @GetMapping()
    public List<EventGetEarliestOpenTicket> getEarliestOpenTickets(){

        return eventService.getEarliestOpenEventInfo();
    }

}
