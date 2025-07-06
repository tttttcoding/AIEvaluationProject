package com.example.aiteach.controller;

import com.example.aiteach.DTO.*;
import com.example.aiteach.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TeacherController {
    @Autowired
    private UserService userService;

    @GetMapping("/courses")
    public ResponseEntity<CourseListDTO> getCourses(@RequestHeader String authorization){
        return new ResponseEntity<>(
                userService.getCourses(authorization),
                HttpStatusCode.valueOf(200)
        );
    }

    @PostMapping("/course")
    public ResponseEntity<CourseCreateResponseDTO> courseRegister(@RequestBody CourseCreateDTO courseCreateDTO){
        return new ResponseEntity<>(
                userService.courseRegister(courseCreateDTO),
                HttpStatusCode.valueOf(200)
        );
    }

    @PutMapping("/course/{courseId}")
    public ResponseEntity<MessageDTO> putCourse(@PathVariable Long courseId,
                                                @RequestBody CourseCreateDTO courseCreateDTO){
        return new ResponseEntity<>(
                userService.coursePut(courseCreateDTO,courseId),
                HttpStatusCode.valueOf(200)
        );
    }

    @GetMapping("/class/ratings/{courseId}")
    public ResponseEntity<CourseWithRatesDTO> getRateofCourse(@RequestHeader String authorization,@PathVariable Long courseId){
        return new ResponseEntity<>(
                userService.getRateOfCourse(authorization,courseId),
                HttpStatusCode.valueOf(200)
        );
    }

    @DeleteMapping("/course/{courseId}")
    public ResponseEntity<MessageDTO> deleteCourse(@RequestBody AuthorizationDTO authorizationDTO,
                                                   @PathVariable Long courseId){
        return new ResponseEntity<>(
                userService.deleteCourse(authorizationDTO,courseId),
                HttpStatusCode.valueOf(200)
        );
    }

    @GetMapping("/student/history/{classId}")
    public ResponseEntity<ClassWithUserWithSumDTO> getSum(@RequestHeader String authorization,
                                                          @PathVariable Long classId){
        return new ResponseEntity<>(
                userService.getSumOfClass(authorization,classId),
                HttpStatusCode.valueOf(200)
        );
    }

    @GetMapping("/rating/history")
    public ResponseEntity<RatingListDTO> getRates(@RequestHeader String authorization,
                                                  @RequestParam String username){
        return new ResponseEntity<>(
                userService.getParticularStudent(authorization,username),
                HttpStatusCode.valueOf(200)
        );
    }
}
