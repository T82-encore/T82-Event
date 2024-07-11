package com.T82.event.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Entity
@Table(name = "Event_Infos")
@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eventInfoId")
    private Long eventInfoId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "rating")
    @Builder.Default
    private Double rating = 0.0;

    @Column(name = "runningTime")
    private String runningTime;

    @Column(name = "ageRestriction")
    private String ageRestriction;

    @Column(name = "sellCount")
    @Builder.Default
    private Integer sellCount = 0;

    @Column(name = "bookStartTime")
    private LocalDateTime bookStartTime;

    @Column(name = "isDeleted")
    @Builder.Default
    private boolean deleted = false;

    @OneToMany(mappedBy = "eventInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "placeId", nullable = false)
    private EventPlace eventPlace;

    @OneToMany(mappedBy = "eventInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeatGradeInfo> seatGradeInfos;
}
