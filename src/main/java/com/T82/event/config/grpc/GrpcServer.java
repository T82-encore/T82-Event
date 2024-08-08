package com.T82.event.config.grpc;

import com.T82.event.domain.repository.EventRepository;
import com.T82.event.dto.response.EventDetail;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.t82.event.lib.EventGrpc;
import org.t82.event.lib.GetEventReply;
import org.t82.event.lib.GetEventRequest;

@GrpcService
@RequiredArgsConstructor
public class GrpcServer extends EventGrpc.EventImplBase {
    private final EventRepository eventRepository;

    @Override
    public void getEventDetail(GetEventRequest request, StreamObserver<GetEventReply> responseObserver) {
        EventDetail eventDetail = eventRepository.findEventDetailByEventId(request.getEventId());
        responseObserver.onNext(
                GetEventReply.newBuilder()
                        .setEventInfoId(eventDetail.getEventInfoId())
                        .setTitle(eventDetail.getTitle())
                        .setEventStartTime(eventDetail.getEventStartTime().toString())
                .build()
        );
        responseObserver.onCompleted();
    }
}
