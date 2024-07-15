package com.T82.event.domain.repository;

import com.T82.event.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c.categoryId FROM Category c WHERE c.parentId = :parentId")
    List<Long> findSubCategoryIdsByParentId(Long parentId);
}
