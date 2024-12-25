
package com.example.androidservice.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.*;

/**
 @author: laihuiming
 @date: 2023/12/12
 @version: 1.0
 @description:
 **/
public class DESUtil {
    public static void main(String[] args) throws Exception {
        // 加密
        String plainText = "Hello World!";
        String encrypt = encrypt(plainText, "1122334455667788");

        // 解密
        String decryptText = decrypt(encrypt, "1122334455667788");

        System.out.println("原文：" + plainText);
        System.out.println("密文：" + encrypt);
        System.out.println("解密后：" + decryptText);
    }

    public static SecretKey generateKey() throws Exception {
        // 实例化密钥工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

        // 生成密钥材料
        byte[] keyMaterial = new byte[8];
        new SecureRandom().nextBytes(keyMaterial);

        // 生成密钥
        SecretKey key = keyFactory.generateSecret(new DESKeySpec(keyMaterial));

        return key;
    }

    public static String encrypt(String plainText, String randomKey) throws Exception {
        // 实例化密钥工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        // 生成密钥
        SecretKey key = keyFactory.generateSecret(new DESKeySpec(randomKey.getBytes()));
        // 实例化加密器
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

        // 初始化加密器
        cipher.init(Cipher.ENCRYPT_MODE, key);

        // 加密
        byte[] cipherText = cipher.doFinal(plainText.getBytes());

        return new String(cipherText);
    }

    public static String decrypt(String cipherText, String randomKey) throws Exception {
        // 实例化密钥工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        // 生成密钥
        SecretKey key = keyFactory.generateSecret(new DESKeySpec(randomKey.getBytes()));
        // 实例化解密器
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

        // 初始化解密器
        cipher.init(Cipher.DECRYPT_MODE, key);

        // 解密
        byte[] plainText = cipher.doFinal(cipherText.getBytes());

        return new String(plainText);
    }
}
