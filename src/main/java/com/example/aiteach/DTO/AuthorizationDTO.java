package com.example.aiteach.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthorizationDTO {
    @JsonProperty(namespace = "Authorization")
    private String Authorization;

    public String getAuthorization() {
        return this.Authorization;
    }

    public void setAuthorization(String Authorization) {
        this.Authorization = Authorization;
    }

    public AuthorizationDTO(String authorization) {
        this.Authorization = authorization;
    }
}
