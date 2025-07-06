package com.example.aiteach.DTO;

import com.example.aiteach.models.Cno;

import java.util.ArrayList;
import java.util.List;

public class ClassDTO {
    private List<Cno> classes;

    public ClassDTO(List<Cno> classes) {
        this.classes = classes;
    }

    public List<Cno> getClasses() {
        return classes;
    }

    public void setClasses(List<Cno> classes) {
        this.classes = classes;
    }
}
