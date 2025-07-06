package com.example.aiteach.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class RatingsWithToolId {
    private Long id;
    private String user;
    private Double rating;
    private String comment;
    @JsonProperty("rate_time")
    private LocalDateTime rateTime;
    @JsonProperty("tool_id")
    private Long toolId;

    public RatingsWithToolId(String user, Double rating, String comment, LocalDateTime rateTime, Long toolId) {
        this.user = user;
        this.rating = rating;
        this.comment = comment;
        this.rateTime = rateTime;
        this.toolId = toolId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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

    public LocalDateTime getRateTime() {
        return rateTime;
    }

    public void setRateTime(LocalDateTime rateTime) {
        this.rateTime = rateTime;
    }

    public Long getToolId() {
        return toolId;
    }

    public void setToolId(Long toolId) {
        this.toolId = toolId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
