package com.T82.event.controller;

import com.T82.event.domain.EventInfo;
import com.T82.event.domain.repository.EventInfoRepository;
import com.T82.event.dto.request.EventInfoRequest;
import com.T82.event.dto.request.UpdateEventInfoRequest;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventInfoControllerTest {
    @Autowired
    private EventInfoRepository eventInfoRepository;
    @Autowired
    private EventInfoController eventInfoController;
    @Autowired
    private EntityManager entityManager;

    @Nested
    @Transactional
    class 공연정보_생성{
        @Test
        void 성공() {
            //given
            EventInfoRequest request = new EventInfoRequest(
                    "뮤지컬 <시카고>",
                    "테스트 내용 내용 내용",
                    "174분",
                    "18세",
                    LocalDateTime.of(2024, 5, 27, 16, 0),
                    1L,
                    5L
            );
            int lengthBefore = eventInfoRepository.findAll().size();
            //when
            eventInfoController.createEventInfo(request);
            //then
            assertEquals(eventInfoRepository.findAll().size(), lengthBefore + 1);
        }
    }
    @Nested
    @Transactional
    class 공연정보_수정 {
        private Long id;
        @BeforeEach
        void setUp() {
            EventInfoRequest request = new EventInfoRequest(
                    "뮤지컬 <시카고>",
                    "테스트 내용 내용 내용",
                    "174분",
                    "18세",
                    LocalDateTime.of(2024, 5, 27, 16, 0),
                    1L,
                    5L
            );
            id = eventInfoRepository.save(request.toEntity()).getEventInfoId();
            entityManager.flush();
            entityManager.clear();
        }
        @Test
        void 성공() {
            //given
            UpdateEventInfoRequest request = new UpdateEventInfoRequest(
                    "테스트제목",
                    "테스트 설명",
                    "189분",
                    "15세",
                    LocalDateTime.of(2024, 5, 27, 12, 0)
            );
            //when
            eventInfoController.updateEventInfo(id, request);
            //then
            EventInfo eventInfo = eventInfoRepository.findById(id).get();
            assertEquals(request.title(), eventInfo.getTitle());
            assertEquals(request.description(), eventInfo.getDescription());
            assertEquals(request.runningTime(), eventInfo.getRunningTime());
            assertEquals(request.runningTime(), eventInfo.getRunningTime());
            assertEquals(request.bookStartTime(), eventInfo.getBookStartTime());
        }
    }
}