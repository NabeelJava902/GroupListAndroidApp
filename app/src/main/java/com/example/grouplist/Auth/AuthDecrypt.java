package com.example.grouplist.Auth;

import android.annotation.SuppressLint;

import javax.crypto.Cipher;

public class AuthDecrypt {

    public static byte[] decrypt(byte[] encrypted) {
        try {
            @SuppressLint("GetInstance") Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, AuthEncrypt.aesKey);
            return cipher.doFinal(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
