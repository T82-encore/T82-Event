package com.T82.event.service;

import com.T82.event.dto.request.InitCategoryRequest;
import com.T82.event.dto.request.InitPlaceRequest;
import com.T82.event.dto.request.InitSeatGradeRequest;
import com.T82.event.dto.request.InitSectionRequest;

public interface InitService {
    void initPlace(InitPlaceRequest initPlaceRequest);

    void initCategory(InitCategoryRequest initCategoryRequest);

    void initSection(Long placeId, InitSectionRequest initSectionRequest);

    void initSeatGrade(Long eventInfoId, Long sectionId, InitSeatGradeRequest initSeatGradeRequest);
}
