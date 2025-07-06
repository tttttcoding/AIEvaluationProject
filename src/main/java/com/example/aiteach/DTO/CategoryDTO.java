package com.example.aiteach.DTO;

import java.util.List;

public class CategoryDTO {
    List<String> categories;

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public CategoryDTO(List<String> categories) {
        this.categories = categories;
    }
}
