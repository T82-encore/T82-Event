package com.T82.event.service;

import com.T82.event.dto.request.EventInfoRequest;
import com.T82.event.dto.request.UpdateEventInfoRequest;
import com.T82.event.dto.response.EventInfoListResponse;

import java.util.List;

public interface EventInfoService {
    void createEventInfo(EventInfoRequest request);
    void updateEventInfo(Long id, UpdateEventInfoRequest request);
    void deleteEventInfo(Long id);
    List<EventInfoListResponse> getEventInfoListByHighCategoryId(Long id);
}
