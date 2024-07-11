package com.T82.event.service.impl;

import com.T82.event.domain.EventInfo;
import com.T82.event.domain.repository.EventInfoRepository;
import com.T82.event.dto.request.EventInfoRequest;
import com.T82.event.service.EventInfoService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventInfoServiceImplTest {
    @Autowired
    private EventInfoService eventInfoService;
    @Autowired
    private EventInfoRepository eventInfoRepository;

    @Nested
    @Transactional
    class 공연정보_생성{
        @Test
        void 성공() {
            //given
            EventInfoRequest request = new EventInfoRequest(
                    "뮤지컬 <시카고>",
                    "테스트 내용 내용 내용",
                    "174분", "18세",
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
}