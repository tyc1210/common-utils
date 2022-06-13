package com.tyc.utils.encrypt;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
/**
 * @author tyc
 * @version 1.0
 * @description RSA加解密工具
 * 在线生产公私钥网站：http://web.chacuo.net/netrsakeypair
 * @date 2021-05-20 16:05:45
 */
public class RSA {
    private final static String RSA = "RSA";
    private final static String PUBKEY = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAOUNOBxUAAVFq9atZZA3snFY/x8LiPhf\n" +
            "Akz5V2k/JlD3HKH39TG+lFNhspP2VSTOI9TwyMntqtQrGapmfZw2T68CAwEAAQ==";

    private final static String PRIKEY = "MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEA5Q04HFQABUWr1q1l\n" +
            "kDeycVj/HwuI+F8CTPlXaT8mUPccoff1Mb6UU2Gyk/ZVJM4j1PDIye2q1CsZqmZ9\n" +
            "nDZPrwIDAQABAkBHP79ugGqpOSzk0HdBTzIAtDBX1dy7dcJfc0hPgsdGc6EaYlfJ\n" +
            "UpBlO4YCO71ZrAarF8aOQAay+kz1VCy/y0tRAiEA9HMYu9k2+oBdq4wSNjyFYKGw\n" +
            "pBKu6/Ezs/vpu3GusJMCIQDv393D4sjt4z3AfC2QIminlZ8b0x2hvYHIfV9sP0ZB\n" +
            "9QIgbG/iOPJSgS0QYFjyezy9rfAL+7yN+/wzg1Psi0oSxCECIQCQTuq4hQACdXpa\n" +
            "CZrToo+5ej79W1XxTvP8LMiyc6KN3QIhAMkkjS4RdDsetGoaJvDBMnoZCaP6sUNT\n" +
            "StYv2npwti/6";

    public static String encryptByKey(String text, PublicKey pubRSA)
            throws Exception {
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.ENCRYPT_MODE, pubRSA);
        byte[] hexByte = cipher.doFinal(text.getBytes());
        return new BASE64Encoder().encode(hexByte);
    }

    public static String encrypt(String text, String pubKeystr) throws Exception {
        byte[] key = new BASE64Decoder().decodeBuffer(pubKeystr);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
        return encryptByKey(text, publicKey);
    }

    public static byte[] decrypt(String data, PrivateKey rk) {
        try {
            return decryptByKey(new BASE64Decoder().decodeBuffer(data), rk);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decryptByKey(byte[] src, PrivateKey rk) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.DECRYPT_MODE, rk);
        return cipher.doFinal(src);
    }

    public static String decrypt(String cipher, String privateKeyString) throws Exception {
        byte[] key = new BASE64Decoder().decodeBuffer(privateKeyString);
        PKCS8EncodedKeySpec x509KeySpec = new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(x509KeySpec);
        byte[] decode = decrypt(cipher, privateKey);
        return new String(decode);
    }

    public static void main(String args[]) {
        try {
            String cipherText = com.tyc.utils.encrypt.RSA.encrypt("123456", PUBKEY);
            System.out.println(cipherText);
            System.out.println(com.tyc.utils.encrypt.RSA.decrypt(cipherText, PRIKEY));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
