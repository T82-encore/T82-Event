package com.T82.event.dto.response;

import java.time.LocalDateTime;

public interface EventDetail {
    Long getEventInfoId();
    String getTitle();
    LocalDateTime getEventStartTime();
    String getImageUrl();
}
