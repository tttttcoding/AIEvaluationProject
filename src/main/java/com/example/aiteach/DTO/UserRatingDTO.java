package com.example.aiteach.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRatingDTO {
    @JsonProperty(namespace = "Authorization")
    private String Authorization;
    private RateDTO rate;

    public String getAuthorization() {
        return Authorization;
    }

    public void setAuthorization(String authorization) {
        Authorization = authorization;
    }

    public RateDTO getRate() {
        return rate;
    }

    public void setRate(RateDTO rate) {
        this.rate = rate;
    }

    public UserRatingDTO(String authorization, RateDTO rate) {
        Authorization = authorization;
        this.rate = rate;
    }
}
