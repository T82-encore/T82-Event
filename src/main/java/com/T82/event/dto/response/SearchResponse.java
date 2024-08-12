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
public class SearchResponse {
    private Long eventInfoId;
    private String title;
    private String placeName;
    private String runningTime;


    public static SearchResponse fromEntity(EventInfo eventInfo){
        return SearchResponse.builder()
                .eventInfoId(eventInfo.getEventInfoId())
                .title(eventInfo.getTitle())
                .runningTime(eventInfo.getRunningTime())
                .placeName(eventInfo.getEventPlace().getPlaceName())
                .build();
    }

}
