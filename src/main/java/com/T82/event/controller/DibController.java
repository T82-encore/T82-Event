package com.T82.event.controller;

import com.T82.event.config.jwt.TokenInfo;
import com.T82.event.dto.response.MyDibListDto;
import com.T82.event.service.DibService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contents")
@RequiredArgsConstructor
public class DibController {
    private final DibService dibService;

    @PostMapping("/{eventInfoId}/dibs")
    public void addDibs(
            @PathVariable("eventInfoId") Long eventInfoId,
            @AuthenticationPrincipal TokenInfo tokenInfo
    ) {
        dibService.addDib(tokenInfo, eventInfoId);
    }

    @DeleteMapping("/{eventInfoId}/dibs")
    public void deleteDibs(
            @PathVariable("eventInfoId") Long eventInfoId,
            @AuthenticationPrincipal TokenInfo tokenInfo
    ) {
        dibService.deleteDib(tokenInfo, eventInfoId);
    }

    @GetMapping("/dibs")
    public List<MyDibListDto> getDibs(@AuthenticationPrincipal TokenInfo tokenInfo) {
        return dibService.getMyDibList(tokenInfo);
    }
}
