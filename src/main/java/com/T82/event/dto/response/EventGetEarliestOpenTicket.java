package com.T82.event.dto.response;


import com.T82.event.domain.Event;
import com.T82.event.domain.EventInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventGetEarliestOpenTicket {
    private Long eventInfoId;
    private String title;
    private Double rating;

    public static EventGetEarliestOpenTicket fromEntity(EventInfo eventInfo){
        return EventGetEarliestOpenTicket.builder()
                .eventInfoId(eventInfo.getEventInfoId())
                .title(eventInfo.getTitle())
                .rating(eventInfo.getRating())
                .build();
    }
}
