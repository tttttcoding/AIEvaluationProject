package com.example.aiteach.controller;

import com.example.aiteach.DTO.*;
import com.example.aiteach.OAuth2.JwtService;
import com.example.aiteach.exception.PermissionDeniedException;
import com.example.aiteach.exception.TokenUnauthorized;
import com.example.aiteach.service.impl.AdminService;
import com.example.aiteach.service.impl.UserService;
import com.example.aiteach.projection.UserProjection;
import com.example.aiteach.tempjwt.AESUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AdminController{

    @Autowired
    private AdminService adminService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/admin/register")
    public ResponseEntity<UserMetaDTO> registerAdmin(@RequestBody UserCreateDTO userCreateDTO){
        return new ResponseEntity<>(
                adminService.registerAdmin(userCreateDTO,jwtService),
                HttpStatusCode.valueOf(200)
                );
    }

    @PostMapping("/auth/login")
    public ResponseEntity<UserMetaDTO> userLogin(@RequestBody UserCreateDTO userCreateDTO){
        return new ResponseEntity<>(
                adminService.login(userCreateDTO,jwtService),
                HttpStatusCode.valueOf(200)
        );
    }

    @GetMapping("/auth")
    public ResponseEntity<Object> getUsers(@RequestHeader("Authorization") String authorization,
                                                             @RequestParam(required = false) String username,
                                                             @RequestParam(required = false) String role){
        if(username == null && role == null){
            return new ResponseEntity<>(
                    adminService.getUsers(new AuthorizationDTO(authorization)),
                    HttpStatusCode.valueOf(200)
            );
        }
        else{
            return new ResponseEntity<>(
                    adminService.getUser(new AuthorizationDTO(authorization),role,username,jwtService),
                    HttpStatusCode.valueOf(200)
            );
        }
    }

    @PostMapping("/auth")
    public ResponseEntity<MessageDTO> addUser(@RequestBody AdminAddUserDTO adminAddUserDTO, @RequestParam String role){
        return new ResponseEntity<>(
                adminService.addUser(adminAddUserDTO,role,jwtService),
                HttpStatusCode.valueOf(201)
        );
    }

    @PutMapping("/auth")
    public ResponseEntity<MessageDTO> putUser(@RequestBody AdminPutUserDTO adminPutUserDTO, @RequestParam String role,@RequestParam Long id){
        return new ResponseEntity<>(
                adminService.putUser(adminPutUserDTO,role,id,jwtService),
                HttpStatusCode.valueOf(200)
        );
    }

    @DeleteMapping("/auth")
    public ResponseEntity<MessageDTO> deleteUser(@RequestHeader("Authorization") String authorization, @RequestParam String role,@RequestParam Long id){
        return new ResponseEntity<>(
                adminService.deleteUser(authorization,role,id,jwtService),
                HttpStatusCode.valueOf(200)
        );
    }
}

