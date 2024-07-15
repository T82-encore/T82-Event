package com.T82.event.domain.repository;

import com.T82.event.domain.Event;
import com.T82.event.domain.EventInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByEventInfoAndBookEndTimeAfterAndIsDeletedIsFalse(EventInfo eventInfo , LocalDateTime currentTime);

}
