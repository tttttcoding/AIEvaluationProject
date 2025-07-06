package com.example.aiteach.DTO;

public class ToolCreateDTO {
    private String name;
    private String url;
    private String logo_url;
    private String description;
    private String category;
    public ToolCreateDTO(String name, String url, String logo_url, String description, String category) {
        this.name = name;
        this.url = url;
        this.logo_url = logo_url;
        this.description = description;
        this.category = category;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
