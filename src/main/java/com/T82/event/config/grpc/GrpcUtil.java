package com.T82.event.config.grpc;

import com.T82.event.domain.repository.EventRepository;
import com.T82.event.dto.response.EventDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.t82.event.lib.GetEventReply;

@Component
@RequiredArgsConstructor
public class GrpcUtil {
    private final EventRepository eventRepository;

    public GetEventReply getEventDetail(Long eventId) {
        EventDetail eventDetail = eventRepository.findEventDetailByEventId(eventId);
        return GetEventReply.newBuilder()
                .setEventInfoId(eventDetail.getEventInfoId())
                .setTitle(eventDetail.getTitle())
                .setEventStartTime(eventDetail.getEventStartTime().toString())
                .build();
    }
}
