package com.example.aiteach.service.impl;

import com.example.aiteach.DTO.ClassDTO;
import com.example.aiteach.models.Cno;
import com.example.aiteach.service.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassService {
    @Autowired
    private ClassRepository classRepository;

    public ClassDTO getAllClass(){
        List<Cno> cnoList = classRepository.getAllBy();
        return new ClassDTO(cnoList);
    }
}
