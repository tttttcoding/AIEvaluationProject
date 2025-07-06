package com.example.aiteach.service;

import com.example.aiteach.models.Rate;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RateRepository extends JpaRepository<Rate,Long> {
    public List<Rate> findByAiId(Long aiId);
    public List<Rate> findAllByUserId(Long userId);
    void deleteRateById(Long id);
    boolean existsById(@NotNull Long id);
    @Query("select u.userId from Rate u where u.id = ?1")
    Long findUserIdById(Long id);
    @Query("select count(r.id) from Rate r where r.userId = ?1")
    Long findSumOfUserReview(Long userId);
}
