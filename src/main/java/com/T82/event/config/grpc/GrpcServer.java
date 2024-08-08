package com.T82.event.config.grpc;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.t82.event.lib.EventGrpc;
import org.t82.event.lib.GetEventReply;
import org.t82.event.lib.GetEventRequest;

@GrpcService
@RequiredArgsConstructor
public class GrpcServer extends EventGrpc.EventImplBase {
    private final GrpcUtil grpcUtil;

    @Override
    public void getEventDetail(GetEventRequest request, StreamObserver<GetEventReply> responseObserver) {
        responseObserver.onNext(grpcUtil.getEventDetail(request.getEventId()));
        responseObserver.onCompleted();
    }
}
