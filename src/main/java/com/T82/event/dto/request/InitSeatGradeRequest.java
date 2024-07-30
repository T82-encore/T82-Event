package com.T82.event.dto.request;

import com.T82.event.domain.EventInfo;
import com.T82.event.domain.SeatGradeInfo;
import com.T82.event.domain.Section;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InitSeatGradeRequest {
    private int price;


    public SeatGradeInfo toEntity(EventInfo eventInfo , Section section){
        return SeatGradeInfo.builder()
                .eventInfo(eventInfo)
                .section(section)
                .price(price)
                .build();

    }
}
