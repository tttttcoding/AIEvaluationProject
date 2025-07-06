package com.example.aiteach.DTO;

import java.util.List;

public class RatingListDTO {
    private List<RatingsWithToolId> rates;

    public RatingListDTO(List<RatingsWithToolId> rates) {
        this.rates = rates;
    }

    public List<RatingsWithToolId> getRates() {
        return rates;
    }

    public void setRates(List<RatingsWithToolId> rates) {
        this.rates = rates;
    }
}
