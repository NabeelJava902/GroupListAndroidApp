package com.example.grouplist.Auth;

public class AuthDecrypt {

    public static String decrypt(String encryptedString){
        String decryptedString;
        char[] encryptedChars = encryptedString.toCharArray();
        for(int i=0; i<encryptedString.length(); i++){
            int value = encryptedString.length() + AuthEncrypt.key + i;
            encryptedChars[i] -= value;
        }
        decryptedString = String.valueOf(encryptedChars);
        return decryptedString;
    }
}
