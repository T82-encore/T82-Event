package com.T82.event.service.impl;

import com.T82.event.domain.Category;
import com.T82.event.domain.Event;
import com.T82.event.domain.EventInfo;
import com.T82.event.domain.EventPlace;
import com.T82.event.domain.repository.EventInfoRepository;
import com.T82.event.domain.repository.EventRepository;
import com.T82.event.dto.request.EventCreateDto;
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


    @BeforeEach
    void setUp() {
        Category category = new Category(1L, "콘서트", null, null);

        EventPlace eventPlace = new EventPlace(1L, "장충 체육관", "장충동", 100, true, null, null);

        eventInfo = new EventInfo(1L, "단콘" ,"콘서트임", 8.5, 120, "12세 관람가", 0 , LocalDateTime.now(), null, category, eventPlace,null);

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
    void updateEvent() {
    }

    @Test
    void deleteEvent() {
    }

    @Test
    void getEarliestOpenEventInfo() {
    }
}