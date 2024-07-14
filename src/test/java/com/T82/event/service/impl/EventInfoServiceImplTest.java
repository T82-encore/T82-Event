package com.T82.event.service.impl;

import com.T82.event.domain.EventInfo;
import com.T82.event.domain.repository.CategoryRepository;
import com.T82.event.domain.repository.EventInfoRepository;
import com.T82.event.dto.request.EventInfoRequest;
import com.T82.event.dto.request.UpdateEventInfoRequest;
import com.T82.event.dto.response.EventInfoListResponse;
import com.T82.event.service.EventInfoService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventInfoServiceImplTest {
    @Autowired
    private EventInfoService eventInfoService;
    @Autowired
    private EventInfoRepository eventInfoRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private CategoryRepository categoryRepository;

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
    @Nested
    @Transactional
    class 공연정보_삭제 {
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
            Long eventInfoId = id;
            //when
            eventInfoService.deleteEventInfo(eventInfoId);
            //then
            assertTrue(eventInfoRepository.findById(eventInfoId).get().isDeleted());
        }
        @Test
        void 실패_데이터_없음() {
            //given
            Long failedId = id + 1;
            //when & then
            assertThrows(IllegalArgumentException.class, () -> eventInfoService.deleteEventInfo(failedId));
        }
    }
    @Nested
    @Transactional
    class 상위_카테고리_기준으로_판매량_10위까지_리스트로_전달 {
        @BeforeEach
        void setUp() {
            for(int l = 1; l < 5; l++) {
                for(int i = 4; i < 10; i++) {
                    EventInfoRequest request = new EventInfoRequest(
                            "테스트 제목"+l+i,
                            "테스트 내용"+l+i,
                            "174분",
                            "18세",
                            LocalDateTime.of(2024, 5, 27, 16, 0),
                            1L,
                            (long) i
                    );
                    eventInfoRepository.save(request.toEntity());
                }
            }

            entityManager.flush();
            entityManager.clear();
        }
        @Test
        void 성공() {
            //given
            Long categoryId = 1L;
            List<Long> categoryIds = categoryRepository.findSubCategoryIdsByParentId(categoryId);
            //when
            List<EventInfoListResponse> list = eventInfoService.getEventInfoListByHighCategoryId(categoryId);
            //then
            assertTrue(list.size() <= 10);
            for(int i = 0; i < list.size() - 1; i++) {
                assertTrue(list.get(i).rating() <= list.get(i + 1).rating());
                assertTrue(categoryIds.contains(eventInfoRepository.findById(list.get(i).eventInfoId()).get().getCategory().getCategoryId()));
            }
        }
        @Test
        void 실패_자식_카테고리_아이디_전달() {
            //given
            Long categoryId = 5L;
            //when & then
            assertThrows(IllegalArgumentException.class, () -> eventInfoService.getEventInfoListByHighCategoryId(categoryId));
            //then
        }
    }
}