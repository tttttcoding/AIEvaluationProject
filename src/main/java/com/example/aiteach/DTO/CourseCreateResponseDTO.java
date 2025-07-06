package com.example.aiteach.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CourseCreateResponseDTO {
    private String message;
    @JsonProperty("course_id")
    private Long courseId;
    private String cno;

    public CourseCreateResponseDTO(String message, Long courseId, String cno) {
        this.message = message;
        this.courseId = courseId;
        this.cno = cno;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }
}
