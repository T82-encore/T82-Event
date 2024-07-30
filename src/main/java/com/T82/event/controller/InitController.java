package com.T82.event.controller;

import com.T82.event.dto.request.InitCategoryRequest;
import com.T82.event.dto.request.InitPlaceRequest;
import com.T82.event.dto.request.InitSeatGradeRequest;
import com.T82.event.dto.request.InitSectionRequest;
import com.T82.event.service.InitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/contents/")
@RequiredArgsConstructor
public class InitController {
    private final InitService initService;

    @PostMapping("/init/place")
    public void initPlace(@RequestBody InitPlaceRequest initPlaceRequest){
    initService.initPlace(initPlaceRequest);
    }

    @PostMapping("/init/category")
    public void initCategory(@RequestBody InitCategoryRequest initCategoryRequest){
        initService.initCategory(initCategoryRequest);
    }

    @PostMapping("/init/place/{placeId}/section")
    public void initSection(@PathVariable Long placeId, @RequestBody InitSectionRequest initSectionRequest){
        initService.initSection(placeId,initSectionRequest);
    }

    @PostMapping("/init/{eventInfoId}/section/{sectionId}/grade")
    public void initSection(@PathVariable Long eventInfoId,
            @PathVariable Long sectionId
            , @RequestBody InitSeatGradeRequest initSeatGradeRequest){
        initService.initSeatGrade(eventInfoId,sectionId,initSeatGradeRequest);
    }


}
