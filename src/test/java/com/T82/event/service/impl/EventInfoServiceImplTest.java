package com.T82.event.service.impl;

import com.T82.event.domain.EventInfo;
import com.T82.event.domain.repository.EventInfoRepository;
import com.T82.event.dto.request.EventInfoRequest;
import com.T82.event.dto.request.UpdateEventInfoRequest;
import com.T82.event.service.EventInfoService;
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
class EventInfoServiceImplTest {
    @Autowired
    private EventInfoService eventInfoService;
    @Autowired
    private EventInfoRepository eventInfoRepository;
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
            eventInfoService.createEventInfo(request);
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
            EventInfo eventInfo = eventInfoRepository.findById(id).get();
            //when
            eventInfoService.updateEventInfo(id, request);
            //then
            EventInfo eventInfo1 = eventInfoRepository.findById(id).get();
            assertEquals(request.title(), eventInfo1.getTitle());
            assertEquals(request.description(), eventInfo1.getDescription());
            assertEquals(request.runningTime(), eventInfo1.getRunningTime());
            assertEquals(request.runningTime(), eventInfo1.getRunningTime());
            assertEquals(request.bookStartTime(), eventInfo1.getBookStartTime());
        }
        @Test
        void 실패_데이터_없음() {
            //given
            UpdateEventInfoRequest request = new UpdateEventInfoRequest(
                    "테스트제목",
                    "테스트 설명",
                    "189분",
                    "15세",
                    LocalDateTime.of(2024, 5, 27, 12, 0)
            );
            Long failedId = id + 1;
            //when & then
            assertThrows(IllegalArgumentException.class, () -> eventInfoService.updateEventInfo(failedId, request));
        }
    }
}