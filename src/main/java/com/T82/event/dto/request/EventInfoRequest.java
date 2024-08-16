package com.T82.event.dto.request;

import com.T82.event.domain.Category;
import com.T82.event.domain.EventInfo;
import com.T82.event.domain.EventPlace;

import java.time.LocalDateTime;

public record EventInfoRequest(
        String title,
        String description,
        String runningTime,
        String ageRestriction,
        LocalDateTime bookStartTime,
        Long placeId,
        Long categoryId,

        String imageUrl
) {
    public EventInfo toEntity() {
        return EventInfo.builder()
                .title(title)
                .description(description)
                .runningTime(runningTime)
                .ageRestriction(ageRestriction)
                .bookStartTime(bookStartTime)
                .eventPlace(EventPlace.builder().placeId(placeId).build())
                .category(Category.builder().categoryId(categoryId).build())
                .imageUrl(imageUrl)
                .build();
    }
}
