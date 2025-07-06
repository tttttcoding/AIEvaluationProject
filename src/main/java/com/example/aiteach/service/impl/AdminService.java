package com.example.aiteach.service.impl;

import com.example.aiteach.DTO.*;
import com.example.aiteach.OAuth2.JwtService;
import com.example.aiteach.exception.*;
import com.example.aiteach.models.Admin;
import com.example.aiteach.models.Cno;
import com.example.aiteach.models.Teacher;
import com.example.aiteach.models.User;
import com.example.aiteach.service.AdminRepository;
import com.example.aiteach.service.ClassRepository;
import com.example.aiteach.service.TeacherRepository;
import com.example.aiteach.service.UserRepository;
import com.example.aiteach.tempjwt.AESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ClassRepository classRepository;
    public UserMetaDTO registerAdmin(UserCreateDTO userCreateDTO, JwtService jwtService) {
        if (adminRepository.existsAdminByUsername(userCreateDTO.getUsername())) {
            throw new UserAlreadyExistsException("admin already exists");
        }
        String token;
        try {
            token = AESUtil.encrypt(userCreateDTO.getUsername()+" "+"admin", AESUtil.key);
        } catch (Exception e) {
            throw new RuntimeException();
        }
        Admin admin = new Admin();
        admin.setUsername(userCreateDTO.getUsername());
        admin.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        adminRepository.save(admin);
        UserMetaDTO metaDTO = new UserMetaDTO(token, new UserDTO(admin.getId(),
                admin.getUsername(),
                "admin",
                "管理员注册成功"));
        return metaDTO;
    }

    public UserMetaDTO login(UserCreateDTO userCreateDTO, JwtService jwtService){
        Admin admin = adminRepository.findByUsername(userCreateDTO.getUsername());
        if (admin == null){
            throw new UserNotFoundException("admin not found");
        }
        if(!passwordEncoder.matches(userCreateDTO.getPassword(),admin.getPassword())){
            throw new PasswordWrongException("管理用户名或密码错误");
        }
        String token;
        try {
            token = AESUtil.encrypt(userCreateDTO.getUsername()+" "+"admin", AESUtil.key);
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return new UserMetaDTO(
                token,
                new UserDTO(admin.getId(),
                        admin.getUsername(),
                        "admin",
                        "管理员登录成功")
        );
    }

    public StudentAndTeacherListDTO getUsers(AuthorizationDTO authorizationDTO){
        String authorizaion;
        try{
            authorizaion = AESUtil.decrypt(authorizationDTO.getAuthorization(),AESUtil.key);
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new TokenUnauthorized("令牌有误");
        }
        if(!AESUtil.getRole(authorizaion).equals("admin")){
            throw new PermissionDeniedException("权限不足");
        }
        List<User> userList = userRepository.findAllBy();
        List<Teacher> teacherList = teacherRepository.findAllBy();
        StudentAndTeacherListDTO studentAndTeacherListDTO = new StudentAndTeacherListDTO();
        List<UserWithIdAndUsernameAndNickname> students = new ArrayList<>();
        List<UserWithIdAndUsernameAndNickname> teachers = new ArrayList<>();
        Iterator<User> iterator = userList.iterator();
        List<Cno> cnoList = classRepository.getAllBy();
        while(iterator.hasNext()){
            User user = iterator.next();
            UserWithIdAndUsernameAndNickname student = new UserWithIdAndUsernameAndNickname(
                    user.getId(),
                    user.getNickname(),
                    user.getUsername()
            );
            if(user.getClassId()!=null && user.getClassId()>0){
                int tmp = Integer.parseInt(user.getClassId().toString())-1;
                student.setCno(cnoList.get(tmp).getName());
            }
            students.add(student);
        }
        Iterator<Teacher> iterator1 = teacherList.iterator();
        while(iterator1.hasNext()){
            Teacher user = iterator1.next();
            UserWithIdAndUsernameAndNickname teacher = new UserWithIdAndUsernameAndNickname(
                    user.getId(),
                    user.getNickname(),
                    user.getUsername()
            );
            teachers.add(teacher);
        }
        studentAndTeacherListDTO.setStudent(students);
        studentAndTeacherListDTO.setTeacher(teachers);
        return studentAndTeacherListDTO;
    }

    public MessageDTO addUser(AdminAddUserDTO adminAddUserDTO,String role,JwtService jwtService){
        if(!role.equals("student") && !role.equals("teacher")){
            throw new IllegalArgumentException("role路径参数有误,请填student或teacher");
        }
        String adminRole;
        try{
            adminRole = AESUtil.getRole(AESUtil.decrypt(adminAddUserDTO.getAuthorization(),AESUtil.key));
        } catch (Exception e){
            throw new TokenUnauthorized("令牌有误");
        }
        if(!adminRole.equals("admin")){
            throw new PermissionDeniedException("权限不足");
        }
        if(role.equals("student")){
            User user = new User();
            AdminUserDTO adminUserDTO = adminAddUserDTO.getUser();
            user.setUsername(adminUserDTO.getUsername());
            user.setPassword(passwordEncoder.encode(adminUserDTO.getPassword()));
            user.setNickname(adminUserDTO.getNickname());
            Cno cno = classRepository.getCnoById(adminAddUserDTO.getUser().getCno());
            if(cno == null){
                throw new UserNotFoundException("班级未找到");
            }
            user.setClassId(cno.getId());
            userRepository.save(user);
        }
        else {
            Teacher teacher = new Teacher();
            AdminUserDTO adminUserDTO = adminAddUserDTO.getUser();
            teacher.setUsername(adminUserDTO.getUsername());
            teacher.setPassword(passwordEncoder.encode(adminUserDTO.getPassword()));
            teacher.setNickname(adminUserDTO.getNickname());
            teacherRepository.save(teacher);
        }
        return new MessageDTO("用户新增成功");
    }

    public MessageDTO putUser(AdminPutUserDTO adminUserDTO,String role,Long id,JwtService jwtService){
        if(!role.equals("student") && !role.equals("teacher")){
            throw new IllegalArgumentException("role路径参数有误,请填student或teacher");
        }
        String adminRole;
        try{
            adminRole = AESUtil.getRole(AESUtil.decrypt(adminUserDTO.getAuthorization(),AESUtil.key));
        } catch (Exception e){
            throw new TokenUnauthorized("令牌有误");
        }
        if(!adminRole.equals("admin")){
            throw new PermissionDeniedException("权限不足");
        }
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException("用户未存在");
        }
        AdminUserDTO adminPutUserDTO = adminUserDTO.getUser();
        if(role.equals("student")){
            User user = userRepository.findUserById(id);
            if(adminPutUserDTO.getUsername() != null) user.setUsername(adminPutUserDTO.getUsername());
            if(adminPutUserDTO.getPassword() != null) user.setPassword(passwordEncoder.encode(adminPutUserDTO.getPassword()));
            if(adminPutUserDTO.getNickname() != null) user.setNickname(adminPutUserDTO.getNickname());
            if(adminPutUserDTO.getCno() != null){
                Cno cno = classRepository.getCnoById(adminPutUserDTO.getCno());
                if(cno == null){
                    throw new UserNotFoundException("班级未找到");
                }
                user.setClassId(cno.getId());
            }
            userRepository.save(user);
        }
        else {
            Teacher teacher = teacherRepository.findTeacherById(id);
            if(teacher.getUsername() != null) teacher.setUsername(adminPutUserDTO.getUsername());
            if(!adminPutUserDTO.getPassword().isEmpty()) teacher.setPassword(passwordEncoder.encode(adminPutUserDTO.getPassword()));
            if(teacher.getNickname() != null) teacher.setNickname(adminPutUserDTO.getNickname());
            teacherRepository.save(teacher);
        }
        return new MessageDTO("用户信息修改成功");
    }

    @Transactional
    public MessageDTO deleteUser(String authorization,String role,Long id,JwtService jwtService){
        if(!role.equals("student") && !role.equals("teacher")){
            throw new IllegalArgumentException("role路径参数有误,请填student或teacher");
        }
        String adminRole;
        try{
            adminRole = AESUtil.getRole(AESUtil.decrypt(authorization,AESUtil.key));
        } catch (Exception e){
            throw new TokenUnauthorized("令牌有误");
        }
        if(!adminRole.equals("admin")){
            throw new PermissionDeniedException("权限不足");
        }
        if(role.equals("student")){
            userRepository.deleteUserById(id);
        }
        else {
            teacherRepository.deleteTeacherById(id);
        }
        return new MessageDTO("用户删除成功");
    }

    public UserDTO getUser(AuthorizationDTO authorizationDTO,String role,String username,JwtService jwtService){
        if(!role.equals("student") && !role.equals("teacher")){
            throw new IllegalArgumentException("role路径参数有误,请填student或teacher");
        }
        String adminRole;
        try{
            adminRole = AESUtil.getRole(AESUtil.decrypt(authorizationDTO.getAuthorization(),AESUtil.key));
        } catch (Exception e){
            throw new TokenUnauthorized("令牌有误");
        }
        if(!adminRole.equals("admin")){
            throw new PermissionDeniedException("权限不足");
        }
        UserDTO userDTO;
        if(role.equals("student")){
            User user = userRepository.findByUsername(username);
            userDTO = new UserDTO(
                    user.getId(),
                    user.getUsername(),
                    "student",
                    "查询成功",
                    user.getNickname()
            ).setCno(classRepository.getCnoById(user.getClassId()).getName());
        }
        else {
            Teacher user = teacherRepository.findByUsername(username);
            userDTO = new UserDTO(
                    user.getId(),
                    user.getUsername(),
                    "teacher",
                    "查询成功",
                    user.getNickname()
            );
        }
        return userDTO;
    }
}
