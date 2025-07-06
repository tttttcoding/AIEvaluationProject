package com.example.aiteach.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class UserHistoryDTO {
    @JsonProperty("toolid")
    private Long toolId;
    private LocalDateTime dateTime;
    private String content;

    public UserHistoryDTO(Long toolId, LocalDateTime dateTime, String content) {
        this.toolId = toolId;
        this.dateTime = dateTime;
        this.content = content;
    }

    public Long getToolId() {
        return toolId;
    }

    public void setToolId(Long toolId) {
        this.toolId = toolId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
