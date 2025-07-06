package com.example.aiteach.DTO;

public class UserWithSumDTO {
    private String username;
    private String nickname;
    private Long report;
    private Long review;

    public UserWithSumDTO(String username, String nickname, Long report, Long review) {
        this.username = username;
        this.nickname = nickname;
        this.report = report;
        this.review = review;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getReport() {
        return report;
    }

    public void setReport(Long report) {
        this.report = report;
    }

    public Long getReview() {
        return review;
    }

    public void setReview(Long review) {
        this.review = review;
    }
}
