package com.T82.event.dto.response;

import com.T82.event.domain.EventInfo;
import com.T82.event.domain.EventPlace;
import lombok.Getter;

import java.util.List;

@Getter
public class EventDto {
    Long eventId;
    String placeName;
    String address;
    boolean seatAvailable;
    int totalSeat;
    Integer totalRow;
    Integer totalCol;
    List<SectionDto> sectionInitRequest;

    public EventDto(Long eventId, EventPlace eventPlace, List<SectionDto> list) {
        this.eventId = eventId;
        this.placeName = eventPlace.getPlaceName();
        this.address = eventPlace.getAddress();
        this.seatAvailable = eventPlace.getSeatAvailable();
        this.totalSeat = eventPlace.getTotalSeat();
        this.totalRow = eventPlace.getTotalRow();
        this.totalCol = eventPlace.getTotalCol();
        this.sectionInitRequest = list;
    }
}
