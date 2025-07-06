package com.example.aiteach.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "evaluation_file_result")
public class ResultFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String path;
    @Column(name = "tool_id")
    private Long toolId;
    @Column(name = "create_time")
    private LocalDateTime createTime;
    @Column(name = "user_id")
    private Long userId;

    public ResultFile(Long id, String path, Long toolId, LocalDateTime createTime, Long userId) {
        this.id = id;
        this.path = path;
        this.toolId = toolId;
        this.createTime = createTime;
        this.userId = userId;
    }

    public ResultFile() {

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getToolId() {
        return toolId;
    }

    public void setToolId(Long toolId) {
        this.toolId = toolId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
