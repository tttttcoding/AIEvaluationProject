package com.example.aiteach.DTO;

public class RateDTO {
    Double rating;
    String comment;

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

    public RateDTO(Double rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }
}
