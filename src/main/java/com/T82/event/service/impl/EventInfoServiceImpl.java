package com.T82.event.service.impl;

import com.T82.event.domain.Category;
import com.T82.event.domain.EventInfo;
import com.T82.event.domain.repository.CategoryRepository;
import com.T82.event.domain.repository.EventInfoRepository;
import com.T82.event.dto.request.EventInfoRequest;
import com.T82.event.dto.request.UpdateEventInfoRequest;
import com.T82.event.dto.response.EventGetEarliestOpenTicket;
import com.T82.event.dto.response.EventInfoListResponse;
import com.T82.event.dto.response.EventInfoResponse;
import com.T82.event.service.EventInfoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventInfoServiceImpl implements EventInfoService {
    private final EventInfoRepository eventInfoRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void createEventInfo(EventInfoRequest request) {

        eventInfoRepository.save(request.toEntity());
    }

    @Override
    @Transactional
    public void updateEventInfo(Long id, UpdateEventInfoRequest request) {
        EventInfo eventInfo = eventInfoRepository
                .findById(id)
                .orElseThrow(IllegalArgumentException::new);
        eventInfo.setTitle(request.title());
        eventInfo.setDescription(request.description());
        eventInfo.setAgeRestriction(request.ageRestriction());
        eventInfo.setRunningTime(request.runningTime());
        eventInfo.setBookStartTime(request.bookStartTime());
    }

    @Override
    @Transactional
    public void deleteEventInfo(Long id) {
        EventInfo eventInfo = eventInfoRepository
                .findById(id)
                .orElseThrow(IllegalArgumentException::new);
        eventInfo.setDeleted(true);
    }

    @Override
    public List<EventInfoListResponse> getEventInfoListByHighCategoryId(Long id) {
        List<Long> subCategoryIds = categoryRepository.findSubCategoryIdsByParentId(id);
        if (subCategoryIds.isEmpty()) throw new IllegalArgumentException("잘못된 부모 카테고리입니다");

        return eventInfoRepository
                .findByCategoryIds(subCategoryIds, PageRequest.of(0, 10))
                .stream()
                .map(EventInfoListResponse::from)
                .toList();
    }

    @Override
    public List<EventInfoListResponse> getNextUpcomingEvents(Long categoryId) {
        List<Long> subCategoryIds = categoryRepository.findSubCategoryIdsByParentId(categoryId);
        if (subCategoryIds.isEmpty()) throw new IllegalArgumentException("잘못된 부모 카테고리입니다");
        LocalDateTime currentDateTime = LocalDateTime.now();
        return eventInfoRepository
                .findNextUpcomingEvents(subCategoryIds, PageRequest.of(0, 10), currentDateTime)
                .stream()
                .map(EventInfoListResponse::from)
                .toList();
    }

    @Override
    public List<EventInfoListResponse> getTopSellingEvents() {
        return eventInfoRepository
                .findTop10BySellCountDesc(PageRequest.of(0, 10))
                .stream()
                .map(EventInfoListResponse::from)
                .toList();
    }

    @Override
    public Page<EventInfoListResponse> getEventInfosByCategoryId(Long categoryId, Pageable pageable) {
        Category category = Category.builder().categoryId(categoryId).build();
        return eventInfoRepository
                .findAllByCategoryAndDeletedFalse(category, pageable)
                .map(EventInfoListResponse::from);
    }
  
    @Override
    public List<EventGetEarliestOpenTicket> getEarliestOpenEventInfo() {
        List<EventInfo> comingEvents = eventInfoRepository.findComingEvents(LocalDateTime.now());

        return comingEvents.stream().map(EventGetEarliestOpenTicket :: fromEntity).toList();
    }

    @Override
    public EventInfoResponse getEventInfo(Long eventInfoId) {
      EventInfo eventInfo =  eventInfoRepository.findById(eventInfoId).orElseThrow(()
              -> new IllegalArgumentException("해당 공연정보가 없습니다."));

        return EventInfoResponse.fromEntity(eventInfo);
    }
}
