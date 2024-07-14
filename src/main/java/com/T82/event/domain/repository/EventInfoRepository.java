package com.T82.event.domain.repository;

import com.T82.event.domain.EventInfo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EventInfoRepository extends JpaRepository<EventInfo, Long> {
    @Query("SELECT e FROM EventInfo e WHERE e.category.categoryId IN :categoryIds ORDER BY e.rating DESC ")
    List<EventInfo> findByCategoryIds(@Param("categoryIds") List<Long> categoryIds, PageRequest pageRequest);
    @Query("SELECT e FROM EventInfo e WHERE e.bookStartTime > :currentDateTime AND e.category.categoryId IN :categoryIds ORDER BY e.bookStartTime ASC")
    List<EventInfo> findNextUpcomingEvents(@Param("categoryIds") List<Long> categoryIds, PageRequest pageRequest, LocalDateTime currentDateTime);
}
