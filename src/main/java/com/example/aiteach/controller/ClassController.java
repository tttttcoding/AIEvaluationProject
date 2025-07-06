package com.example.aiteach.controller;

import com.example.aiteach.DTO.ClassDTO;
import com.example.aiteach.service.impl.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ClassController {
    @Autowired
    private ClassService classService;

    @GetMapping("/class")
    public ResponseEntity<ClassDTO> getClasses(){
        return new ResponseEntity<>(
                classService.getAllClass(),
                HttpStatusCode.valueOf(200)
        );
    }
}
