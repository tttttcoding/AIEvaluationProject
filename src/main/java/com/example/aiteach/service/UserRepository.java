package com.example.aiteach.service;

import com.example.aiteach.models.User;
import com.example.aiteach.projection.UserProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
//    List<UserProjection> findAllBy();
    boolean existsUserByUsername(String username);
    User findByUsername(String username);
    User findUserById(Long id);
    List<User> findAllBy();
    void deleteUserById(Long id);
    List<User> findUsersByClassId(Long classId);
}
