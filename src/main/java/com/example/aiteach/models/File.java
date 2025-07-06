package com.example.aiteach.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "evaluation_file")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    private String path;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    private String filename;

    public File(){}

    public File(Long id, Long userId, String path, LocalDateTime createDate,String filename) {
        this.id = id;
        this.userId = userId;
        this.path = path;
        this.createDate = createDate;
        this.filename = filename;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
}
