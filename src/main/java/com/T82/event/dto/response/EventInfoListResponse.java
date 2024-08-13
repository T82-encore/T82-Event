package com.T82.event.dto.response;

import com.T82.event.domain.EventInfo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record EventInfoListResponse(
        Long eventInfoId,
        String title,
        Double rating,

        LocalDateTime bookStartTime
        ) {
    public static EventInfoListResponse from(EventInfo eventInfo) {
        return new EventInfoListResponse(
                eventInfo.getEventInfoId(),
                eventInfo.getTitle(),
                eventInfo.getRating(),
                eventInfo.getBookStartTime()
        );
    }
}
