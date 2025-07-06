package com.example.aiteach.tempjwt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESUtil {
    private static final String AES = "AES";
    public static final SecretKey key;

    static {
        try {
            key = generateKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 生成密钥
    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(AES);
        keyGen.init(128); // 可以选择128、192、256位密钥
        return keyGen.generateKey();
    }

    // 加密
    public static String encrypt(String data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // 解密
    public static String decrypt(String encryptedData, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        System.out.println(new String(decryptedBytes));
        return new String(decryptedBytes);
    }

    //验证身份
    public static String getRole(String decryptedData){
        String[] str1 = decryptedData.split(" ");
        if(str1.length != 2){
            throw new RuntimeException();
        }
        return str1[1];
    }
    public static String getUsername(String decryptedData){
        String[] str1 = decryptedData.split(" ");
        if(str1.length != 2){
            throw new RuntimeException();
        }
        return str1[0];
    }
}