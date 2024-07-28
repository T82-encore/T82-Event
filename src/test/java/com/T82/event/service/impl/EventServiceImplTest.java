package com.T82.event.service.impl;

import com.T82.event.domain.Category;
import com.T82.event.domain.Event;
import com.T82.event.domain.EventInfo;
import com.T82.event.domain.EventPlace;
import com.T82.event.domain.repository.EventInfoRepository;
import com.T82.event.domain.repository.EventRepository;
import com.T82.event.dto.request.EventCreateDto;
import com.T82.event.dto.request.EventUpdateDto;
import com.T82.event.dto.response.EventGetInfoList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    private List<EventInfo> eventInfoList;

    private List<Event> eventList;

    private LocalDateTime currentTime;

    @BeforeEach
    void setUp() {
        currentTime = LocalDateTime.now();

        Category category = new Category(1L, "콘서트", null, null);

        EventPlace eventPlace = new EventPlace(1L, "장충 체육관", "장충동", 100, true, null, null);

        eventInfoList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            eventInfoList.add(new EventInfo(
                    (long) (i + 1),
                    "단콘 " + (i + 1),
                    "콘서트임",
                    8.5,
                    "120분",
                    "12세 관람가",
                    0,
                    LocalDateTime.now().plusDays(i),
                    false,
                    null,
                    category,
                    eventPlace,
                    null
            ));
        }

        eventList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            eventList.add(new Event(
                    (long) (i + 1),
                    LocalDateTime.now().plusDays(10),
                    LocalDateTime.now().plusDays(5),
                    false,
                    false,
                    0L,
                    eventInfo
            ));
        }

        eventInfo = new EventInfo(1L, "단콘" ,"콘서트임", 8.5, "120분", "12세 관람가", 0
                , LocalDateTime.now(),false, null, category, eventPlace,null);

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
        when(eventRepository.findByEventIdAndIsDeletedFalse(eventId)).thenReturn(Optional.of(event));

        // When
        eventServiceImpl.updateEvent(eventInfoId, eventId, eventUpdateDto);

        // Then
        verify(eventRepository, times(1)).findByEventIdAndIsDeletedFalse(eventId);
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
        verify(eventRepository, never()).findByEventIdAndIsDeletedFalse(eventId);
        assertThat(exception.getMessage()).isEqualTo("해당 공연정보가 없습니다");
    }


    @Test
    void updateEventFail_EventIsDeleted() {
        //given
        Long eventInfoId = 1L;
        Long eventId = 1L;
        EventUpdateDto eventUpdateDto = new EventUpdateDto(LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(15));

        Event event = new Event();
        event.setIsDeleted(true);

        when(eventInfoRepository.existsById(eventInfoId)).thenReturn(true);
        when(eventRepository.findByEventIdAndIsDeletedFalse(eventId)).thenReturn(Optional.empty());

        // When
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> eventServiceImpl.updateEvent(eventInfoId, eventId, eventUpdateDto));

        // Then
        verify(eventRepository, times(1)).findByEventIdAndIsDeletedFalse(eventId);
        assertThat(exception.getMessage()).isEqualTo("해당 이벤트가 없습니다");
    }


    @Test
    void deleteEventSuccess() {
        //given
        Long eventInfoId = 1L;
        Long eventId = 1L;

        //when
        when(eventInfoRepository.existsById(eventInfoId)).thenReturn(true);
        when(eventRepository.findByEventIdAndIsDeletedFalse(eventId)).thenReturn(Optional.of(event));
        eventServiceImpl.deleteEvent(eventInfoId,eventId);

        //then
        verify(eventInfoRepository,times(1)).existsById(eventInfoId);
        verify(eventRepository, times(1)).findByEventIdAndIsDeletedFalse(eventId);
        assertThat(event.getIsDeleted()).isTrue();
    }

    @Test
    void deleteEventFail_EventInfoNotFound() {
        //given
        Long eventInfoId = 99L;
        Long eventId = 1L;

        //when
        when(eventInfoRepository.existsById(eventInfoId)).thenReturn(false);
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> eventServiceImpl.deleteEvent(eventInfoId, eventId));

        //then
        verify(eventInfoRepository, times(1)).existsById(eventInfoId);
        verify(eventRepository, never()).findById(eventId);
        assertThat(exception.getMessage()).isEqualTo("해당 공연정보가 없습니다");
    }

    @Test
    void deleteEventFail_EventNotFound() {
        //given
        Long eventInfoId = 1L;
        Long eventId = 99L;

        //when
        when(eventInfoRepository.existsById(eventInfoId)).thenReturn(true);
        when(eventRepository.findByEventIdAndIsDeletedFalse(eventId)).thenReturn(Optional.empty());
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> eventServiceImpl.deleteEvent(eventInfoId, eventId));

        //then
        verify(eventInfoRepository, times(1)).existsById(eventInfoId);
        verify(eventRepository, times(1)).findByEventIdAndIsDeletedFalse(eventId);
        assertThat(exception.getMessage()).isEqualTo("해당 이벤트가 없습니다");
    }

    @Test
    void getInfoList_Success() {
        // Given
        Long eventInfoId = 1L;
        when(eventInfoRepository.findById(eventInfoId)).thenReturn(Optional.of(eventInfo));
        doReturn(eventList).when(eventRepository).findAllByEventInfoAndBookEndTimeAfterAndIsDeletedIsFalse(eq(eventInfo), any(LocalDateTime.class));

        // When
        List<EventGetInfoList> result = eventServiceImpl.getInfoList(eventInfoId);

        // Then
        verify(eventInfoRepository, times(1)).findById(eventInfoId);
        verify(eventRepository, times(1)).findAllByEventInfoAndBookEndTimeAfterAndIsDeletedIsFalse(eq(eventInfo), any(LocalDateTime.class));
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(eventList.size());
    }


    @Test
    void getInfoList_EventInfoNotFound() {
        // Given
        Long eventInfoId = 99L;
        when(eventInfoRepository.findById(eventInfoId)).thenReturn(Optional.empty());

        // When
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> eventServiceImpl.getInfoList(eventInfoId));

        // Then
        verify(eventInfoRepository, times(1)).findById(eventInfoId);
        verify(eventRepository, never()).findAllByEventInfoAndBookEndTimeAfterAndIsDeletedIsFalse(any(), any());
        assertThat(exception.getMessage()).isEqualTo("해당 공연정보가 없습니다");
    }
}