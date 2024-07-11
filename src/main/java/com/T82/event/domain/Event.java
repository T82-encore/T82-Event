package com.T82.event.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "Events")
@Data
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eventId")
    private Long eventId;

    @Column (name = "startTime")
    private LocalDateTime startTime;

    @Column (name = "endTime")
    private LocalDateTime endTime;

    @Column (name = "isSoldOut")
    private Boolean isSoldOut;

    @Column (name = "eventSellCount")
    private Long eventSellCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eventInfoId", nullable = false)
    private EventInfo eventInfo;
}