package com.example.aiteach.service;

import com.example.aiteach.models.Tool;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToolRepository extends JpaRepository<Tool,Long> {
    List<Tool> findToolsByCategoryId(Long category_id, Sort sort);
    Tool findToolById(Long id);
    boolean existsToolByName(String name);
    boolean existsToolById(Long id);
}
