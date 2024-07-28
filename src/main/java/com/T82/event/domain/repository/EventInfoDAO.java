package com.T82.event.domain.repository;

import com.T82.event.domain.EventInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EventInfoDAO {
    private final EventInfoRepository eventInfoRepository;

    public Optional<EventInfo> findById(long id) {eventInfoRepository.findById(id);
        return Optional.empty();
    }
}
