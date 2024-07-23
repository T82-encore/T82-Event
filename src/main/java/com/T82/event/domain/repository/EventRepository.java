package com.T82.event.domain.repository;

import com.T82.event.domain.Event;
import com.T82.event.domain.EventInfo;
import com.T82.event.dto.response.EventDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByEventInfoAndBookEndTimeAfterAndIsDeletedIsFalse(EventInfo eventInfo , LocalDateTime currentTime);
    @Query("select ei.eventInfoId eventInfoId, ei.title title, e.eventStartTime eventStartTime from Event e join EventInfo ei on e.eventInfo.eventInfoId = ei.eventInfoId where e.eventId = :eventId")
    EventDetail findEventDetailByEventId(@Param("eventId") Long eventId);
}
