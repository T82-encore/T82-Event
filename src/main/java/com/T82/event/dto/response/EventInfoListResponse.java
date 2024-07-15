package com.T82.event.dto.response;

import com.T82.event.domain.EventInfo;

public record EventInfoListResponse(
        Long eventInfoId,
        String title,
        Double rating
        ) {
    public static EventInfoListResponse from(EventInfo eventInfo) {
        return new EventInfoListResponse(
                eventInfo.getEventInfoId(),
                eventInfo.getTitle(),
                eventInfo.getRating()
        );
    }
}
