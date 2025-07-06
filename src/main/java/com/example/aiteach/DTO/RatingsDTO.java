package com.example.aiteach.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class RatingsDTO {
    private String user;
    private Double rating;
    private String comment;
    @JsonProperty("rate_time")
    private LocalDateTime rateTime;
    private String cno;

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

    public RatingsDTO(String user, Double rating, String comment) {
        this.user = user;
        this.rating = rating;
        this.comment = comment;
    }

    public LocalDateTime getRateTime() {
        return rateTime;
    }

    public RatingsDTO setRateTime(LocalDateTime rateTime) {
        this.rateTime = rateTime;
        return this;
    }

    public String getCno() {
        return cno;
    }

    public RatingsDTO setCno(String cno) {
        this.cno = cno;
        return this;
    }
}
