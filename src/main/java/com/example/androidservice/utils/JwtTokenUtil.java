
package com.example.androidservice.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.xml.crypto.Data;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 @author: laihuiming
 @date: 2023/12/15
 @version: 1.0
 @description: jwtToken工具类
 **/
public class JwtTokenUtil {

    public static final String SECRET = "laihm";
    public static final String PAYLOAD = "payload";
    public static final String HEAD_KEY = "token";

    public static String getToken(String userId, int time){
        Date now = new Date();
        Calendar newTime = Calendar.getInstance();
        newTime.add(Calendar.MINUTE,time);
        Date expDate = newTime.getTime();
        Map<String,Object> map = new HashMap<>(8);
        String token = JWT.create().withHeader(map)
                .withClaim(PAYLOAD,userId)
                .withExpiresAt(expDate)
                .withIssuedAt(now)
                .sign(Algorithm.HMAC256(SECRET));
        return token;
    }

    public static Map<String, Claim> verifierToken(String token){
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT jwt = null;
        try{
            jwt = verifier.verify(token);
        }catch (Exception e){
            throw new RuntimeException("凭证已过期，请重新获取!");
        }
        return jwt.getClaims();
    }

    public static String getUserIdByToken(String token){
        Map<String,Claim> map = verifierToken(token);
        String string = map.get(PAYLOAD).asString();
        return string;
    }
}
