package com.T82.event.service;

import com.T82.event.dto.request.EventInfoRequest;
import com.T82.event.dto.request.UpdateEventInfoRequest;
import com.T82.event.dto.response.EventGetEarliestOpenTicket;
import com.T82.event.dto.response.EventInfoListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.T82.event.dto.response.EventInfoResponse;

import java.util.List;

public interface EventInfoService {
    void createEventInfo(EventInfoRequest request);
    void updateEventInfo(Long id, UpdateEventInfoRequest request);
    void deleteEventInfo(Long id);
    List<EventInfoListResponse> getEventInfoListByHighCategoryId(Long id);
    List<EventInfoListResponse> getNextUpcomingEvents(Long categoryId);
    List<EventInfoListResponse> getTopSellingEvents();
    Page<EventInfoListResponse> getEventInfosByCategoryId(Long categoryId, Pageable pageable);
    List<EventGetEarliestOpenTicket> getEarliestOpenEventInfo();
    EventInfoResponse getEventInfo(Long eventInfoId);
}
