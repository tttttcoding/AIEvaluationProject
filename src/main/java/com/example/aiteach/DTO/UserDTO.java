package com.example.aiteach.DTO;

public class UserDTO {
    private Long id;
    private String username;
    private String role;
    private String nickname ="";
    private String message;
    private String cno;

    public UserDTO(Long id, String username, String role, String message,String nickname) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.message = message;
        this.nickname = nickname;
    }
    public UserDTO(Long id, String username, String role, String message) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public void setRole(String role) {
        this.role = role;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }


    public String getRole() {
        return role;
    }

    public String getMessage() {
        return message;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCno() {
        return cno;
    }

    public UserDTO setCno(String cno) {
        this.cno = cno;
        return this;
    }
}
