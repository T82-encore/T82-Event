package com.T82.event.service.impl;

import com.T82.event.domain.EventInfo;
import com.T82.event.domain.repository.EventInfoRepository;
import com.T82.event.dto.request.EventInfoRequest;
import com.T82.event.service.EventInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventInfoServiceImpl implements EventInfoService {
    private final EventInfoRepository eventInfoRepository;

    @Override
    public void createEventInfo(EventInfoRequest request) {
        eventInfoRepository.save(request.toEntity());
    }
}
