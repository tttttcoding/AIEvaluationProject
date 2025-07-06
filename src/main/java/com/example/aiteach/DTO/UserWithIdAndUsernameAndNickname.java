package com.example.aiteach.DTO;

public class UserWithIdAndUsernameAndNickname {
    private Long id;
    private String nickname;
    private String username;
    private String cno = "未归属";

    public UserWithIdAndUsernameAndNickname(Long id, String nickname, String username) {
        this.id = id;
        this.nickname = nickname;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCno() {
        return cno;
    }

    public UserWithIdAndUsernameAndNickname setCno(String cno) {
        this.cno = cno;
        return this;
    }
}
