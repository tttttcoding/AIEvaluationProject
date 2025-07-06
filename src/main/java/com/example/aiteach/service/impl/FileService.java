package com.example.aiteach.service.impl;

import com.example.aiteach.DTO.AuthorizationDTO;
import com.example.aiteach.DTO.MessageDTO;
import com.example.aiteach.DTO.UserGetHistoryDTO;
import com.example.aiteach.DTO.UserHistoryDTO;
import com.example.aiteach.exception.*;
import com.example.aiteach.exception.NoResultGetException;
import com.example.aiteach.models.ResultFile;
import com.example.aiteach.models.Teacher;
import com.example.aiteach.models.User;
import com.example.aiteach.service.FileRepository;
import com.example.aiteach.service.ResultFileRepository;
import com.example.aiteach.service.TeacherRepository;
import com.example.aiteach.service.UserRepository;
import com.example.aiteach.tempjwt.AESUtil;
import com.example.aiteach.util.AIEvaluateHttpClient;
import com.example.aiteach.util.FileMerger;
import com.example.aiteach.util.UserFile;
import com.example.aiteach.util.UserGetTime;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FileService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private ResultFileRepository resultFileRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    public void fileTransferAndSave(MultipartFile file,String filename,boolean isDocx){
        if(isDocx){
            Pattern pattern = Pattern.compile(".*(.docx)$");
            Matcher matcher = pattern.matcher(file.getOriginalFilename());
            if(!matcher.matches()){
                throw new FileUploadFailedException("请上传docx文件");
            }
        }
        Path destFile = Paths.get(filename);
        if(!Files.exists(destFile)){
            try{
                Files.createFile(destFile);
            }catch (Exception e){
                throw new FileUploadFailedException("文件上传失败");
            }
        }
        try{
            file.transferTo(destFile);
        }catch (IOException e){
            throw new FileUploadFailedException("文件传输失败");
        }
    }

    @Transactional
    public MessageDTO fileUpload(String authorization,String filename){
        String username;
        String role;
        try{
            username = AESUtil.getUsername(AESUtil.decrypt(authorization,AESUtil.key));
            role = AESUtil.getRole(AESUtil.decrypt(authorization,AESUtil.key));
        } catch (Exception e){
            throw new TokenUnauthorized("令牌有误");
        }
        if(!role.equals("student")){
            throw new IllegalArgumentException("only student");
        }
        User user = userRepository.findByUsername(username);
        Long id = user.getId();
        Path currentPath = Paths.get("evaluations").toAbsolutePath();
        if(!Files.exists(currentPath)){
            try{
                Files.createDirectory(currentPath);
            } catch (IOException e){
                System.out.println(e.getMessage());
                throw new RuntimeException("无法创建目录");
            }
        }
        List<com.example.aiteach.models.File> fileList = fileRepository.findAllByUserId(id);
        for(com.example.aiteach.models.File file:fileList){
            String path = file.getPath();
            try {
                Files.delete(Path.of(path));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        fileRepository.deleteFileByUserId(id);
        com.example.aiteach.models.File file1 = new com.example.aiteach.models.File();
        file1.setUserId(id);
        file1.setPath(filename);
        file1.setCreateDate(LocalDateTime.now());
        file1.setFilename(filename);
        fileRepository.save(file1);
        File resultFile = new File("evaluateResult"+"/"+UUID.randomUUID());
        return new MessageDTO("上传成功");
    }

    @Transactional
    public MessageDTO fileUpload(String authorization, MultipartFile file,Long fileSum,Long fileId){
        String username;
        String role;
        try{
            username = AESUtil.getUsername(AESUtil.decrypt(authorization,AESUtil.key));
            role = AESUtil.getRole(AESUtil.decrypt(authorization,AESUtil.key));
        } catch (Exception e){
            throw new TokenUnauthorized("令牌有误");
        }
        if(!role.equals("student")){
            throw new IllegalArgumentException("only student");
        }
        if(file.isEmpty()){
            throw new IllegalArgumentException("文件为空");
        }
        User user = userRepository.findByUsername(username);
        if(user == null) throw new UserNotFoundException("用户不存在");
        Long userId = user.getId();
        String filename = Paths.get("evaluations").toAbsolutePath()+ "/"+UUID.randomUUID()+file.getOriginalFilename();
        fileTransferAndSave(file,filename,false);
        UserFile userFile = new UserFile(filename,userId,fileId);
        if(FileMerger.addUserFile(userId,userFile,fileSum)){
            FileMerger.fileMerge(userId,filename+".docx");
            //请移除所有分片文件
            try{
                FileMerger.removeAllFile(userId);
            }catch (IOException e){
                throw new FileUploadFailedException("文件上传失败");
            }
            fileUpload(authorization,filename+".docx");
            return new MessageDTO("分片文件全部上传完毕，请调用/evaluate接口获取结果");
        }
        return new MessageDTO("分片文件上传成功，还需要"+(fileSum-FileMerger.userFileMap.get(userId).size())+"个文件");
    }

    public MessageDTO getEvaluate(AuthorizationDTO authorizationDTO,Long toolId){
        String username = "";
        try{
            username = AESUtil.getUsername(AESUtil.decrypt(authorizationDTO.getAuthorization(),AESUtil.key));
        } catch (Exception e){
            throw new TokenUnauthorized("令牌有误");
        }
        if(!userRepository.existsUserByUsername(username)){
            throw new UserNotFoundException("用户不存在");
        }
        User user = userRepository.findByUsername(username);
        com.example.aiteach.models.File file = fileRepository.findTopByUserIdOrderByIdDesc(user.getId());
        if(file == null || LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")) - file.getCreateDate().toEpochSecond(ZoneOffset.of("+8"))>600){
            throw new IllegalArgumentException("未上传评价文件或者距离上次文件传输已过600秒，请确认文件是否上传完毕，要想获取上次记录请调用/evaluate/history");
        }
        String filename = file.getFilename();
        Path resultPath = Paths.get("evaluateResult").toAbsolutePath();
        if(!Files.exists(resultPath)){
            try{
                Files.createDirectory(resultPath);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        //避免前端轮询对模型服务器造成压力
        UserGetTime userGetTime = UserGetTime.findClassByUser(user);
        if(userGetTime==null){
            new UserGetTime(user);
        } else if (userGetTime.isExpired()) {
            System.out.println(System.currentTimeMillis() - userGetTime.getTime());
            userGetTime.remove();
            new UserGetTime(user);
        } else {
            List<String> resultFilePathList = resultFileRepository.findPathByUserIdOrderByIdDesc(user.getId(), LocalDateTime.now().minusSeconds(60));
            if(resultFilePathList.isEmpty()){
                throw new NoResultGetException("processing");
            }
            String resultFilePath = resultFilePathList.get(0);
            if(resultFilePath.isEmpty() || !Files.exists(Paths.get(resultFilePath))){
                throw new NoResultGetException("processing");
            }
            try{
                String evaluate = Files.readString(Paths.get(resultFilePath));
                userGetTime.remove();
                return new MessageDTO(evaluate);
            } catch (IOException e){
                System.out.println(e.getMessage());
                throw new RuntimeException(e.getMessage());
            }
        }
        File destFile;
        try{
            destFile = new File(filename);
            String result = AIEvaluateHttpClient.postEvaluate(destFile,filename);
            Path resultFilePath = Paths.get("evaluateResult"+"/"+UUID.randomUUID()).toAbsolutePath();
            if(!Files.exists(resultFilePath)){
                Files.createFile(resultFilePath);
            }
            Files.write(resultFilePath, result.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            ResultFile resultFile = new ResultFile();
            resultFile.setToolId(toolId);
            resultFile.setPath(resultFilePath.toString());
            resultFile.setCreateTime(LocalDateTime.now());
            resultFile.setUserId(user.getId());
            resultFileRepository.save(resultFile);
        }catch (Exception e){
            if(userGetTime!=null) userGetTime.remove();
            throw new RuntimeException(e.getMessage());
        }
        throw new NoResultGetException("processing");
    }

    public UserGetHistoryDTO getHistory(String authorization){
        String username;
        String role;
        try{
            username = AESUtil.getUsername(AESUtil.decrypt(authorization,AESUtil.key));
            role = AESUtil.getRole(AESUtil.decrypt(authorization,AESUtil.key));
        } catch (Exception e){
            throw new TokenUnauthorized("令牌有误");
        }
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UserNotFoundException("user not found");
        }
        List<ResultFile> resultFiles = resultFileRepository.findAllByUserIdOrderByIdDesc(user.getId());
        return getUserGetHistoryDTO(resultFiles);
    }

    public UserGetHistoryDTO getHistory(String authorization,String userName){
        String username;
        String role;
        try{
            username = AESUtil.getUsername(AESUtil.decrypt(authorization,AESUtil.key));
            role = AESUtil.getRole(AESUtil.decrypt(authorization,AESUtil.key));
            if(!role.equals("teacher")){
                throw new PermissionDeniedException("教师专用");
            }
        } catch (Exception e){
            throw new TokenUnauthorized("令牌有误");
        }
        Teacher user = teacherRepository.findByUsername(username);
        if(user == null){
            throw new UserNotFoundException("user not found");
        }
        Long userId = userRepository.findByUsername(userName).getId();
        List<ResultFile> resultFiles = resultFileRepository.findAllByUserIdOrderByIdDesc(userId);
        return getUserGetHistoryDTO(resultFiles);
    }

    @NotNull
    private UserGetHistoryDTO getUserGetHistoryDTO(List<ResultFile> resultFiles) {
        List<UserHistoryDTO> historyLists = new ArrayList<>();
        for(ResultFile resultFile: resultFiles){
            try {
                File file = new File(resultFile.getPath());
                StringBuilder content = new StringBuilder();
                byte[] buffer = new byte[1024];
                int len;
                FileInputStream fis = new FileInputStream(file);
                while((len = fis.read(buffer)) != -1){
                    String bufferString = new String(buffer,0,len);
                    content.append(bufferString);
                }
                UserHistoryDTO userHistoryDTO = new UserHistoryDTO(
                        resultFile.getToolId(),
                        resultFile.getCreateTime(),
                        content.toString()
                );
                historyLists.add(userHistoryDTO);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return new UserGetHistoryDTO(
                "查询成功",
                historyLists);
    }
}
