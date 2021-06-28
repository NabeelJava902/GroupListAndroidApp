package com.example.grouplist.Auth;

public class AuthEncrypt {

    public static int key = 2;

    public static String encrypt(String string){
        char[] hashedChars1 = string.toCharArray();

        for(int i=0; i<string.length(); i++){
            int value = string.length() + key + i;
            hashedChars1[i] += value;
        }
        return String.valueOf(hashedChars1);
    }
}