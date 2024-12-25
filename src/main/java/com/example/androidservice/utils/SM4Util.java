
package com.example.androidservice.utils;

import cn.hutool.crypto.symmetric.SymmetricCrypto;

/**
 @author: laihuiming
 @date: 2023/12/14
 @version: 1.0
 @description: sm4加密工具类
 **/
public class SM4Util {
    //加密为16进制，也可以加密成base64/字节数组
    public static String encryptSm4(String plaintext, String key) {
        //指明加密算法和秘钥
        SymmetricCrypto sm4 = new SymmetricCrypto("SM4/ECB/PKCS5Padding", key.getBytes());
        return sm4.encryptHex(plaintext);
    }

    //解密
    public static String decryptSm4(String ciphertext, String key) {
        //指明加密算法和秘钥
        SymmetricCrypto sm4 = new SymmetricCrypto("SM4/ECB/PKCS5Padding", key.getBytes());
        return sm4.decryptStr(ciphertext);
    }
}
