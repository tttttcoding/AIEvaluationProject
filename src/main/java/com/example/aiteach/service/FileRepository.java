package com.example.aiteach.service;

import com.example.aiteach.models.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File,Long> {
    public File findTopByUserIdOrderByIdDesc(Long userId);
    public void deleteFileByUserId(Long userId);
    public List<File> findAllByUserId(Long userId);
}
