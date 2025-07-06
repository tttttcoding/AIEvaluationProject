package com.example.aiteach.DTO;

import java.util.List;

public class ToolAndRatesDTO extends ToolDTO{
    private List<RatingsDTO> rates;

    public ToolAndRatesDTO(Long id, String name, String url, String logo_url, String description, Long cat_id, String category, Double score, Integer ratingCount, List<RatingsDTO> rates) {
        super(id, name, url, logo_url, description, cat_id, category, score, ratingCount);
        this.rates = rates;
    }

    public List<RatingsDTO> getRates() {
        return rates;
    }

    public void setRates(List<RatingsDTO> rates) {
        this.rates = rates;
    }
}
