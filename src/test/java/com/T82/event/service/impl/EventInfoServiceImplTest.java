package com.T82.event.service.impl;

import com.T82.event.domain.Category;
import com.T82.event.domain.EventInfo;
import com.T82.event.domain.EventPlace;
import com.T82.event.domain.repository.CategoryRepository;
import com.T82.event.domain.repository.EventInfoRepository;
import com.T82.event.domain.repository.EventPlaceRepository;
import com.T82.event.dto.request.EventInfoRequest;
import com.T82.event.dto.request.UpdateEventInfoRequest;
import com.T82.event.dto.response.EventInfoListResponse;
import com.T82.event.dto.response.EventInfoResponse;
import com.T82.event.dto.response.SearchResponse;
import com.T82.event.service.EventInfoService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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

    @Autowired
    private EventPlaceRepository eventPlaceRepository;

    @Nested
    @Transactional
    class 공연정보_생성 {
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
            for (int l = 1; l < 5; l++) {
                for (int i = 4; i < 10; i++) {
                    EventInfoRequest request = new EventInfoRequest(
                            "테스트 제목" + l + i,
                            "테스트 내용" + l + i,
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
            for (int i = 0; i < list.size() - 1; i++) {
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

    @Nested
    @Transactional
    class 선택한_카테고리_하위_티켓_중에서_오픈일이_빠른_티켓_10개_전달 {
        @BeforeEach
        void setUp() {
            for (int l = 1; l < 5; l++) {
                for (int i = 4; i < 10; i++) {
                    EventInfoRequest request = new EventInfoRequest(
                            "테스트 제목" + l + i,
                            "테스트 내용" + l + i,
                            "174분",
                            "18세",
                            LocalDateTime.of(2024, 8, l, i, 0),
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
            List<EventInfoListResponse> list = eventInfoService.getNextUpcomingEvents(categoryId);
            //then
            assertTrue(list.size() <= 10 && list.size() >= 5);
            for (int i = 0; i < list.size() - 1; i++) {
                assertTrue(categoryIds.contains(eventInfoRepository.findById(list.get(i).eventInfoId()).get().getCategory().getCategoryId()));
            }
        }

        @Test
        void 실패_자식_카테고리_아이디_전달() {
            //given
            Long categoryId = 5L;
            //when & then
            assertThrows(IllegalArgumentException.class, () -> eventInfoService.getNextUpcomingEvents(categoryId));
        }
    }

    @Nested
    @Transactional
    class 현재_오픈된_티켓_중에서_판매량이_가장_많은_공연정보_10개_출력 {
        @BeforeEach
        void setUp() {
            for (int l = 1; l < 5; l++) {
                for (int i = 4; i < 10; i++) {
                    EventInfo eventInfo = new EventInfoRequest(
                            "테스트 제목" + l + i,
                            "테스트 내용" + l + i,
                            "174분",
                            "18세",
                            LocalDateTime.of(2024, 8, l, i, 0),
                            1L,
                            (long) i
                    ).toEntity();
                    eventInfo.setSellCount(l * 100 + l * i);
                    eventInfoRepository.save(eventInfo);
                }
            }
            entityManager.flush();
            entityManager.clear();
        }

        @Test
        void 성공() {
            //given & when
            List<EventInfoListResponse> list = eventInfoService.getTopSellingEvents();
            //then
            assertTrue(list.size() <= 10 && list.size() >= 5);
        }
    }

    @Nested
    @Transactional
    class 하위_카테고리_별_공연정보_리스트_10개씩_페이징 {
        @BeforeEach
        void setUp() {
            for (int l = 1; l < 17; l++) {
                for (int i = 4; i < 8; i++) {
                    EventInfo eventInfo = new EventInfoRequest(
                            "테스트 제목" + l + i,
                            "테스트 내용" + l + i,
                            "174분",
                            "18세",
                            LocalDateTime.of(2024, 8, l, i, 0),
                            1L,
                            (long) i
                    ).toEntity();
                    if (l % 3 == 0) eventInfo.setDeleted(true);
                    eventInfoRepository.save(eventInfo);
                }
            }
            entityManager.flush();
            entityManager.clear();
        }

        @Test
        void 성공_1페이지() {
            //given
            Long categoryId = 5L;
            Pageable pageable = PageRequest.of(0, 10);
            //when
            Page<EventInfoListResponse> page1 = eventInfoService.getEventInfosByCategoryId(categoryId, pageable);
            //then
            assertEquals(10, page1.getNumberOfElements());
        }

        @Test
        void 성공_2페이지() {
            //given
            Long categoryId = 5L;
            Pageable pageable = PageRequest.of(1, 10);
            //when
            Page<EventInfoListResponse> page1 = eventInfoService.getEventInfosByCategoryId(categoryId, pageable);
            //then
            assertEquals(1, page1.getNumberOfElements());
        }
    }

    @Nested
    @Transactional
    class 공연정보_반환 {

        @Test
        void 성공() {
            //given
            Long eventInfoId = 1L;
            EventInfo eventInfo = eventInfoRepository.findById(eventInfoId).get();
            //when
            EventInfoResponse response = eventInfoService.getEventInfo(eventInfoId);
            //then
            assertNotNull(response);
            assertEquals(eventInfo.getTitle(), response.getTitle());
            assertEquals(eventInfo.getDescription(), response.getDescription());
            assertEquals(eventInfo.getRunningTime(), response.getRunningTime());
            assertEquals(eventInfo.getRating(), response.getRating());
            assertEquals(eventInfo.getAgeRestriction(), response.getAgeRestriction());
            assertEquals(eventInfo.getEventPlace().getPlaceName(), response.getPlaceName());
            assertEquals(eventInfo.getEventPlace().getTotalSeat(), response.getTotalSeat());
        }

        @Test
        void 실패_데이터_없음() {
            //given
            Long eventInfoId = 999L;
            //when & then
            assertThrows(IllegalArgumentException.class, () -> eventInfoService.getEventInfo(eventInfoId));
        }
    }

    @Nested
    @Transactional
    class 공연_검색 {

        @BeforeEach
        void setUp() {
            // 카테고리 엔티티를 먼저 생성
            Category category = Category.builder()
                    .categoryName("공연")
                    .build();
                    categoryRepository.save(category);

            EventPlace place = EventPlace.builder()
                    .placeName("홍범댁")
                    .build();

                eventPlaceRepository.save(place);

            // 여러 개의 이벤트를 저장
            for (int l = 1; l < 5; l++) {
                for (int i = 4; i < 10; i++) {
                    EventInfoRequest request = new EventInfoRequest(
                            "홍범 항복" + l + i,
                            "테스트 내용" + l + i,
                            "174분",
                            "18세",
                            LocalDateTime.of(2024, 5, 27, 16, 0),
                            1L,
                            1L// 저장된 카테고리 ID 사용
                    );
                    eventInfoRepository.save(request.toEntity());
                }
            }

            entityManager.flush();
            entityManager.clear();
        }


        @Test
        void 공연_검색() {
            //given
            String keyWord = "홍범";

            //when
            List<SearchResponse> searchResults = eventInfoService.searchByTitle(keyWord);

            //then
            // 검색된 모든 결과가 기대한 대로 "홍범"을 포함하는지 확인
            for (SearchResponse response : searchResults) {
                assertTrue(response.getTitle().contains(keyWord), "검색 결과의 제목이 키워드를 포함해야 합니다.");
            }
        }
    }
}