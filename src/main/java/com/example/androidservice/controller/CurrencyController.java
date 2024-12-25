
package com.example.androidservice.controller;

import com.alibaba.fastjson2.JSON;
import com.example.androidservice.base.BaseConstant;
import com.example.androidservice.base.Result;
import com.example.androidservice.base.aspect.Log;
import com.example.androidservice.service.CurrencyService;
import com.example.androidservice.utils.RSAUtil;
import com.example.androidservice.utils.SM2Util;
import com.example.androidservice.utils.SM4Util;
import com.example.androidservice.utils.VerificationCodeUtil;
import com.example.androidservice.vo.VerificationCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 @author: laihuiming
 @date: 2023/12/13
 @version: 1.0
 @description: 通用控制类
 **/
@Api(value = "通用API",tags = {"通用API"})
@RestController
@RequestMapping("/currency")
public class CurrencyController {
    public static final String RSA= "RSA";
    public static final String SM2= "SM2";

    @Autowired
    private Environment env;
    @Autowired
    CurrencyService currencyService;
    /***
     * 流程:用RSA/SM2非对称加密方式，前端获取公钥，将随机数用公钥加密给后端，后端返回sid
     */

    @ResponseBody
    @RequestMapping(value = "/getPublicKey",method = RequestMethod.GET)
    public Result getPublicKey(){
        Result result = Result.success();
        Map<String,String> map = new HashMap<>();
        String encrypt = env.getProperty("domestic.encrypt");
        String publicKey;
        if (BaseConstant.Yes.equals(encrypt)){
            publicKey = env.getProperty("sm2.public.key");
        }else {
            publicKey = RSAUtil.getPublicKey();
        }
        map.put("publicKey",publicKey);
        result.setData(JSON.toJSON(map));
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/exchangeKey",method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "randomKey",value = "16位加密后的随机字符串",dataType = "string",required = true),
            @ApiImplicitParam(name = "encryptType",value = "randomKey加密规则类型:1-国密SM2,2-RSA",dataType = "string",required = true)
    })
    public Result exchangeKey(String randomKey,String encryptType) throws Exception {
        Result result = Result.success();
//        if (randomKey.length() != 16){
//            return Result.fail("randomKey长度应为16位");
//        }
        String privateKey;
        if ("1".equals(encryptType)){
            privateKey = env.getProperty("sm2.private.key");
            randomKey = SM2Util.decrypt(randomKey,privateKey);
        }else {
            randomKey = RSAUtil.decrypt(randomKey);
        }
        String sid = currencyService.getSidByRandomKey(randomKey);
        Map<String,String> map = new HashMap<>();
        map.put("sid",sid);
        result.setData(JSON.toJSON(map));
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/getVerificationCode",method = RequestMethod.POST)
    public void GetVerificationCode(String sid,HttpServletResponse response) throws IOException {
        VerificationCode verificationCode = VerificationCodeUtil.generateCaptcha();
        BufferedImage bufferedImage = verificationCode.getBufferedImage();
        String code = verificationCode.getCode();
        currencyService.saveVerificationCode(sid,code);
        response.setHeader("Content-disposition",
                String.format("attachment; filename=\"%s\"", "verifyCode.jpg"));
        response.setContentType("image/jpeg;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        ImageIO.write(bufferedImage, "JPEG", response.getOutputStream());

    }

}
