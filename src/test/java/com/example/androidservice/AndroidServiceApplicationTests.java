package com.example.androidservice;

import com.example.androidservice.entity.SystemUser;
import com.example.androidservice.service.CurrencyService;
import com.example.androidservice.service.DocService;
import com.example.androidservice.service.ExcelImportService;
import com.example.androidservice.utils.JwtTokenUtil;
import com.example.androidservice.utils.PassWordUtil;
import com.example.androidservice.utils.SM2Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import java.net.URLEncoder;

@SpringBootTest
class AndroidServiceApplicationTests {

    @Autowired
    CurrencyService currencyService;

    @Autowired
    ExcelImportService excelImportService;

    @Autowired
    private Environment env;

    @Autowired
    DocService docService;

    @Test
    void contextLoads() {
        String sidByRandomKey = currencyService.getSidByRandomKey("1122334455667788");
        System.out.println(sidByRandomKey);
    }

    @Test
    void getToken(){
        String token = JwtTokenUtil.getToken("123456", 300);
        System.out.println(token);
    }

//    @Test
//    void verifierToken(){
//        String userIdByToken = JwtTokenUtil.getUserIdByToken("");
//        System.out.println(userIdByToken);
//    }

    @Test
    void test(){
        String admin = PassWordUtil.sm3Encrypt("admin", "123456");
        System.out.println(admin);
    }

    @Test
    void get16sid(){
        String encrypt = SM2Util.encrypt("1122334455667788", "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEXE1NelY+xrgf7oUjv3Vu+KC6loeRKggHlHJB5Va6xuw59NwGziAXx37d9TsyUSxEmS+rAqxpB+42g62GDCWiuQ==");
        String encode = URLEncoder.encode(encrypt);
        System.out.println(encode);
//        String privateKey = env.getProperty("sm2.private.key");
//        String decrypt = SM2Util.decrypt(encrypt, privateKey);
//        System.out.println(decrypt);
        //BPkXe1ylI7vbVSFmxch3T4E2NPpplg1r3b0qYebMYUJHvQEqcxIgQq5zPmMep7X8JMLPjWBVb3wp4FPFI7eCF6MuUcMERlHIkVYHNbfpbc234a9G%2F4lyHU46C8Xw6PAlNZxzU2StZKimQza3PISUrmg%3D
    }

    @Test
    void wordToPdfTest(){
        docService.wordToPdf();
    }

}
