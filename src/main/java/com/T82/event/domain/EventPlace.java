package com.T82.event.domain;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "Event_Places")
@Data
public class EventPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "placeId")
    private Long placeId;

    @Column(name = "placeName")
    private String placeName;

    @Column(name = "address")
    private String address;

    @Column(name = "totalSeat")
    private Integer totalSeat;

    @OneToMany(mappedBy = "eventPlace", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventInfo> eventInfos;

    @OneToMany(mappedBy = "eventPlace", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Section> sections;

}