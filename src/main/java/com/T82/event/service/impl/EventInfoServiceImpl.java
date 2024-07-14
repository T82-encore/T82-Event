package com.T82.event.service.impl;

import com.T82.event.domain.EventInfo;
import com.T82.event.domain.repository.EventInfoRepository;
import com.T82.event.dto.request.EventInfoRequest;
import com.T82.event.dto.request.UpdateEventInfoRequest;
import com.T82.event.dto.response.EventInfoListResponse;
import com.T82.event.service.EventInfoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventInfoServiceImpl implements EventInfoService {
    private final EventInfoRepository eventInfoRepository;

    @Override
    public void createEventInfo(EventInfoRequest request) {
        eventInfoRepository.save(request.toEntity());
    }

    @Override
    @Transactional
    public void updateEventInfo(Long id, UpdateEventInfoRequest request) {
        EventInfo eventInfo = eventInfoRepository
                .findById(id)
                .orElseThrow(IllegalArgumentException::new);
        eventInfo.setTitle(request.title());
        eventInfo.setDescription(request.description());
        eventInfo.setAgeRestriction(request.ageRestriction());
        eventInfo.setRunningTime(request.runningTime());
        eventInfo.setBookStartTime(request.bookStartTime());
    }

    @Override
    @Transactional
    public void deleteEventInfo(Long id) {
        EventInfo eventInfo = eventInfoRepository
                .findById(id)
                .orElseThrow(IllegalArgumentException::new);
        eventInfo.setDeleted(true);
    }

    @Override
    public List<EventInfoListResponse> getEventInfoListByHighCategoryId(Long id) {
        return List.of();
    }
}
