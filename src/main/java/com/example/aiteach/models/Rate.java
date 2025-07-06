package com.example.aiteach.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "review")
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "ai_id")
    private Long aiId;
    private Double rating;
    private String comment;
    @Column(name="rate_time")
    private LocalDateTime rateTime;

    public Rate(Long id, Long user_id, Long ai_id, Double rating, String comment) {
        this.id = id;
        this.userId = user_id;
        this.aiId = ai_id;
        this.rating = rating;
        this.comment = comment;
    }

    public Rate() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long user_id) {
        this.userId = user_id;
    }

    public Long getAi_id() {
        return aiId;
    }

    public void setAi_id(Long ai_id) {
        this.aiId = ai_id;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getRateTime() {
        return rateTime;
    }

    public void setRateTime(LocalDateTime rateTime) {
        this.rateTime = rateTime;
    }
}
