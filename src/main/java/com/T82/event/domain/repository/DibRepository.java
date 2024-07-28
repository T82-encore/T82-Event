package com.T82.event.domain.repository;

import com.T82.event.domain.Dib;
import com.T82.event.dto.response.MyDibListDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DibRepository extends JpaRepository<Dib, Long> {
    @Query("SELECT ei.eventInfoId eventInfoId, ei.title title, ei.bookStartTime bookStartTime " +
            "FROM Dib d JOIN EventInfo ei ON d.eventInfo.eventInfoId = ei.eventInfoId WHERE d.userId = :userId")
    List<MyDibListDto> getMyDibList(@Param("userId") UUID userId);
    Optional<Dib> findByUserIdAndEventInfo_EventInfoId(UUID userId, Long eventInfoId);
}
