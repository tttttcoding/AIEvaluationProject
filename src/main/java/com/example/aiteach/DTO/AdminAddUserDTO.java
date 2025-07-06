package com.example.aiteach.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdminAddUserDTO {
    @JsonProperty("Authorization")
    private String Authorization;
    private AdminUserDTO user;

    public String getAuthorization() {
        return Authorization;
    }

    public void setAuthorization(String authorization) {
        Authorization = authorization;
    }

    public AdminUserDTO getUser() {
        return user;
    }

    public void setUser(AdminUserDTO user) {
        this.user = user;
    }

    public AdminAddUserDTO(String authorization, AdminUserDTO user) {
        Authorization = authorization;
        this.user = user;
    }
}
