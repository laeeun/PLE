package com.springmvc.util;

import java.util.Random;

public class CaptchaUtil {
    public static String generateCaptcha(int length) {
        String chars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789"; // 헷갈리는 문자 제거
        StringBuilder captcha = new StringBuilder();
        Random rnd = new Random();

        for (int i = 0; i < length; i++) {
            captcha.append(chars.charAt(rnd.nextInt(chars.length())));
        }

        return captcha.toString();
    }
}
