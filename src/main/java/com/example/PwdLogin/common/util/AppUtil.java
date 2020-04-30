package com.example.PwdLogin.common.util;

import java.util.Random;

public class AppUtil {
    public static void main(String[] args) {
        System.out.println(generaterCode());
    }
    public static String generaterCode(){
        String ZiMu = "1234567890";
        String result = "";
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(ZiMu.length());
            char c = ZiMu.charAt(index);
            result += c;
        }
        return result;
    }
}
