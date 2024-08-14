package com.T82.event.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Builder
@Getter
public class PushEventDto {
     Long eventId;
     Timestamp eventStartTime;
}
