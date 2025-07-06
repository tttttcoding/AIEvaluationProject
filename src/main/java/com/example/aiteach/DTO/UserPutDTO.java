package com.example.aiteach.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserPutDTO {
    @JsonProperty("Authorization")
    private String Authorization;
    private String old_password;
    private String password;
    private String nickname;
    private String username;
    private Long cno;

    public UserPutDTO(String authorization, String old_password, String password, String nickname, String username) {
        Authorization = authorization;
        this.old_password = old_password;
        this.password = password;
        this.nickname = nickname;
        this.username = username;
    }

    public String getAuthorization() {
        return Authorization;
    }

    public void setAuthorization(String authorization) {
        Authorization = authorization;
    }

    public String getOld_password() {
        return old_password;
    }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Long getCno() {
        return cno;
    }

    public void setCno(Long cno) {
        this.cno = cno;
    }
}
