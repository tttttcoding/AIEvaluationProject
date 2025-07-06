package com.example.aiteach.DTO;

public class ToolDTO {
    private Long id;
    private String name;
    private String url;
    private String logo_url;
    private String description;
    private Long cat_id;
    private String category;
    private Double score;
    private Integer ratingCount;

    public ToolDTO(Long id, String name, String url, String logo_url, String description, Long cat_id,String category , Double score, Integer ratingCount) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.logo_url = logo_url;
        this.description = description;
        this.cat_id = cat_id;
        this.category = category;
        this.score = score;
        this.ratingCount = ratingCount;
    }

    public ToolDTO() {

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

    public Long getCat_id() {
        return cat_id;
    }

    public void setCat_id(Long cat_id) {
        this.cat_id = cat_id;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setRatingCount(Integer ratingCount) {
        this.ratingCount = ratingCount;
    }
}
