package com.atguigu.commonutils.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Project: guli-parent
 * @Describe: 描述
 * @Author: Jan
 * @Date: 2020-09-24 12:25
 */
public class MD5 {

    public static String encrypt(String strSrc) {

        try{
            char[] hexChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            byte[] bytes = strSrc.getBytes();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(bytes);
            bytes = md5.digest();
            int j = bytes.length;
            char[] chars = new char[j * 2];
            int k = 0;
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                chars[k++] = hexChars[b >>> 4 & 0xf];
                chars[k++] = hexChars[b & 0xf];
            }
            return new String(chars);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("MD5加密出错!! + " + e);
        }
    }

    public static void main(String[] args) {
        System.out.println(MD5.encrypt("11111111"));
    }
}
