package com.T82.event.domain;

import com.T82.event.dto.request.EventUpdateDto;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
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
    private LocalDateTime eventStartTime;

    @Column (name = "bookEndTime")
    private LocalDateTime bookEndTime;

    @Column(name = "isSoldOut")
    private Boolean isSoldOut;

    @Column(name = "isDeleted")
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

    @Transactional
    public void update(EventUpdateDto eventUpdateDto){
        if (eventUpdateDto.getEventStartTime() != null) {
            this.setEventStartTime(eventUpdateDto.getEventStartTime());
        }
        if (eventUpdateDto.getBookEndTime() != null) {
            this.setBookEndTime(eventUpdateDto.getBookEndTime());
        }
    }

    @Transactional
    public void delete(){
        this.setIsDeleted(true);
    }

}