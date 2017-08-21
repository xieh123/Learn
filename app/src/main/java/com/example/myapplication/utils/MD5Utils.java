package com.example.myapplication.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by xieH on 2017/6/27 0027.
 */
public class MD5Utils {

    /**
     * MD5 加密
     *
     * @param data 明文
     * @return
     */
    public static String encrypt(String data) {
        MessageDigest mDigest = null;
        StringBuffer buffer = new StringBuffer();
        try {
            mDigest = MessageDigest.getInstance("md5");

            byte[] bytes = mDigest.digest(data.getBytes("UTF-8"));
            for (byte b : bytes) {
                int number = b & 0xff;
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return buffer.toString();
    }

}
