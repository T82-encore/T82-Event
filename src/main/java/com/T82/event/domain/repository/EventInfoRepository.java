package com.T82.event.domain.repository;

import com.T82.event.domain.Category;
import com.T82.event.domain.EventInfo;
import com.T82.event.dto.response.EventDto;
import com.T82.event.dto.response.SectionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventInfoRepository extends JpaRepository<EventInfo, Long> {
    Optional<EventInfo> findByCategory(Category category);

    //현재 시간으로 예매시간이 빠른순으로 정렬
    @Query(value = "SELECT * FROM event_infos WHERE book_start_time > :now ORDER BY book_start_time ASC LIMIT 10", nativeQuery = true)
    List<EventInfo> findComingEvents(@Param("now") LocalDateTime now);
    @Query("SELECT e FROM EventInfo e WHERE e.category.categoryId IN :categoryIds ORDER BY e.rating DESC ")
    List<EventInfo> findByCategoryIds(@Param("categoryIds") List<Long> categoryIds, PageRequest pageRequest);
    @Query("SELECT e FROM EventInfo e WHERE e.bookStartTime > :currentDateTime AND e.category.categoryId IN :categoryIds ORDER BY e.bookStartTime ASC")
    List<EventInfo> findNextUpcomingEvents(@Param("categoryIds") List<Long> categoryIds, PageRequest pageRequest, LocalDateTime currentDateTime);
    @Query("SELECT e FROM EventInfo e WHERE e.deleted = false ORDER BY e.sellCount DESC")
    List<EventInfo> findTop10BySellCountDesc(PageRequest pageRequest);
//    @Query("SELECT e FROM EventInfo e WHERE e.category.categoryId = :categoryId OR e.category.parentId = :categoryId")
    Page<EventInfo> findAllByCategoryAndDeletedFalse(Category category, Pageable pageable);
    @Query("select s.sectionName sectionName, s.startRow startRow, s.startCol startCol, s.seatRowCount rowNum, " +
            "s.seatColumnsCount colNum, sgi.price price, s.seatRowCount * s.seatColumnsCount sectionTotalSeat " +
            "FROM EventInfo ei " +
            "JOIN EventPlace ep ON ei.eventPlace.placeId = ep.placeId " +
            "JOIN Section s ON ep.placeId = s.eventPlace.placeId " +
            "JOIN SeatGradeInfo sgi ON s.sectionId = sgi.section.sectionId AND ei.eventInfoId = sgi.eventInfo.eventInfoId " +
            "WHERE ei.eventInfoId = :eventInfoId")
    List<SectionDto> test(@Param("eventInfoId") Long eventInfoId);
}
