package com.example.aiteach.models;

import jakarta.persistence.*;

@Entity
@Table(name = "admin")
public class Admin{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    public Admin(Long id,String username){
        this.id = id;
        this.username = username;
    }

    public Admin() {

    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
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
}

