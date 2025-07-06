package com.example.aiteach.util;

import com.example.aiteach.models.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserGetTime {
    private final User user;
    private final Long time;
    private static final List<UserGetTime> userList = new ArrayList<>();

    public UserGetTime(User user){
        this.time = System.currentTimeMillis();
        this.user = user;
        userList.add(this);
    }
    public static UserGetTime findClassByUser(User user){
        Iterator<UserGetTime> iterator = userList.iterator();
        while(iterator.hasNext()){
            UserGetTime tem = iterator.next();
            if(tem.getUser().getUsername().equals(user.getUsername())){
                return tem;
            }
        }
        return null;
    }
    public boolean isExpired(){
        Long userTime = this.getTime();
        Long currentTime = System.currentTimeMillis();
        return (currentTime - userTime) > 30000;
    }
    public void remove(){
        userList.remove(this);
    }
    public User getUser() {
        return user;
    }

    public Long getTime() {
        return time;
    }
}
