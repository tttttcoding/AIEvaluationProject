package com.example.aiteach.DTO;

import java.util.List;

public class CourseListDTO {
    private List<CourseDTO> courses;

    public CourseListDTO(List<CourseDTO> courses) {
        this.courses = courses;
    }

    public List<CourseDTO> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseDTO> courses) {
        this.courses = courses;
    }
}
