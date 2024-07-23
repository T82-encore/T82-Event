package com.T82.event.controller;

import com.T82.event.dto.request.EventCreateDto;
import com.T82.event.dto.request.EventUpdateDto;
import com.T82.event.dto.response.EventDetail;
import com.T82.event.dto.response.EventGetInfoList;
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
     * 특정 공연 정보의 이벤트 리스트 리턴
     **/
    @GetMapping("{eventInfoId}/events")
    public List<EventGetInfoList> getInfoList(@PathVariable("eventInfoId") Long id){

        return eventService.getInfoList(id);
    }

    //티켓 -> 이벤트 feign 요청
    @GetMapping("{eventId}/details")
    public EventDetail getEventDetail(@PathVariable("eventId") Long id){
        return eventService.getEventDetail(id);
    }


}
