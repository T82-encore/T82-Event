package com.T82.event.dto.request;

import com.T82.event.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InitCategoryRequest {
    private String categoryName;
    private Long parentId;

    public Category toEntity() {
        return Category.builder()
                .categoryName(categoryName)
                .parentId(parentId)
                .build();
    }
}
