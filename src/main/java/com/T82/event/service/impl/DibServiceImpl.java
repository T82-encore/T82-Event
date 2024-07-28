package com.T82.event.service.impl;

import com.T82.event.config.jwt.TokenInfo;
import com.T82.event.domain.Dib;
import com.T82.event.domain.repository.DibRepository;
import com.T82.event.domain.repository.EventInfoDAO;
import com.T82.event.dto.response.MyDibListDto;
import com.T82.event.service.DibService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class DibServiceImpl implements DibService {
    private final DibRepository dibRepository;
    private final EventInfoDAO eventInfoDAO;

    @Override
    @Transactional
    public void addDib(TokenInfo tokenInfo, Long eventInfoId) {
        dibRepository.findByUserIdAndEventInfo_EventInfoId(
                tokenInfo.id(),
                eventInfoId
        ).orElseThrow(IllegalArgumentException::new);

        dibRepository.save(Dib.toEntity(tokenInfo.id(), eventInfoId));
        eventInfoDAO.findById(eventInfoId)
                .orElseThrow(IllegalArgumentException::new)
                .addDib();
    }

    @Override
    public void deleteDib(TokenInfo tokenInfo, Long eventInfoId) {
        Dib dib = dibRepository.findByUserIdAndEventInfo_EventInfoId(
                tokenInfo.id(),
                eventInfoId
        ).orElseThrow(IllegalArgumentException::new);

        dibRepository.delete(dib);
        eventInfoDAO.findById(eventInfoId)
                .orElseThrow(IllegalArgumentException::new)
                .deleteDib();
    }

    @Override
    public List<MyDibListDto> getMyDibList(TokenInfo tokenInfo) {
        return dibRepository.getMyDibList(tokenInfo.id());
    }
}
