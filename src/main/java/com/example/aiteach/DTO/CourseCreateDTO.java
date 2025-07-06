package com.example.aiteach.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CourseCreateDTO {
    @JsonProperty("Authorization")
    private String authorization;
    @JsonProperty("name")
    private String course;
    private String description;
    private Long cno;

    public CourseCreateDTO(String authorization, String course, String description, Long cno) {
        this.authorization = authorization;
        this.course = course;
        this.description = description;
        this.cno = cno;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCno() {
        return cno;
    }

    public void setCno(Long cno) {
        this.cno = cno;
    }
}
