package com.T82.event.service.impl;

import com.T82.event.domain.Category;
import com.T82.event.domain.Event;
import com.T82.event.domain.EventInfo;
import com.T82.event.domain.EventPlace;
import com.T82.event.domain.repository.EventInfoRepository;
import com.T82.event.domain.repository.EventRepository;
import com.T82.event.dto.request.EventCreateDto;
import com.T82.event.dto.request.EventUpdateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @InjectMocks
    private EventServiceImpl eventServiceImpl;

    @Mock
    private EventRepository eventRepository;

    @Mock
    EventInfoRepository eventInfoRepository;

    private EventInfo eventInfo;

    private Event event;


    @BeforeEach
    void setUp() {
        Category category = new Category(1L, "콘서트", null, null);

        EventPlace eventPlace = new EventPlace(1L, "장충 체육관", "장충동", 100, true, null, null);

        eventInfo = new EventInfo(1L, "단콘" ,"콘서트임", 8.5, 120, "12세 관람가", 0
                , LocalDateTime.now(), null, category, eventPlace,null);

        event = new Event(1L, LocalDateTime.now().plusDays(10), LocalDateTime.now().plusDays(5),false,false,0L ,eventInfo);

    }

    @Test
    void createEventSuccess() {
        //given
        Long eventInfoId = 1L;
        EventCreateDto eventCreateDto = new EventCreateDto(LocalDateTime.now().plusDays(20),LocalDateTime.now().plusDays(1));
        when(eventInfoRepository.findById(eventInfoId)).thenReturn(Optional.of(eventInfo));

        // When
        eventServiceImpl.createEvent(eventInfoId, eventCreateDto);

        // Then
        verify(eventInfoRepository, times(1)).findById(eventInfoId);
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    void createEventFail() {
        //given
        Long eventInfoId = 99L;
        EventCreateDto eventCreateDto = new EventCreateDto(LocalDateTime.now().plusDays(20),LocalDateTime.now().plusDays(1));
        when(eventInfoRepository.findById(eventInfoId)).thenReturn(Optional.empty());
        // When
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> eventServiceImpl.createEvent(eventInfoId,eventCreateDto));
        // Then
        verify(eventRepository, never()).save(any(Event.class));
        assertThat(exception.getMessage()).isEqualTo("해당 공연정보가 없습니다");
    }

    @Test
    void updateEventSuccess() {
        //given
        Long eventInfoId = 1L;
        Long eventId = 1L;
        EventUpdateDto eventUpdateDto = new EventUpdateDto(LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(15));

        when(eventInfoRepository.existsById(eventInfoId)).thenReturn(true);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        // When
        eventServiceImpl.updateEvent(eventInfoId, eventId, eventUpdateDto);

        // Then
        verify(eventRepository, times(1)).findById(eventId);
        assertThat(event.getEventStartTime()).isEqualTo(eventUpdateDto.getEventStartTime());
        assertThat(event.getBookEndTime()).isEqualTo(eventUpdateDto.getBookEndTime());
    }

    @Test
    void updateEventFail_EventInfoNotFound() {
        //given
        Long eventInfoId = 99L;
        Long eventId = 1L;
        EventUpdateDto eventUpdateDto = new EventUpdateDto(LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(15));

        when(eventInfoRepository.existsById(eventInfoId)).thenReturn(false);

        // When
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> eventServiceImpl.updateEvent(eventInfoId, eventId, eventUpdateDto));

        // Then
        verify(eventRepository, never()).findById(eventId);
        assertThat(exception.getMessage()).isEqualTo("해당 공연정보가 없습니다");
    }

    @Test
    void updateEventFail_EventNotFound() {
        //given
        Long eventInfoId = 1L;
        Long eventId = 99L;
        EventUpdateDto eventUpdateDto = new EventUpdateDto(LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(15));

        when(eventInfoRepository.existsById(eventInfoId)).thenReturn(true);
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        // When
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> eventServiceImpl.updateEvent(eventInfoId, eventId, eventUpdateDto));

        // Then
        verify(eventRepository, times(1)).findById(eventId);
        assertThat(exception.getMessage()).isEqualTo("해당 이벤트가 없습니다");
    }

    @Test
    void updateEventFail_EventIsDeleted() {
        //given
        Long eventInfoId = 1L;
        Long eventId = 1L;
        EventUpdateDto eventUpdateDto = new EventUpdateDto(LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(15));

        event.setIsDeleted(true);
        when(eventInfoRepository.existsById(eventInfoId)).thenReturn(true);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        // When
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> eventServiceImpl.updateEvent(eventInfoId, eventId, eventUpdateDto));

        // Then
        verify(eventRepository, times(1)).findById(eventId);
        assertThat(exception.getMessage()).isEqualTo("해당 이벤트가 없습니다");
    }


    @Test
    void deleteEvent() {
    }

    @Test
    void getEarliestOpenEventInfo() {
    }

    @Test
    void getInfoList() {
    }
}