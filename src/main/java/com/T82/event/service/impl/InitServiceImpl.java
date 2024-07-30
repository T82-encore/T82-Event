package com.T82.event.service.impl;

import com.T82.event.domain.EventInfo;
import com.T82.event.domain.EventPlace;
import com.T82.event.domain.Section;
import com.T82.event.domain.repository.*;
import com.T82.event.dto.request.InitCategoryRequest;
import com.T82.event.dto.request.InitPlaceRequest;
import com.T82.event.dto.request.InitSeatGradeRequest;
import com.T82.event.dto.request.InitSectionRequest;
import com.T82.event.service.InitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InitServiceImpl implements InitService {
    private final EventPlaceRepository eventPlaceRepository;
    private final CategoryRepository categoryRepository;
    private final SectionRepository sectionRepository;
    private final SeatGradeInfoRepository seatGradeInfoRepository;
    private final EventInfoRepository eventInfoRepository;

    @Override
    public void initPlace(InitPlaceRequest initPlaceRequest) {
        eventPlaceRepository.save(initPlaceRequest.toEntity());
    }

    @Override
    public void initCategory(InitCategoryRequest initCategoryRequest) {
        if(initCategoryRequest.getParentId() != 0) {
            categoryRepository.findById(initCategoryRequest.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 카테고리 없음"));
            categoryRepository.save(initCategoryRequest.toEntity());
        }else categoryRepository.save(initCategoryRequest.toEntity());
    }

    @Override
    public void initSection(Long placeId, InitSectionRequest initSectionRequest) {
        EventPlace eventPlace = eventPlaceRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 장소가 존재하지 않습니다."));

        sectionRepository.save(initSectionRequest.toEntity(eventPlace));
    }

    @Override
    public void initSeatGrade(Long eventInfoId, Long sectionId, InitSeatGradeRequest initSeatGradeRequest) {
        EventInfo eventInfo = eventInfoRepository.findById(eventInfoId)
                .orElseThrow(() -> new IllegalArgumentException("해당 이벤트 정보가 존재하지 않습니다"));
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 구역이 존재하지 않습니다."));

        seatGradeInfoRepository.save(initSeatGradeRequest.toEntity(eventInfo,section));
    }
}
