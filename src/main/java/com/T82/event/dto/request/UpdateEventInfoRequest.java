package com.T82.event.dto.request;

import java.time.LocalDateTime;

public record UpdateEventInfoRequest(
        String title,
        String description,
        String runningTime,
        String ageRestriction,
        LocalDateTime bookStartTime
) {
}
