package com.T82.event.domain.repository;

import com.T82.event.domain.Section;
import com.T82.event.dto.response.SectionDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SectionRepository extends JpaRepository<Section, Long> {
    @Query("select s.sectionName sectionName, s.startRow startRow, s.startCol startCol, s.seatRowCount rowNum, " +
            "s.seatColumnsCount colNum, sgi.price price, s.seatRowCount * s.seatColumnsCount sectionTotalSeat " +
            "FROM Section s " +
            "JOIN EventPlace ep ON s.eventPlace.placeId = ep.placeId " +
            "JOIN SeatGradeInfo sgi ON s.sectionId = sgi.section.sectionId  " +
            "WHERE sgi.eventInfo.eventInfoId = :eventInfoId")
    List<SectionDto> getSectionDataList(@Param("eventInfoId") Long eventInfoId);
}
