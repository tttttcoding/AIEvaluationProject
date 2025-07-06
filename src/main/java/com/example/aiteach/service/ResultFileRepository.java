package com.example.aiteach.service;

import com.example.aiteach.models.ResultFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ResultFileRepository extends JpaRepository<ResultFile,Long> {
    List<ResultFile> findAllByUserIdOrderByIdDesc(Long userId);
    @Query("select distinct r.path from ResultFile r where r.userId = ?1 and r.createTime >= ?2")
    List<String> findPathByUserIdOrderByIdDesc(Long userId, LocalDateTime time);
    @Query("select count(r.id) from ResultFile r where r.userId = ?1")
    Long findSumOfUserReport(Long userId);
}
