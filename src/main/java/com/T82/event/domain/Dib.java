package com.T82.event.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "Dibs")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dib {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dibId")
    private Long id;

    @Column(name = "userId")
    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eventInfoId")
    private EventInfo eventInfo;

    public static Dib toEntity(UUID userId, Long eventInfoId) {
        return Dib.builder()
                .userId(userId)
                .eventInfo(
                        EventInfo.builder()
                                .eventInfoId(eventInfoId)
                                .build()
                ).build();
    }
}
