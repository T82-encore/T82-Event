package com.T82.event.service;

import com.T82.event.domain.EventInfo;
import com.T82.event.dto.request.EventInfoRequest;

public interface EventInfoService {
    public void createEventInfo(EventInfoRequest request);
}
