package com.example.aiteach.DTO;

import java.util.List;

public class ClassWithUserWithSumDTO {
    private String message;
    private String classname;
    private List<UserWithSumDTO> users;

    public ClassWithUserWithSumDTO(String message, String classname, List<UserWithSumDTO> users) {
        this.message = message;
        this.classname = classname;
        this.users = users;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public List<UserWithSumDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserWithSumDTO> users) {
        this.users = users;
    }
}
