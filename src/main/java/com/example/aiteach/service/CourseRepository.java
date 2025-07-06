package com.example.aiteach.service;

import com.example.aiteach.models.Course;
import com.example.aiteach.models.Teacher;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course,Long> {
    Course findCourseById(Long id);
    List<Course> findCoursesByTeacherId(Long id);
    Course findCourseByClassId(Long id);
    boolean existsById(@NotNull Long id);
    boolean deleteCourseById(Long id);
}
