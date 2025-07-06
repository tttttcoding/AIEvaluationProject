package com.example.aiteach.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class RateForCourseDTO {
    private String username;
    private String user;
    private Double rating;
    private String comment;
    private String cno;
    @JsonProperty("rate_time")
    private LocalDateTime rateTime;
    @JsonProperty("tool_id")
    private Long toolId;

    public RateForCourseDTO(String username, String user, Double rating, String comment, String cno, LocalDateTime rateTime, Long toolId) {
        this.username = username;
        this.user = user;
        this.rating = rating;
        this.comment = comment;
        this.cno = cno;
        this.rateTime = rateTime;
        this.toolId = toolId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
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
}
