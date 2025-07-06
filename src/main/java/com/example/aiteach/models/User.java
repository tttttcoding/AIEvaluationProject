package com.example.aiteach.models;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    private String nickname = "";
    @Column(name = "is_fresh")
    private Long isFresh =1L;

    @Column(name = "class_id")
    private Long classId;

    public User(){}
    public User(Long id,String username,String nickname){
        super();
        this.id = id;
        this.username = username;
        this.nickname = nickname;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Long getIsFresh() {
        return isFresh;
    }

    public void setIsFresh(Long isFresh) {
        this.isFresh = isFresh;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }
}

