package com.example.aiteach.service;

import com.example.aiteach.models.Teacher;
import com.example.aiteach.models.User;
import com.example.aiteach.projection.UserProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher,Long> {
    boolean existsTeacherByUsername(String username);
    Teacher findByUsername(String username);
    Teacher findTeacherById(Long id);
    List<Teacher> findAllBy();
    void deleteTeacherById(Long id);
}
