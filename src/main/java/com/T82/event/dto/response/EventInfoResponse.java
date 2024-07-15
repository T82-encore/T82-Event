package com.T82.event.dto.response;

import com.T82.event.domain.EventInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventInfoResponse {

    private String title;
    private String description;
    private Double rating;
    private String runningTime;
    private String ageRestriction;
    private String placeName;
    private Integer totalSeat;


    public static EventInfoResponse fromEntity(EventInfo eventInfo){
        return EventInfoResponse.builder()
                .title(eventInfo.getTitle())
                .description(eventInfo.getDescription())
                .rating(eventInfo.getRating())
                .runningTime(eventInfo.getRunningTime())
                .ageRestriction(eventInfo.getAgeRestriction())
                .placeName(eventInfo.getEventPlace().getPlaceName())
                .totalSeat(eventInfo.getEventPlace().getTotalSeat())
                .build();
    }
}
