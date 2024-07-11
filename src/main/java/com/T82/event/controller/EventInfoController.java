package com.T82.event.controller;

import com.T82.event.dto.request.EventInfoRequest;
import com.T82.event.dto.request.UpdateEventInfoRequest;
import com.T82.event.service.EventInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/contents")
@RequiredArgsConstructor
public class EventInfoController {
    private final EventInfoService eventInfoService;

    @PostMapping
    public void createEventInfo(@RequestBody EventInfoRequest request) {
        eventInfoService.createEventInfo(request);
    }
    @PostMapping("{eventInfoId}")
    public void updateEventInfo(@PathVariable Long eventInfoId, @RequestBody UpdateEventInfoRequest request) {
        eventInfoService.updateEventInfo(eventInfoId, request);
    }
}
