package com.example.aiteach.service;

import com.example.aiteach.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findByCategory(String category);
    Category findCategoryById(Long id);

    boolean existsCategoryByCategory(String category);
}
