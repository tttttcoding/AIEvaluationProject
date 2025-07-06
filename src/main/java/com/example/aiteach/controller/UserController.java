package com.example.aiteach.controller;

import com.example.aiteach.DTO.*;
import com.example.aiteach.OAuth2.JwtService;
import com.example.aiteach.exception.FileUploadFailedException;
import com.example.aiteach.service.impl.FileService;
import com.example.aiteach.service.impl.UserService;
import com.example.aiteach.projection.UserProjection;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserController{

   @Autowired
   private UserService userService;
   @Autowired
   private FileService fileService;
   @Autowired
   private JwtService jwtService;

//    @GetMapping("/users")
//    public List<UserProjection> getUsers(){
//        return userService.getUsers();
//    }


    @PostMapping("/user/register")
    public ResponseEntity<UserMetaDTO> registerUser(@RequestBody UserCreateDTO userCreateDTO){
        if(userCreateDTO.getRole().equals("student"))
            return new ResponseEntity<>(
                    userService.registerUser(userCreateDTO,jwtService),
                    HttpStatusCode.valueOf(201)
                    );
        else if(userCreateDTO.getRole().equals("teacher"))
            return new ResponseEntity<>(
                    userService.registerTeacher(userCreateDTO,jwtService),
                    HttpStatusCode.valueOf(201)
            );
        throw new IllegalArgumentException("请求参数有误,role字段请填teacher或student");
    }

    @PostMapping("/user/login")
    public ResponseEntity<UserMetaDTO> userLogin(@RequestBody UserCreateDTO userCreateDTO){
        if(userCreateDTO.getRole().equals("student"))
            return new ResponseEntity<>(
                    userService.userLogin(userCreateDTO,jwtService),
                    HttpStatusCode.valueOf(200)
            );
        else if(userCreateDTO.getRole().equals("teacher"))
            return new ResponseEntity<>(
                    userService.teacherLogin(userCreateDTO,jwtService),
                    HttpStatusCode.valueOf(200)
            );
        throw new IllegalArgumentException("请求参数有误,role字段请填teacher或student");
    }

    @PutMapping("/user")
    public ResponseEntity<UserMetaDTO> userPut(@RequestBody UserPutDTO userPutDTO){
        return new ResponseEntity<>(
                userService.putUser(userPutDTO,jwtService),
                HttpStatusCode.valueOf(200)
        );
    }

    @PostMapping("/evaluation/upload")
    public ResponseEntity<MessageDTO> uploadFile(@RequestParam MultipartFile file,
                                                 @RequestParam String Authorization){
        if(file.isEmpty()){
            throw new IllegalArgumentException("文件为空");
        }
        String filename = Paths.get("evaluations").toAbsolutePath()+ "/" +UUID.randomUUID() + file.getOriginalFilename();
        fileService.fileTransferAndSave(file,filename,true);
        return new ResponseEntity<>(
                fileService.fileUpload(Authorization,filename),
                HttpStatusCode.valueOf(200)
        );
    }

    @PostMapping("/evaluation/{toolId}")
    public ResponseEntity<MessageDTO> uploadFile(@RequestBody AuthorizationDTO authorizationDTO,
                                                 @PathVariable Long toolId){
        return new ResponseEntity<>(
                fileService.getEvaluate(authorizationDTO,toolId),
                HttpStatusCode.valueOf(200)
        );
    }

    @PostMapping("evaluation/upload/{fileId}")
    public ResponseEntity<MessageDTO> uploadFile(@RequestParam MultipartFile file,
                                                 @RequestParam String fileSum,
                                                 @RequestParam String Authorization,
                                                 @PathVariable Long fileId){
        return new ResponseEntity<>(
                fileService.fileUpload(Authorization,file,Long.parseLong(fileSum),fileId),
                HttpStatusCode.valueOf(200)
        );
    }

    @GetMapping("/ratings")
    public ResponseEntity<RatingListDTO> getPersonalHistory(@RequestHeader String authorization){
        return new ResponseEntity<>(
                userService.getAllHistory(authorization),
                HttpStatusCode.valueOf(200)
        );
    }

    @DeleteMapping("/ratings/{rateId}")
    public ResponseEntity<MessageDTO> deleteReview(@RequestHeader String authorization,
                                                   @PathVariable Long rateId){
        return new ResponseEntity<>(
                userService.deleteReview(authorization,rateId),
                HttpStatusCode.valueOf(200)
        );
    }

    @GetMapping("/evaluate/history")
    public ResponseEntity<UserGetHistoryDTO> getHistoryByUserId(@RequestHeader String authorization,
                                                                @RequestParam(required = false) String username){
        if(username != null)
            return new ResponseEntity<>(
                    fileService.getHistory(authorization,username),
                    HttpStatusCode.valueOf(200)
            );
        else{
            return new ResponseEntity<>(
                    fileService.getHistory(authorization),
                    HttpStatusCode.valueOf(200)
            );
        }
    }
}

