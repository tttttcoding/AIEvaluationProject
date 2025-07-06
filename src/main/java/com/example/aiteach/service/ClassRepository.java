package com.example.aiteach.service;

import com.example.aiteach.models.Cno;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassRepository extends JpaRepository<Cno,Long> {
    List<Cno> getAllBy();
    Cno getCnoById(Long id);

    boolean existsById(@NotNull Long id);
}
