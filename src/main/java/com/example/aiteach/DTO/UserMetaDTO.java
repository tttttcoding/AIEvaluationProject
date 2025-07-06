package com.example.aiteach.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserMetaDTO {
    @JsonProperty("Authorization")
    private String Authorization;
    @JsonProperty("user")
    private UserDTO user;

    public UserMetaDTO(String Authorization, UserDTO user) {
        this.Authorization = Authorization;
        this.user = user;
    }

    @JsonProperty("Authorization")
    public String getAuthorization() {
        return Authorization;
    }

    @JsonProperty("Authorization")
    public void setAuthorization(String Authorization) {
        this.Authorization = Authorization;
    }

    @JsonProperty("user")
    public UserDTO getUserDTO() {
        return user;
    }

    @JsonProperty("user")
    public void setUserDTO(UserDTO user) {
        this.user = user;
    }

}
