package com.T82.event.domain.repository;

import com.T82.event.domain.EventInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventInfoRepository extends JpaRepository<EventInfo, Long> {

}
