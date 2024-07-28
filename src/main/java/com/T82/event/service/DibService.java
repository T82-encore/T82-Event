package com.T82.event.service;

import com.T82.event.config.jwt.TokenInfo;
import com.T82.event.dto.response.MyDibListDto;

import java.util.List;

public interface DibService {
    void addDib(TokenInfo tokenInfo, Long eventInfoId);
    void deleteDib(TokenInfo tokenInfo, Long eventInfoId);
    List<MyDibListDto> getMyDibList(TokenInfo tokenInfo);
}
