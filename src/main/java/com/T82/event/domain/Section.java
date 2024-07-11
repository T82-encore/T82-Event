package com.T82.event.domain;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "Sections")
@Data

public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sectionId")
    private Long sectionId;

    @Column(name = "seatRowCount")
    private Long seatRowCount;

    @Column(name = "seatColumnsCount")
    private Long seatColumnsCount;

    @Column(name = "sectionName")
    private String sectionName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "placeId", nullable = false)
    private EventPlace eventPlace;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeatGradeInfo> seatGradeInfos;
}
