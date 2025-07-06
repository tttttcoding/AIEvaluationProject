package com.example.aiteach.service.impl;

import com.example.aiteach.DTO.*;
import com.example.aiteach.OAuth2.JwtService;
import com.example.aiteach.exception.*;
import com.example.aiteach.models.*;
import com.example.aiteach.service.*;
import com.example.aiteach.projection.UserProjection;
import com.example.aiteach.tempjwt.AESUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private RateRepository rateRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ResultFileRepository resultFileRepository;
    public UserMetaDTO registerUser(UserCreateDTO userCreateDTO, JwtService jwtService) {
        if (userRepository.existsUserByUsername(userCreateDTO.getUsername())) {
            throw new UserAlreadyExistsException("student already exists");
        }
        String token;
        try {
            token = AESUtil.encrypt(userCreateDTO.getUsername()+" "+"student", AESUtil.key);
        } catch (Exception e) {
            throw new RuntimeException();
        }
        User user = new User();
        user.setUsername(userCreateDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        user.setClassId(userCreateDTO.getCno());
        userRepository.save(user);
        String className = "未归属";
        if(userCreateDTO.getCno()!=0){
            className = classRepository.getCnoById(userCreateDTO.getCno()).getName();
        }
        UserMetaDTO metaDTO = new UserMetaDTO(token, new UserDTO(user.getId(),
                user.getUsername(),
                "student",
                "用户注册成功").setCno(className));
        return metaDTO;
    }

    public UserMetaDTO registerTeacher(UserCreateDTO userCreateDTO, JwtService jwtService) {
        if (teacherRepository.existsTeacherByUsername(userCreateDTO.getUsername())) {
            throw new UserAlreadyExistsException("teacher already exists");
        }
        String token;
        try {
            token = AESUtil.encrypt(userCreateDTO.getUsername()+" "+"teacher", AESUtil.key);
        } catch (Exception e) {
            throw new RuntimeException();
        }
        Teacher teacher = new Teacher();
        teacher.setUsername(userCreateDTO.getUsername());
        teacher.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        teacherRepository.save(teacher);
        UserMetaDTO metaDTO = new UserMetaDTO(token, new UserDTO(teacher.getId(),
                teacher.getUsername(),
                "teacher",
                "用户注册成功"));
        return metaDTO;
    }

    public UserMetaDTO userLogin(UserCreateDTO userCreateDTO, JwtService jwtService){
        User user = userRepository.findByUsername(userCreateDTO.getUsername());
        if (user == null){
            throw new UserNotFoundException("user not found");
        }
        if(!passwordEncoder.matches(userCreateDTO.getPassword(),user.getPassword())){
            throw new PasswordWrongException("用户名或密码错误");
        }
        String token;
        try {
            token = AESUtil.encrypt(userCreateDTO.getUsername()+" "+"student", AESUtil.key);
        } catch (Exception e) {
            throw new RuntimeException();
        }
        String className = "未归属";
        if(user.getClassId()!= 0 && user.getClassId() != null){
            className = classRepository.getCnoById(user.getClassId()).getName();
        }
        return new UserMetaDTO(
                token,
                new UserIncludeFreshDTO(user.getId(),
                        user.getUsername(),
                        "student",
                        "用户登录成功",
                        user.getNickname()).setCno(className).resetIsFresh(user.getIsFresh())
        );
    }
    public UserMetaDTO teacherLogin(UserCreateDTO userCreateDTO, JwtService jwtService){
        Teacher user = teacherRepository.findByUsername(userCreateDTO.getUsername());
        if (user == null){
            throw new UserNotFoundException("teacher not found");
        }
        if(!passwordEncoder.matches(userCreateDTO.getPassword(),user.getPassword())){
            throw new PasswordWrongException("用户名或密码错误");
        }
        String token;
        try {
            token = AESUtil.encrypt(userCreateDTO.getUsername()+" "+"teacher", AESUtil.key);
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return new UserMetaDTO(
                token,
                new UserDTO(user.getId(),
                        user.getUsername(),
                        "teacher",
                        "用户登录成功",
                        user.getNickname()).setCno("未归属")
        );
    }
//    public List<UserProjection> getUsers(){
//        return userRepository.findAllBy();
//    }

    public UserMetaDTO putUser(UserPutDTO userPutDTO, JwtService jwtService){
        String username = "";
        try{
            username = AESUtil.getUsername(AESUtil.decrypt(userPutDTO.getAuthorization(),AESUtil.key));
        } catch (Exception e){
            throw new TokenUnauthorized("令牌有误");
        }
        if(!userRepository.existsUserByUsername(username)){
            throw new UserNotFoundException("用户不存在");
        }
        User user = userRepository.findByUsername(username);
        if(!userPutDTO.getPassword().isEmpty() && !userPutDTO.getOld_password().isEmpty()){
            if(!passwordEncoder.matches(userPutDTO.getOld_password(),user.getPassword())){
                throw new PasswordWrongException("密码错误");
            }
            user.setPassword(passwordEncoder.encode(userPutDTO.getPassword()));
            user.setIsFresh(0L);
        }
        String className = "未归属";
        if(userPutDTO.getCno()!=null){
            Cno cno = classRepository.getCnoById(userPutDTO.getCno());
            if(cno == null){
                throw new UserNotFoundException("找不到该班级请输入id");
            }
            user.setClassId(cno.getId());
            className = cno.getName();
        }
        if(!userPutDTO.getUsername().isEmpty()) user.setUsername(userPutDTO.getUsername());
        if(!userPutDTO.getNickname().isEmpty()) user.setNickname(userPutDTO.getNickname());
        userRepository.save(user);
        return new UserMetaDTO(
                userPutDTO.getAuthorization(),
                new UserDTO(
                        user.getId(),
                        user.getUsername(),
                        "student",
                        "用户信息修改成功",
                        user.getNickname()
                ).setCno(className)
        );
    }

    public RatingListDTO getAllHistory(String authorization){
        String username = "";
        try{
            username = AESUtil.getUsername(AESUtil.decrypt(authorization,AESUtil.key));
        } catch (Exception e){
            throw new TokenUnauthorized("令牌有误");
        }
        if(!userRepository.existsUserByUsername(username)){
            throw new UserNotFoundException("用户不存在");
        }
        User user = userRepository.findByUsername(username);
        List<Rate> rateList = rateRepository.findAllByUserId(user.getId());
        List<RatingsWithToolId> ratingsDTOList = new ArrayList<>();
        for(Rate rate:rateList){
            RatingsWithToolId ratingsDTO = new RatingsWithToolId(
                    username,
                    rate.getRating(),
                    rate.getComment(),
                    rate.getRateTime(),
                    rate.getAi_id()
            );
            ratingsDTO.setId(rate.getId());
            ratingsDTOList.add(ratingsDTO);
        }
        return new RatingListDTO(ratingsDTOList);
    }

    public CourseListDTO getCourses(String authorization){
        String username = "";
        String decode;
        try{
            decode = AESUtil.decrypt(authorization,AESUtil.key);
            username = AESUtil.getUsername(decode);
        } catch (Exception e){
            throw new TokenUnauthorized("令牌有误");
        }
        if(!AESUtil.getRole(decode).equals("teacher")){
            throw new PermissionDeniedException("权限不足");
        }
        Teacher teacher = teacherRepository.findByUsername(username);
        if(teacher == null){
            throw new IllegalArgumentException("该教师不存在");
        }
        List<Course> courseList = courseRepository.findCoursesByTeacherId(teacher.getId());
        List<CourseDTO> courseDTOList = new ArrayList<>();
        for(Course course:courseList){
            Long classId = course.getClassId();
            courseDTOList.add(new CourseDTO(
                    course.getId(),
                    course.getName(),
                    course.getDescription(),
                    classRepository.getCnoById(classId).getName()
            ));
        }
        return new CourseListDTO(courseDTOList);
    }

    public CourseCreateResponseDTO courseRegister(CourseCreateDTO courseCreateDTO){
        String username = "";
        String decode;
        try{
            decode = AESUtil.decrypt(courseCreateDTO.getAuthorization(),AESUtil.key);
            username = AESUtil.getUsername(decode);
        } catch (Exception e){
            throw new TokenUnauthorized("令牌有误");
        }
        if(!AESUtil.getRole(decode).equals("teacher")){
            throw new PermissionDeniedException("权限不足");
        }
        Teacher teacher = teacherRepository.findByUsername(username);
        if(teacher == null){
            throw new IllegalArgumentException("该教师不存在");
        }
        if(!classRepository.existsById(courseCreateDTO.getCno())){
            throw new IllegalArgumentException("班级未找到");
        }
        Course course = new Course();
        course.setClassId(courseCreateDTO.getCno());
        course.setDescription(courseCreateDTO.getDescription());
        course.setName(courseCreateDTO.getCourse());
        course.setTeacherId(teacher.getId());
        courseRepository.save(course);
        String className = classRepository.getCnoById(courseCreateDTO.getCno()).getName();
        return new CourseCreateResponseDTO(
                "创建成功",
                course.getId(),
                className
        );
    }

    public MessageDTO coursePut(CourseCreateDTO courseCreateDTO,Long courseId){
        String username = "";
        String decode;
        try{
            decode = AESUtil.decrypt(courseCreateDTO.getAuthorization(),AESUtil.key);
            username = AESUtil.getUsername(decode);
        } catch (Exception e){
            throw new TokenUnauthorized("令牌有误");
        }
        if(!AESUtil.getRole(decode).equals("teacher")){
            throw new PermissionDeniedException("权限不足");
        }
        Teacher teacher = teacherRepository.findByUsername(username);
        if(teacher == null){
            throw new UserNotFoundException("该教师不存在");
        }
        if(!classRepository.existsById(courseCreateDTO.getCno())){
            throw new UserNotFoundException("班级未找到");
        }
        Course course = courseRepository.findCourseById(courseId);
        if(course.getTeacherId() != teacher.getId()){
            throw new IllegalArgumentException("该教师未与该课程绑定");
        }
        course.setClassId(courseCreateDTO.getCno());
        course.setDescription(courseCreateDTO.getDescription());
        course.setName(courseCreateDTO.getCourse());
        courseRepository.save(course);
        return new MessageDTO(
                "修改成功"
        );
    }

    public CourseWithRatesDTO getRateOfCourse(String authorization,Long courseId){
        String username = "";
        String decode;
        try{
            decode = AESUtil.decrypt(authorization,AESUtil.key);
            username = AESUtil.getUsername(decode);
        } catch (Exception e){
            throw new TokenUnauthorized("令牌有误");
        }
        if(!AESUtil.getRole(decode).equals("teacher")){
            throw new PermissionDeniedException("权限不足");
        }
        Teacher teacher = teacherRepository.findByUsername(username);
        if(teacher == null){
            throw new UserNotFoundException("该教师不存在");
        }
        Course course = courseRepository.findCourseById(courseId);
        if(course == null){
            throw new IllegalArgumentException("课程未找到");
        }
        List<User> userList = userRepository.findUsersByClassId(course.getClassId());
        List<RateForCourseDTO> rates = new ArrayList<>();
        for(User user:userList){
            String className = "未归属";
            if(user.getClassId()!=0){
                if(classRepository.existsById(user.getClassId())){
                    className = classRepository.getCnoById(user.getClassId()).getName();
                }else{
                    throw new IllegalArgumentException("班级未找到");
                }
            }
            for(Rate rate:rateRepository.findAllByUserId(user.getId())){
                RateForCourseDTO rateForCourseDTO = new RateForCourseDTO(
                        user.getUsername(),
                        user.getNickname(),
                        rate.getRating(),
                        rate.getComment(),
                        className,
                        rate.getRateTime(),
                        rate.getAi_id()
                );
                rates.add(rateForCourseDTO);
            }
        }
        return new CourseWithRatesDTO(
                "查询成功",
                rates
        );
    }

    @Transactional
    public MessageDTO deleteCourse(AuthorizationDTO authorizationDTO,Long courseId){
        String username = "";
        String decode;
        try{
            decode = AESUtil.decrypt(authorizationDTO.getAuthorization(),AESUtil.key);
            username = AESUtil.getUsername(decode);
        } catch (Exception e){
            throw new TokenUnauthorized("令牌有误");
        }
        if(!AESUtil.getRole(decode).equals("teacher")){
            throw new PermissionDeniedException("权限不足");
        }
        Teacher teacher = teacherRepository.findByUsername(username);
        if(teacher == null){
            throw new UserNotFoundException("该教师不存在");
        }
        Course course = courseRepository.findCourseById(courseId);
        if(course == null){
            throw new IllegalArgumentException("课程未找到");
        }
        courseRepository.deleteCourseById(courseId);
        return new MessageDTO("删除成功");
    }

    @Transactional
    public MessageDTO deleteReview(String authorization,Long rateId){
        String username = "";
        String decode;
        try{
            decode = AESUtil.decrypt(authorization,AESUtil.key);
            username = AESUtil.getUsername(decode);
        } catch (Exception e){
            throw new TokenUnauthorized("令牌有误");
        }
        if(!rateRepository.existsById(rateId)){
            throw new IllegalArgumentException("评论id有误");
        }
        if(!username.equals(userRepository.findUserById(rateRepository.findUserIdById(rateId)).getUsername())){
            throw new IllegalArgumentException("不能删除别人的");
        }
        rateRepository.deleteRateById(rateId);
        return new MessageDTO("删除成功");
    }

    public ClassWithUserWithSumDTO getSumOfClass(String authorization,Long classId){
        try{
            String decode = AESUtil.decrypt(authorization,AESUtil.key);
            if(!AESUtil.getRole(decode).equals("teacher")){
                throw new PermissionDeniedException("权限不足");
            }
        } catch (Exception e){
            throw new TokenUnauthorized("令牌有误");
        }
        Cno cno = classRepository.getCnoById(classId);
        if(cno == null){
            throw new IllegalArgumentException("班级不存在");
        }
        List<User> userList = userRepository.findUsersByClassId(classId);
        List<UserWithSumDTO> list = new ArrayList<>();
        for(User user:userList){
            UserWithSumDTO userWithSumDTO = new UserWithSumDTO(
                    user.getUsername(),
                    user.getNickname(),
                    resultFileRepository.findSumOfUserReport(user.getId()),
                    rateRepository.findSumOfUserReview(user.getId())
            );
            list.add(userWithSumDTO);
        }
        return new ClassWithUserWithSumDTO(
                "查询成功",
                cno.getName(),
                list
        );
    }

    public RatingListDTO getParticularStudent(String authorization,String username){
        String decode;
        try{
            decode = AESUtil.decrypt(authorization,AESUtil.key);
            if(!AESUtil.getRole(decode).equals("teacher")){
                throw new PermissionDeniedException("权限不足");
            }
        } catch (Exception e){
            throw new TokenUnauthorized("令牌有误");
        }
        User user =userRepository.findByUsername(username);
        List<Rate> rateList = rateRepository.findAllByUserId(user.getId());
        List<RatingsWithToolId> dtos = new ArrayList<>();
        for(Rate rate:rateList){
            RatingsWithToolId dto = new RatingsWithToolId(
                    user.getNickname(),
                    rate.getRating(),
                    rate.getComment(),
                    rate.getRateTime(),
                    rate.getAi_id()
            );
            dto.setId(user.getId());
        }
        return new RatingListDTO(dtos);
    }
}

