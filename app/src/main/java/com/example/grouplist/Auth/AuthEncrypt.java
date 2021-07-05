package com.example.grouplist.Auth;

import android.annotation.SuppressLint;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AuthEncrypt {

    private static final String key = "Bar12345Bar12345";
    public static Key aesKey = new SecretKeySpec(key.getBytes(), "AES");

    public static byte[] encrypt(String string)
    {
        try
        {
            // 128 bit key
            // Create key and cipher
            Cipher cipher = Cipher.getInstance("AES");
            // encrypt the text
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);

            return cipher.doFinal(string.getBytes());
            // decrypt the text
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}