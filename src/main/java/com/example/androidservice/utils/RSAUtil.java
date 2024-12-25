
package com.example.androidservice.utils;

import com.example.androidservice.base.BaseConstant;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 @author: laihuiming
 @date: 2023/12/13
 @version: 1.0
 @description: RSA加解密工具
 **/
public class  RSAUtil {
    public static final String PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC/fJudKeV1Z5MmfoCO0zW2vU93VITdX1Gtb0sXkd64QcC8M+I/8+5JD1LXONhwZJbweRtE5v6wN/Jn6RRVeZ08qimlLXp7MIYl1icWWcZ97DYRZ20SRQUOLRol8t0Kcc+tkIvVsaMQcKS1qlHzX7puHKr0zxmQC8hpaTQlzifBLTj1qWw/WbEYVjwaQsEWWf0XQKJoXqCERqu6+50RKAJiNdjUKUxHnKBq2dle9OsIRKYvTeuXe9Z0wCSoZuBb51hU2ufMK5gjwGjyNFdZKrFP7eCFColxSqWRcjDaj09cPW0YmPkgHkNJKPMRYYW/1DXpVErWdJN3k4+RdGdTkn73AgMBAAECggEASjeItOR9LAEF9qCHQVQ5eGdtemnGyWpdZORC7ZK7CR1rD0boxTLNQ4Ht8L72nLFQE0pNOGpzgmdcy8t59xsYx1+Inp2+4CpRhC65nZ8UINBBsIMusisQJ4txnA3CgH7WKIZivGOKOR//pu2pr7OFtZHk7NesJ7l5ZunArXp2zBcIUMzfadYisnMvzHqzDDvTOdDWUaBjqHK5JwZ2x6C19oN0SlRyL3wyvDZQTjjuMehoH8Kn+zWmI6BhIMRHRiDqmNaBWspIeUolgz92tNJAXy1pAfVKyuRiYoBCKcwDv0kbhefejNFDtciIlJ3ufE0RiBrSaWDttI1v6GBDIXbToQKBgQDjcBgNAThIg+nHNEoM3fMbnz1fkvWRgQ2HpPoMj6Kig9T7d4u+m1mY2NDj4sIxMIsEQ/JI7s2T0IqbN4qXJrF2bhjbcX2ndDFJGaplzNi5yh9im+KrdB/8PybUVbC62ezqHHLe/Yg05cYuDjorrak2O0nT7Pbo4hE1oe58BPyz5wKBgQDXiLhxkWwnelT94vP7ZO3lSsfOvIzQUR1ozXnkS954nmrA5xgzn6PPR10y1jIEKXl+XH1Uc8sUs5qGPAaVmPvx3jNRGw77XPDOQff4IvEC4wDU0AcDplwvAHOl3l+Y+xWyRbyoagshiV5fK0z5J8AMjufCcn4RkdwA7PORAf16cQKBgGbR+k2Gy6cc28dPAFsozAskxl461gHd0fwMvW6IUQIfj13QAcE+AhdzjZw4m4oZY/+dqB8Itq+oi8D4LxN+TqQ3yc0LVb8qbE2b6Mj0/tWfZujxiFvyt7MV6bv8sVUHyG9eRSBChh1D84BGtk2gyOrXitzgOA+4hAc3GdW/e27pAoGBALW50BHpVDCBA5JBPgez1KVkhM15ypyGF+7aeLCVi5FVuJrG/m/gZQSKnjkTwn4h3g89umpva0YcHOibSc3ufFc1Sz1SPDKR7XNqycHM7DrL3VKRx6g7JzR2BAxRT3c0FNoH1IaHSrVPFmPVpYPaESJFh4E6ebMksUOShda4v9jxAoGAbDtevRikHsSAj5RQRZ2Ijpwx5yHmNT1X8dD/z737CgGzKVWlHgz2UqsnXCitWJs0Fa8v+Y4k9AA9EvyNnuWIaC44222iLHOL56uugP03H4QyyUWVlNNnZ6F2eFHD/k9I7Lp2KvXYPVP214SR5ss2aWjM/M9VGBQgL+jS6seaRiE=";
    public static String getPublicKey(){
        // 生成密钥对
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // 获取公钥
        PublicKey publicKey = keyPair.getPublic();
        byte[] publicKeyBytes = publicKey.getEncoded();
        String stringPublicKey = Base64.getEncoder().encodeToString(publicKeyBytes);
        return stringPublicKey;
    }

    /***
     * 用公钥加密
     * @param plainText
     * @param stringPublicKey
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(String plainText, String stringPublicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(stringPublicKey));
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(plainText.getBytes());
    }

    /***
     * 用私钥解密
     * @param cipherText
     * @return
     * @throws Exception
     */
    public static String decrypt(String cipherText) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(PRIVATE_KEY));
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(cipherText.getBytes()));
    }
}
