package com.example.aiteach.service;

import com.example.aiteach.models.Admin;
import com.example.aiteach.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {
    boolean existsAdminByUsername(String username);
    Admin findByUsername(String username);
}
