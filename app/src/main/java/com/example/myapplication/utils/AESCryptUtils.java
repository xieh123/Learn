package com.example.myapplication.utils;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by xieH on 2017/03/31 0024.
 */
public class AESCryptUtils {

    private Cipher cipher;

    private SecretKeySpec key;

    private AlgorithmParameterSpec spec;

    public static final String SEED_16_CHARACTER = "sdt20160531";

    private static AESCryptUtils instance;

    public static AESCryptUtils getInstance() {
        if (null == instance) {
            synchronized (AESCryptUtils.class) {
                if (null == instance) {
                    try {
                        instance = new AESCryptUtils();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }

    public AESCryptUtils() {
        try {
            // hash password with SHA-256 and crop the output to 128-bit for key
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            digest.update(SEED_16_CHARACTER.getBytes("UTF-8"));
            byte[] keyBytes = new byte[32];
            System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);
            cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            key = new SecretKeySpec(keyBytes, "AES");
            spec = getIV();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AlgorithmParameterSpec getIV() {
        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,};
        IvParameterSpec ivParameterSpec;
        ivParameterSpec = new IvParameterSpec(iv);
        return ivParameterSpec;
    }

    public String encrypt(String plainText) {
        String encryptedText = "";

        try {
            cipher.init(Cipher.ENCRYPT_MODE, key, spec);
            byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
            encryptedText = new String(Base64.encode(encrypted, Base64.DEFAULT), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return encryptedText;
    }

    public String decrypt(String cryptedText) {
        String decryptedText = "";
        try {
            cipher.init(Cipher.DECRYPT_MODE, key, spec);
            byte[] bytes = Base64.decode(cryptedText, Base64.DEFAULT);
            byte[] decrypted = cipher.doFinal(bytes);
            decryptedText = new String(decrypted, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return decryptedText;
    }

}
