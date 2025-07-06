package com.example.aiteach.OAuth2;

import com.example.aiteach.tempjwt.AESUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.SecretKey;

public class Test {
    public static void main(String[] args) throws Exception {
        SecretKey key = AESUtil.generateKey();
        System.out.println(key);
    }
}
