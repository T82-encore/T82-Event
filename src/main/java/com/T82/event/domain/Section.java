package com.T82.event.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Sections")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sectionId")
    private Long sectionId;

    @Column(name = "startRow")
    private Integer startRow;

    @Column(name = "startCol")
    private Integer startCol;

    @Column(name = "seatRowCount")
    private Long seatRowCount;

    @Column(name = "seatColCount")
    private Long seatColumnsCount;

    @Column(name = "sectionName")
    private String sectionName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "placeId", nullable = false)
    private EventPlace eventPlace;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeatGradeInfo> seatGradeInfos;
}
