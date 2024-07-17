package com.T82.event.domain.repository;

import com.T82.event.domain.Event;
import com.T82.event.domain.EventInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    Optional<Event> findByEventIdAndIsDeletedFalse(Long eventId);

    List<Event> findAllByEventInfoAndBookEndTimeAfterAndIsDeletedIsFalse(EventInfo eventInfo , LocalDateTime currentTime);

}
