package com.example.aiteach.DTO;

public class CourseDTO {
    private Long id;
    private String course;
    private String description;
    private String cno;

    public CourseDTO(Long id, String course, String description, String cno) {
        this.id = id;
        this.course = course;
        this.description = description;
        this.cno = cno;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }
}
