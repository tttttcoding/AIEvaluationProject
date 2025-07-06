package com.example.aiteach.OAuth2;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;

public class GenerateJwtKey {

    public static void main(String[] args) {
        try {
            // 生成一个随机的对称密钥（HMAC密钥）
            SecureRandom random = new SecureRandom();
            byte[] keyBytes = new byte[16]; // 16字节，即128位
            random.nextBytes(keyBytes);

            // 将密钥保存到jwt.key文件中
            try (FileOutputStream fos = new FileOutputStream("jwt.key")) {
                fos.write(keyBytes);
            }

            System.out.println(keyBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
