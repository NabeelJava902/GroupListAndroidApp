package com.example.grouplist.Auth;

import com.example.grouplist.Objects.ListObject;

import java.util.ArrayList;

public class AuthConditional {

    public static void setVariables(String passcode, ArrayList<ListObject> mAllLists){
        AuthVerifier.setVariables(passcode, mAllLists);
    }

    public static String getMessage(){
        AuthEnum status = AuthVerifier.getStatus();
        switch(status){
            case PASSCODE_EXISTS:
                return "Passcode already exists";
            case PASSCODE_TOO_SHORT:
                return "Passcode must be " + AuthVerifier.getMinimumPasscodeLength() + " characters or longer";
            case NO_PASSCODE_ENTERED:
                return "Enter a passcode";
            case PASSCODE_WORKS:
                return "List created";
        }
        return null;
    }

    public static Boolean doesVerify(){
        AuthEnum status = AuthVerifier.getStatus();
        return status == AuthEnum.PASSCODE_WORKS;
    }
}
