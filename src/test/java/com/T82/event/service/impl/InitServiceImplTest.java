package com.T82.event.service.impl;

import com.T82.event.domain.*;
import org.junit.jupiter.api.Test;

import com.T82.event.domain.repository.*;
import com.T82.event.dto.request.InitCategoryRequest;
import com.T82.event.dto.request.InitPlaceRequest;
import com.T82.event.dto.request.InitSeatGradeRequest;
import com.T82.event.dto.request.InitSectionRequest;
import org.junit.jupiter.api.BeforeEach;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class InitServiceImplTest {

    @Mock
    private EventPlaceRepository eventPlaceRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private SectionRepository sectionRepository;

    @Mock
    private SeatGradeInfoRepository seatGradeInfoRepository;

    @Mock
    private EventInfoRepository eventInfoRepository;

    @InjectMocks
    private InitServiceImpl initService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Mockito 초기화
    }

    @Test
    void testInitPlace() {
        InitPlaceRequest request = mock(InitPlaceRequest.class);
        EventPlace eventPlace = mock(EventPlace.class);

        when(request.toEntity()).thenReturn(eventPlace);

        initService.initPlace(request);

        verify(eventPlaceRepository, times(1)).save(eventPlace);
    }

    @Test
    void testInitCategory_withParent() {
        InitCategoryRequest request = mock(InitCategoryRequest.class);
        when(request.getParentId()).thenReturn(1L);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(mock(Category.class)));
        when(request.toEntity()).thenReturn(mock(Category.class));

        initService.initCategory(request);

        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testInitCategory_withoutParent() {
        InitCategoryRequest request = mock(InitCategoryRequest.class);
        when(request.getParentId()).thenReturn(0L);
        when(request.toEntity()).thenReturn(mock(Category.class));

        initService.initCategory(request);

        verify(categoryRepository, times(0)).findById(anyLong());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testInitSection() {
        InitSectionRequest request = mock(InitSectionRequest.class);
        EventPlace eventPlace = mock(EventPlace.class);
        when(eventPlaceRepository.findById(1L)).thenReturn(Optional.of(eventPlace));
        when(request.toEntity(eventPlace)).thenReturn(mock(Section.class));

        initService.initSection(1L, request);

        verify(eventPlaceRepository, times(1)).findById(1L);
        verify(sectionRepository, times(1)).save(any(Section.class));
    }

    @Test
    void testInitSeatGrade() {
        InitSeatGradeRequest request = mock(InitSeatGradeRequest.class);
        EventInfo eventInfo = mock(EventInfo.class);
        Section section = mock(Section.class);
        when(eventInfoRepository.findById(1L)).thenReturn(Optional.of(eventInfo));
        when(sectionRepository.findById(1L)).thenReturn(Optional.of(section));
        when(request.toEntity(eventInfo, section)).thenReturn(mock(SeatGradeInfo.class));

        initService.initSeatGrade(1L, 1L, request);

        verify(eventInfoRepository, times(1)).findById(1L);
        verify(sectionRepository, times(1)).findById(1L);
        verify(seatGradeInfoRepository, times(1)).save(any(SeatGradeInfo.class));
    }
}
