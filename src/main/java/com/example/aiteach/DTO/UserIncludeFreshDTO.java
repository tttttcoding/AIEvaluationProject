package com.example.aiteach.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserIncludeFreshDTO extends UserDTO{
    public Long getIsFresh() {
        return isFresh;
    }

    public void setIsFresh(Long isFresh) {
        this.isFresh = isFresh;
    }
    public UserIncludeFreshDTO resetIsFresh(Long isFresh){
        this.isFresh = isFresh;
        return this;
    }

    @JsonProperty("is_fresh")
    private Long isFresh;
    public UserIncludeFreshDTO(Long id, String username, String role, String message, String nickname) {
        super(id, username, role, message, nickname);
    }

    public UserIncludeFreshDTO(Long id, String username, String role, String message) {
        super(id, username, role, message);
    }

    @Override
    public UserIncludeFreshDTO setCno(String cno){
        super.setCno(cno);
        return this;
    }
}
