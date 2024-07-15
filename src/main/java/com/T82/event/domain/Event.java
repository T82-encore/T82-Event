package com.T82.event.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Events")
@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eventId")
    private Long eventId;

    @Column (name = "eventStartTime")
    @Setter
    private LocalDateTime eventStartTime;

    @Column (name = "bookEndTime")
    @Setter
    private LocalDateTime bookEndTime;

    @Column(name = "isSoldOut")
    private Boolean isSoldOut;

    @Column(name = "isDeleted")
    @Setter
    private Boolean isDeleted;

    @Column(name = "eventSellCount")
    private Long eventSellCount;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eventInfoId")
    private EventInfo eventInfo;

    @PrePersist
    protected void onCreate() {
        if (isSoldOut == null) {
            isSoldOut = false;
        }
        if (isDeleted == null) {
            isDeleted = false;
        }
        if (eventSellCount == null) {
            eventSellCount = 0L;
        }
    }
}