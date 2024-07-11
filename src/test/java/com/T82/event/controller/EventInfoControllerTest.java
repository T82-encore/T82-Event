package com.T82.event.controller;

import com.T82.event.domain.repository.EventInfoRepository;
import com.T82.event.dto.request.EventInfoRequest;
import jakarta.transaction.Transactional;
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
}