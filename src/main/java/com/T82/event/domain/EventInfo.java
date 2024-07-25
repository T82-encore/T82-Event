package com.T82.event.domain;

import com.T82.event.dto.response.ReviewDto;
import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Entity
@Table(name = "Event_Infos")
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
    @Setter
    private String title;

    @Column(name = "description")
    @Setter
    private String description;

    @Column(name = "rating")
    @Builder.Default
    private Double rating = 0.0;

    @Column(name = "reviewCount")
    @Builder.Default
    private Integer reviewCount = 0;

    @Column(name = "runningTime")
    @Setter
    private String runningTime;

    @Column(name = "ageRestriction")
    @Setter
    private String ageRestriction;

    @Column(name = "sellCount")
    @Builder.Default
    @Setter
    private Integer sellCount = 0;

    @Column(name = "bookStartTime")
    @Setter
    private LocalDateTime bookStartTime;

    @Column(name = "isDeleted")
    @Builder.Default
    @Setter
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

    public void addReview(Double rating) {
        this.reviewCount += 1;
        this.rating += rating;
    }

    public void deleteReview(Double rating) {
        this.reviewCount -= 1;
        this.rating -= rating;
    }
}
