package com.T82.event.domain.repository;

import com.T82.event.dto.response.SectionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SectionDAO {
    private final SectionRepository sectionRepository;

    public List<SectionDto> getSectionDataList(Long eventInfoId) {
        return sectionRepository.getSectionDataList(eventInfoId);
    }
}
