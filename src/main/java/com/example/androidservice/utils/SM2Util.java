
package com.example.androidservice.utils;

import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 @author: laihuiming
 @date: 2023/12/14
 @version: 1.0
 @description: sm2加解密
 **/
public class SM2Util {
    /***
     * 公钥加密
     * @param plaintext
     * @param publicKey
     * @return
     */
    public static String encrypt(String plaintext,String publicKey){
        SM2 sm2 = SmUtil.sm2(null, publicKey);
        String ciphertext = sm2.encryptBase64(plaintext, KeyType.PublicKey);
        return ciphertext;
    }

    /***
     * 私钥解密
     * @param ciphertext
     * @param privateKey
     * @return
     */
    public static String decrypt(String ciphertext, String privateKey){
        SM2 sm2 = SmUtil.sm2(privateKey,null);
        String plaintext = sm2.decryptStr(ciphertext, KeyType.PrivateKey);
        return plaintext;
    }

    public static Map<String,String> getKeyMap(){
        SM2 sm2 = SmUtil.sm2();
        String publicKey = sm2.getPublicKeyBase64();
        String privateKey = sm2.getPrivateKeyBase64();
        Map<String, String> keyMap = new HashMap<>();
        keyMap.put("publicKey",publicKey);
        keyMap.put("privateKey",privateKey);
        return keyMap;
    }

    public static void main(String[] args) {
        Map<String, String> keyMap = getKeyMap();
        System.out.println(keyMap.get("publicKey"));
        System.out.println(keyMap.get("privateKey"));
//        String ciphertext = encrypt("112233445566", keyMap.get("publicKey"));
//        System.out.println("加密后:"+ciphertext);
//        String plaintext = decrypt(ciphertext, keyMap.get("privateKey"));
//        System.out.println("解密后:"+plaintext);
    }
}
