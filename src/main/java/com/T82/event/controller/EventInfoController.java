package com.T82.event.controller;

import com.T82.event.dto.request.EventInfoRequest;
import com.T82.event.dto.request.UpdateEventInfoRequest;
import com.T82.event.dto.response.EventGetEarliestOpenTicket;
import com.T82.event.dto.response.EventInfoListResponse;
import com.T82.event.dto.response.EventInfoResponse;
import com.T82.event.dto.response.SearchResponse;
import com.T82.event.service.EventInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @DeleteMapping("{eventInfoId}")
    public void deleteEventInfo(@PathVariable Long eventInfoId) {
        eventInfoService.deleteEventInfo(eventInfoId);
    }
    @GetMapping("genre/{categoryId}/rank")
    public List<EventInfoListResponse> getEventInfoListByCategoryId(@PathVariable Long categoryId) {
        return eventInfoService.getEventInfoListByHighCategoryId(categoryId);
    }
    @GetMapping("genre/{categoryId}/earliest-ticket")
    public List<EventInfoListResponse> getNextUpcomingEvents(@PathVariable Long categoryId) {
        return eventInfoService.getNextUpcomingEvents(categoryId);
    }
    @GetMapping("rank")
    public List<EventInfoListResponse> getTopSellingEvents() {
        return eventInfoService.getTopSellingEvents();
    }
    @GetMapping("genre/{categoryId}/events")
    public Page<EventInfoListResponse> getEventInfosByCategoryId(
            @PathVariable Long categoryId,
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ) {
        return eventInfoService.getEventInfosByCategoryId(categoryId, pageable);
    }

    /**
     * 오늘 날짜 이후로 가까운 이벤트 정보 리스트 10개 보내기
     **/
    @GetMapping()
    public List<EventGetEarliestOpenTicket> getEarliestOpenTickets(){
        return eventInfoService.getEarliestOpenEventInfo();
    }

    @GetMapping("/{eventInfoId}")
    public EventInfoResponse getEventInfo(@PathVariable("eventInfoId") Long eventInfoId){
        return eventInfoService.getEventInfo(eventInfoId);
    }

    @GetMapping("/search")
    public List<SearchResponse> searchEventInfo (@RequestParam String searchWord){
        return eventInfoService.searchByTitle(searchWord);
    }
}
