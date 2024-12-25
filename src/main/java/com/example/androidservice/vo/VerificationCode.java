
package com.example.androidservice.vo;

import java.awt.image.BufferedImage;

/**
 @author: laihuiming
 @date: 2024/2/29
 @version: 1.0
 @description: 验证码
 **/
public class VerificationCode {
    BufferedImage bufferedImage;
    String code;

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
