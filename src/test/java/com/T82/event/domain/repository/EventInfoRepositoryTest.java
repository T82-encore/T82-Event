package com.T82.event.domain.repository;

import com.T82.event.domain.EventInfo;
import com.T82.event.dto.request.EventInfoRequest;
import com.T82.event.dto.response.EventInfoListResponse;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

class EventInfoRepositoryTest {
    @Autowired
    private EventInfoRepository eventInfoRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private CategoryRepository categoryRepository;

    @Nested
    @Transactional
    class 선택한_카테고리_하위_티켓_중에서_오픈일이_빠른_티켓_10개_전달 {
        @BeforeEach
        void setUp() {
            for(int l = 1; l < 5; l++) {
                for(int i = 4; i < 10; i++) {
                    EventInfoRequest request = new EventInfoRequest(
                            "테스트 제목"+l+i,
                            "테스트 내용"+l+i,
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
            LocalDateTime currentDateTime = LocalDateTime.now();
            List<Long> categoryIds = categoryRepository.findSubCategoryIdsByParentId(categoryId);
            //when
            List<EventInfo> list = eventInfoRepository
                    .findNextUpcomingEvents(
                            categoryIds,
                            PageRequest.of(0, 10),
                            currentDateTime);
            //then
            assertTrue(list.size() <= 10 && list.size() >= 5);
            for(int i = 0; i < list.size() - 1; i++) {
                assertTrue(list.get(i).getBookStartTime().compareTo(list.get(i+1).getBookStartTime()) <= 0);
            }
        }
    }

    @Nested
    @Transactional
    class 현재_오픈된_티켓_중에서_판매량이_가장_많은_공연정보_10개_출력 {
        @BeforeEach
        void setUp() {
            for(int l = 1; l < 5; l++) {
                for(int i = 4; i < 10; i++) {
                    EventInfo eventInfo = new EventInfoRequest(
                            "테스트 제목"+l+i,
                            "테스트 내용"+l+i,
                            "174분",
                            "18세",
                            LocalDateTime.of(2024, 8, l, i, 0),
                            1L,
                            (long) i
                    ).toEntity();
                    eventInfo.setSellCount(l * 100 + l * i);
                    if(i % 3 == 0) eventInfo.setDeleted(true);
                    eventInfoRepository.save(eventInfo);
                }
            }
            entityManager.flush();
            entityManager.clear();
        }
        @Test
        void 성공() {
            //given & when
            List<EventInfo> list = eventInfoRepository.findTop10BySellCountDesc(PageRequest.of(0, 10));
            //then
            assertTrue(list.size() <= 10 && list.size() >= 5);
            for(int i = 0; i < list.size() - 1; i++) {
                assertTrue(list.get(i).getSellCount() >= list.get(i+1).getSellCount());
                assertFalse(list.get(i).isDeleted());
            }
        }
    }
}