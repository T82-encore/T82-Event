package com.T82.event.domain;


import jakarta.persistence.*;
import lombok.*;


import java.util.List;

@Entity
@Table(name = "Categories")
@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryId")
    private Long categoryId;

    @Column(name = "categoryName")
    private String categoryName;

    @Column(name = "parentId")
    private String parentId;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventInfo> eventInfos;
}
