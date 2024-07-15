package com.T82.event.service.impl;


import com.T82.event.domain.Event;
import com.T82.event.domain.EventInfo;
import com.T82.event.domain.repository.EventInfoRepository;
import com.T82.event.domain.repository.EventRepository;
import com.T82.event.dto.request.EventCreateDto;
import com.T82.event.dto.request.EventUpdateDto;
import com.T82.event.dto.response.EventGetEarliestOpenTicket;
import com.T82.event.dto.response.EventGetInfoList;
import com.T82.event.service.EventService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    @Transactional
    public void updateEvent(Long id, Long eventId, EventUpdateDto eventUpdateDto) {
        if(!eventInfoRepository.existsById(id)) throw  new IllegalArgumentException("해당 공연정보가 없습니다");

        Event event = eventRepository.findById(eventId).orElseThrow(()
                -> new IllegalArgumentException("해당 이벤트가 없습니다"));

        if(event.getIsDeleted()) throw new IllegalArgumentException("해당 이벤트가 없습니다");

        event.setEventStartTime(eventUpdateDto.getEventStartTime());
        event.setBookEndTime(eventUpdateDto.getBookEndTime());
    }

    @Override
    @Transactional
    public void deleteEvent(Long id, Long eventId) {
        if(!eventInfoRepository.existsById(id)) throw  new IllegalArgumentException("해당 공연정보가 없습니다");

        Event event = eventRepository.findById(eventId).orElseThrow(()
                -> new IllegalArgumentException("해당 이벤트가 없습니다"));

        event.setIsDeleted(true);
    }

    @Override
    public List<EventGetEarliestOpenTicket> getEarliestOpenEventInfo() {
        List<EventInfo> comingEvents = eventInfoRepository.findComingEvents(LocalDateTime.now());

        return comingEvents.stream().map(EventGetEarliestOpenTicket :: fromEntity).toList();
    }

    @Override
    public List<EventGetInfoList> getInfoList(Long eventInfoId) {
        EventInfo eventInfo = eventInfoRepository.findById(eventInfoId).orElseThrow(()
                -> new IllegalArgumentException("해당 공연정보가 없습니다"));

        List<Event> event = eventRepository.findAllByEventInfoAndBookEndTimeAfterAndIsDeletedIsFalse(eventInfo,LocalDateTime.now());

        return event.stream().map(EventGetInfoList :: fromEntity).toList();
    }


}
