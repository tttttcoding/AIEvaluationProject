package com.example.aiteach.util;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class UserFile extends File{

    public  UserFile(@NotNull String pathname,Long userId,Long fileId){
        super(pathname);
        this.userId=userId;
        this.fileId=fileId;
    }

    private final Long userId;
    private final Long fileId;

    public Long getUserId() {
        return userId;
    }
    public Long getFileId() {
        return fileId;
    }

    @Override
    public int compareTo(File file){
        return Long.compare(this.fileId,((UserFile) file).getFileId());
    }
}
