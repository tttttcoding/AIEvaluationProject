package com.example.aiteach.controller;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class Controller {
    @GetMapping("/")
    public String redirect(){
        return "redirect:/index.html";
    }

}
