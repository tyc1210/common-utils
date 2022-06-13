package com.tyc.utils.encrypt;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.DigestUtils;
/**
 * Bcrypt加密内部自己实现了随机加盐处理
 * 使用MD5加密，每次加密后的密文其实都是一样的
 * Bcrypt生成的密文是60位的。而MD5的是32位的。
 * 使用BCrypt 主要是能实现每次加密的值都是不一样的。
 *
 * @author tyc
 * @version 1.0
 * @date 2021-06-30 17:03:36
 */
public class MD5BCrypt {
    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static String md5(String str){
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    /**
     *
     * @param str 未经过MD5加密
     * @param md5 经过MD5加密
     * @return
     */
    public static boolean md5(String str, String md5){
        return DigestUtils.md5DigestAsHex(str.getBytes()).equals(md5);
    }

    public static String  bCrypt(String str){
        return bCryptPasswordEncoder.encode(str);
    }

    /**
     *
     * @param str 原字符
     * @param bCrypt 机密后字符
     * @return
     */
    public static boolean bCrypt (String str, String bCrypt){
        return bCryptPasswordEncoder.matches(str,bCrypt);
    }

    public static void main(String[] args) {
        String password = "123456";
        String md5 = md5(password);
        System.out.println("md5加密结果：" + md5 + " 比对：" + md5(password,md5));
        String md51 = md5(password);
        System.out.println("md5加密结果：" + md51 + " 比对：" + md5(password,md51));

        String b = bCrypt(password);
        System.out.println("加密结果：" + b + " 比对：" + bCrypt(password,b));
        String b1 = bCrypt(password);
        System.out.println("加密结果：" + b1 + " 比对：" + bCrypt(password,b1));
    }
}

