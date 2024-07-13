package com.T82.event.domain.repository;

import com.T82.event.domain.Category;
import com.T82.event.domain.EventInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventInfoRepository extends JpaRepository<EventInfo, Long> {
    //현재 시간으로 예매시간이 빠른순으로 정렬
    @Query(value = "SELECT * FROM event_infos WHERE book_start_time > : now ORDER BY book_start_time DESC LIMIT 10", nativeQuery = true)
    List<EventInfo> findComingEvents(@Param("now") LocalDateTime now);
}
