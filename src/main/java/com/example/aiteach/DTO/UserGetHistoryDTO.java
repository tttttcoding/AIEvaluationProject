package com.example.aiteach.DTO;

import java.util.List;

public class UserGetHistoryDTO {
    private String message;
    private List<UserHistoryDTO> history;

    public UserGetHistoryDTO(String message, List<UserHistoryDTO> history) {
        this.message = message;
        this.history = history;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<UserHistoryDTO> getHistory() {
        return history;
    }

    public void setHistory(List<UserHistoryDTO> history) {
        this.history = history;
    }
}
