package com.T82.event.service.impl;


import com.T82.event.config.kafka.EventProducer;
import com.T82.event.config.kafka.PushEventProducer;
import com.T82.event.domain.Event;
import com.T82.event.domain.EventInfo;
import com.T82.event.domain.repository.EventInfoRepository;
import com.T82.event.domain.repository.EventRepository;
import com.T82.event.domain.repository.SectionDAO;
import com.T82.event.dto.request.EventCreateDto;
import com.T82.event.dto.request.EventUpdateDto;
import com.T82.event.dto.response.EventDetail;
import com.T82.event.dto.response.EventDto;
import com.T82.event.dto.response.EventGetInfoList;
import com.T82.event.dto.response.PushEventDto;
import com.T82.event.service.EventService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private static final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);
    private final EventRepository eventRepository;
    private final EventInfoRepository eventInfoRepository;
    private final SectionDAO sectionDAO;
    private final EventProducer eventProducer;
    private final PushEventProducer pushEventProducer;

    @Override
    public void createEvent(Long id, EventCreateDto eventCreateDto) {
        EventInfo eventInfo = eventInfoRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 공연정보가 없습니다"));

        Event event = eventRepository.save(eventCreateDto.toEntity(eventInfo));

            eventProducer.send(
                    "create",
                    new EventDto(
                            event.getEventId(),
                            eventInfo.getEventPlace(),
                            sectionDAO.getSectionDataList(eventInfo.getEventInfoId())
                    )
            );

            pushEventProducer.send("create", PushEventDto.builder()
                    .eventId(event.getEventId())
                    .eventStartTime(Timestamp.valueOf(event.getEventStartTime()))
                    .build());
    }

    @Override
    @Transactional
    public void updateEvent(Long id, Long eventId, EventUpdateDto eventUpdateDto) {
        if(!eventInfoRepository.existsById(id)) throw  new IllegalArgumentException("해당 공연정보가 없습니다");

        Event event = eventRepository.findByEventIdAndIsDeletedFalse(eventId).orElseThrow(()
                -> new IllegalArgumentException("해당 이벤트가 없습니다"));

        event.update(eventUpdateDto);
    }

    @Override
    @Transactional
    public void deleteEvent(Long id, Long eventId) {
        if(!eventInfoRepository.existsById(id)) throw  new IllegalArgumentException("해당 공연정보가 없습니다");

        Event event = eventRepository.findByEventIdAndIsDeletedFalse(eventId).orElseThrow(()
                -> new IllegalArgumentException("해당 이벤트가 없습니다"));

        event.delete();
    }

    @Override
    public List<EventGetInfoList> getInfoList(Long eventInfoId) {
        EventInfo eventInfo = eventInfoRepository.findById(eventInfoId).orElseThrow(()
                -> new IllegalArgumentException("해당 공연정보가 없습니다"));

        List<Event> event = eventRepository.findAllByEventInfoAndBookEndTimeAfterAndIsDeletedIsFalse(eventInfo,LocalDateTime.now());

        return event.stream().map(EventGetInfoList :: fromEntity).toList();
    }

    @Override
    public EventDetail getEventDetail(Long eventId) {
        return eventRepository.findEventDetailByEventId(eventId);
    }


}
