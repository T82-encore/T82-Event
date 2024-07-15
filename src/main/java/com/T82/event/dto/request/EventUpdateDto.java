package com.T82.event.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventUpdateDto {
    private LocalDateTime bookEndTime;

    private LocalDateTime eventStartTime;
}
