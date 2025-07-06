package com.example.aiteach.util;

import com.example.aiteach.exception.FileUploadFailedException;
import org.junit.Test;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileMerger {
    public static final Map<Long, List<UserFile>> userFileMap = new HashMap<>();
    public static boolean addUserFile(Long userId,UserFile userFile,Long fileSum){
        if(userFileMap.containsKey(userId)){
            userFileMap.get(userId).add(userFile);
        }else{
            List<UserFile> list = new ArrayList<>();
            list.add(userFile);
            userFileMap.put(userId,list);
        }
        return userFileMap.get(userId).size() == fileSum;
    }
    public static void fileMerge(Long userId,String filename){
        try (FileOutputStream fos = new FileOutputStream(filename)){
            List<UserFile> list = userFileMap.get(userId);
            Collections.sort(list);
            for(UserFile userFile:list){
                byte[] buffer = new byte[1024];
                try (FileInputStream fis = new FileInputStream(userFile)){
                    int len;
                    while((len = fis.read(buffer)) !=-1){
                        fos.write(buffer,0,len);
                    }
                } catch (IOException e){
                    throw new FileUploadFailedException("文件上传失效");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void removeAllFile(Long userId) throws IOException {
        List<UserFile> list = userFileMap.get(userId);
        for(UserFile userFile:list){
            Files.delete(Paths.get(userFile.getPath()));
        }
        userFileMap.remove(userId);
    }
    @Test
    public void test(){
        Long userId= 1L;
        UserFile userFile1 = new UserFile("cxt/1",userId,1L);
        UserFile userFile2 = new UserFile("cxt/2",userId,2L);
        UserFile userFile3 = new UserFile("cxt/3",userId,3L);
        addUserFile(userId,userFile1,3L);
        addUserFile(userId,userFile2,3L);
        addUserFile(userId,userFile3,3L);
        fileMerge(userId,"cxt/123.docx");
    }
    @Test
    public void test1() throws IOException {
        File file = new File("cxt/example.docx");
        FileInputStream fis = new FileInputStream(file);
        FileChannel fileChannel = fis.getChannel();
        long size = fis.getChannel().size();
        long s = size/3;
        for(int i=0;i<3;i++){
            fileChannel.position(s*i);
            File file1 = new File("cxt/"+(i+1));
            FileOutputStream fos = new FileOutputStream(file1);
            byte[] buffer = new byte[1024];
            int len;
            long readBytes;
            if(i!=2) readBytes = s;
            else readBytes = size-(size/3)*2;
            while((len = fis.read(buffer))!=-1){
                System.out.println(readBytes);
                if(len<=readBytes){
                    fos.write(buffer,0,len);
                }else{
                    fos.write(buffer,0,(int)readBytes);
                    break;
                }
                readBytes-=len;
            }
            fos.close();
        }
        fis.close();
    }
}
