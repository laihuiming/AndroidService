
package com.example.androidservice.utils;

import com.example.androidservice.vo.VerificationCode;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
/**
 @author: laihuiming
 @date: 2024/2/26
 @version: 1.0
 @description: 验证码工具类
 **/
public class VerificationCodeUtil {
    private static final int WIDTH = 120;
    private static final int HEIGHT = 40;
    private static final int CODE_LENGTH = 4;
    private static final String CODE_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static VerificationCode generateCaptcha() {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        Random random = new Random();

        // 设置背景色
        g.setColor(new Color(255, 255, 255));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        //实际验证码
        String verificationCode = "";
        // 生成随机验证码
        StringBuilder captchaCode = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(CODE_SET.length());
            verificationCode = verificationCode + index;
            char code = CODE_SET.charAt(index);
            captchaCode.append(code);
            g.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString(String.valueOf(code), 10 + i * 30, 25);
        }

        // 添加干扰线
        for (int i = 0; i < 5; i++) {
            g.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            int x1 = random.nextInt(WIDTH);
            int y1 = random.nextInt(HEIGHT);
            int x2 = random.nextInt(WIDTH);
            int y2 = random.nextInt(HEIGHT);
            g.drawLine(x1, y1, x2, y2);
        }

        g.dispose();
        VerificationCode verifier = new VerificationCode();
        verifier.setBufferedImage(image);
        verifier.setCode(verificationCode);
        return verifier;
    }
}
