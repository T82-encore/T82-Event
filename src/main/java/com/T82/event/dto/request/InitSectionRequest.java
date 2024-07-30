package com.T82.event.dto.request;

import com.T82.event.domain.EventPlace;
import com.T82.event.domain.Section;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InitSectionRequest {
    private int startRow;
    private int startCol;
    private Long seatRowCount;
    private Long seatColumnsCount;
    private String sectionName;


    public Section toEntity(EventPlace eventPlace){
        return  Section.builder()
                .startRow(startRow)
                .startCol(startCol)
                .seatRowCount(seatRowCount)
                .seatColumnsCount(seatColumnsCount)
                .sectionName(sectionName)
                .eventPlace(eventPlace)
                .build();
    }

}
