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

    @Column (name = "eventStartTime")
    private LocalDateTime eventStartTime;

    @Column (name = "bookEndTime")
    private LocalDateTime bookEndTime;

    @Column (name = "isSoldOut")
    private Boolean isSoldOut;

    @Column (name = "eventSellCount")
    private Long eventSellCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eventInfoId", nullable = false)
    private EventInfo eventInfo;
}