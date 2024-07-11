package com.T82.event.service.impl;

import com.T82.event.domain.EventInfo;
import com.T82.event.domain.repository.EventInfoRepository;
import com.T82.event.domain.repository.EventRepository;
import com.T82.event.dto.EventCreateDto;
import com.T82.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventInfoRepository eventInfoRepository;
    @Override
    public void createEvent(Long id, EventCreateDto eventCreateDto) {
        EventInfo eventInfo = eventInfoRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 공연정보가 없습니다"));

        eventRepository.save(eventCreateDto.toEntity(eventInfo));
    }
}
