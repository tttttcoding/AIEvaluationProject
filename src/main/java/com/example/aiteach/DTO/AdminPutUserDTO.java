package com.example.aiteach.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdminPutUserDTO{
    @JsonProperty("Authorization")
    private String authorization;
    private AdminUserDTO user;

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public AdminUserDTO getUser() {
        return user;
    }

    public void setUser(AdminUserDTO user) {
        this.user = user;
    }
}
