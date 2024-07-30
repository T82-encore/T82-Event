package com.T82.event.dto.request;

import com.T82.event.domain.Event;
import com.T82.event.domain.EventInfo;
import com.T82.event.domain.EventPlace;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InitPlaceRequest {
    private String placeName;

    private String address;

    private int totalSeat;

    private boolean seatAvailable;

    private int totalRow;

    private int totalCol;

    public EventPlace toEntity( ){
        return EventPlace.builder()
                .placeName(placeName)
                .address(address)
                .totalSeat(totalSeat)
                .seatAvailable(seatAvailable)
                .totalRow(totalRow)
                .totalCol(totalCol)
                .build();
    }
}
