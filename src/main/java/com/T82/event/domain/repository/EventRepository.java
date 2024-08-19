package com.T82.event.domain.repository;

import com.T82.event.domain.Event;
import com.T82.event.domain.EventInfo;
import com.T82.event.dto.response.EventDetail;
import com.T82.event.dto.response.EventDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    Optional<Event> findByEventIdAndIsDeletedFalse(Long eventId);

    List<Event> findAllByEventInfoAndBookEndTimeAfterAndIsDeletedIsFalse(EventInfo eventInfo , LocalDateTime currentTime);
    @Query("select ei.eventInfoId eventInfoId, ei.title title, e.eventStartTime eventStartTime, ei.imageUrl imageUrl from Event e join EventInfo ei on e.eventInfo.eventInfoId = ei.eventInfoId where e.eventId = :eventId")
    EventDetail findEventDetailByEventId(@Param("eventId") Long eventId);
//    @Query("select e.eventId eventId, ep.placeName placeName, ep.address address, ep.seatAvailable seatAvailAble, " +
//            "ep.totalSeat totalSeat, ep.totalRow totalRow, ep.totalCol totalCol, s.sectionName sectionName, " +
//            "s.startRow startRow, s.startCol startCol, s.seatRowCount rowNum, s.seatColumnsCount colNum, " +
//            "sgi.price price, s.seatRowCount * s.seatColumnsCount sectionTotalSeat " +
//            "FROM Event e " +
//            "JOIN EventInfo ei ON e.eventInfo.eventInfoId = ei.eventInfoId " +
//            "JOIN EventPlace ep ON ei.eventPlace.placeId = ep.placeId " +
//            "JOIN Section s ON ep.placeId = s.eventPlace.placeId " +
//            "JOIN SeatGradeInfo sgi ON s.sectionId = sgi.section.sectionId AND ei.eventInfoId = sgi.eventInfo.eventInfoId " +
//            "WHERE e.eventId = :eventId")
//    EventDto test(@Param("eventId") Long eventId);
}
