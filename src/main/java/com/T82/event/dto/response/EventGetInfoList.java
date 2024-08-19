package com.T82.event.dto.response;

import com.T82.event.domain.Event;
import com.T82.event.domain.EventInfo;
import com.T82.event.domain.EventPlace;
import com.T82.event.domain.Section;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventGetInfoList{
    private Long eventId;
    private LocalDateTime eventStartTime;
    private Long eventSellCount;



    public static EventGetInfoList fromEntity(Event event){
        return EventGetInfoList.builder()
                .eventId(event.getEventId())
                .eventStartTime(event.getEventStartTime().minusHours(9))
                .eventSellCount(event.getEventSellCount())
                .build();
    }
}
