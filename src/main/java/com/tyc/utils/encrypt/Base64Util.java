package com.tyc.utils.encrypt;

import java.util.Base64;

/**
 * 类描述
 *
 * @author tyc
 * @version 1.0
 * @date 2022-06-13 11:02:56
 */
public class Base64Util {
    public static String decode(byte[] bytes) {
        return new String(Base64.getDecoder().decode(bytes));
    }

    public static String encode(byte[] bytes) {
        return new String(Base64.getEncoder().encode(bytes));
    }

    public static String decode(String str) {
        return new String(Base64.getDecoder().decode(str.getBytes()));
    }

    public static String encode(String str) {
        return new String(Base64.getEncoder().encode(str.getBytes()));
    }

    public static void main(String[] args) {
        String string = "testhdubhauhnudhyausda";

        System.out.println(encode(string.getBytes()));

        System.out.println(decode(encode(string.getBytes()).getBytes()));
    }
}
