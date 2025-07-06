package com.example.aiteach.DTO;

import java.util.List;

public class StudentAndTeacherListDTO {
    private List<UserWithIdAndUsernameAndNickname> student;
    private List<UserWithIdAndUsernameAndNickname> teacher;

    public StudentAndTeacherListDTO(){

    }

    public StudentAndTeacherListDTO(List<UserWithIdAndUsernameAndNickname> student, List<UserWithIdAndUsernameAndNickname> teacher) {
        this.student = student;
        this.teacher = teacher;
    }

    public List<UserWithIdAndUsernameAndNickname> getStudent() {
        return student;
    }

    public void setStudent(List<UserWithIdAndUsernameAndNickname> student) {
        this.student = student;
    }

    public List<UserWithIdAndUsernameAndNickname> getTeacher() {
        return teacher;
    }

    public void setTeacher(List<UserWithIdAndUsernameAndNickname> teacher) {
        this.teacher = teacher;
    }
}
