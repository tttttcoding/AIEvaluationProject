package com.example.aiteach.DTO;

import java.util.List;

public class CourseWithRatesDTO {
    private String message;
    private List<RateForCourseDTO> rates;

    public CourseWithRatesDTO(String message, List<RateForCourseDTO> rates) {
        this.message = message;
        this.rates = rates;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<RateForCourseDTO> getRates() {
        return rates;
    }

    public void setRates(List<RateForCourseDTO> rates) {
        this.rates = rates;
    }
}
