package com.example.aiteach.models;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

@Entity
@Table(name = "ai_infor")
public class Tool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String url;
    private String logo_url;
    private String description;
    @Column(name = "category_id")
    private Long categoryId;
    private Double score;
    private Integer ratingCount;

    public Tool(Long id, String name, String url, String logo_url, String description, Long categoryId, Double score, Integer ratingCount) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.logo_url = logo_url;
        this.description = description;
        this.categoryId = categoryId;
        this.score = score;
        this.ratingCount = ratingCount;
    }

    public Tool(String name, String url, String logo_url, String description, Long categoryId) {
        this.name = name;
        this.url = url;
        this.logo_url = logo_url;
        this.description = description;
        this.categoryId = categoryId;
        this.score = 0.0;
        this.ratingCount = 0;
    }

    public Tool() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(Integer ratingCount) {
        this.ratingCount = ratingCount;
    }
}
