package com.example.aiteach.models;

import jakarta.persistence.*;

@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Column(name = "class_id")
    private Long classId;
    @Column(name = "teacher_id")
    private Long teacherId;
    private Long tem1;
    private Long tem2;
    private Long tem3;
    public Course(){}
    public Course(Long id, String name, String description, Long classId, Long teacherId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.classId = classId;
        this.teacherId = teacherId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getTem1() {
        return tem1;
    }

    public void setTem1(Long tem1) {
        this.tem1 = tem1;
    }

    public Long getTem2() {
        return tem2;
    }

    public void setTem2(Long tem2) {
        this.tem2 = tem2;
    }

    public Long getTem3() {
        return tem3;
    }

    public void setTem3(Long tem3) {
        this.tem3 = tem3;
    }
}
