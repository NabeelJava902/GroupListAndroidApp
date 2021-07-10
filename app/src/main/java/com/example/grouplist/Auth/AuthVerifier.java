package com.example.grouplist.Auth;

import android.os.Build;

import com.example.grouplist.ActivityHelper;
import com.example.grouplist.Objects.ListObject;

import java.util.ArrayList;

public class AuthVerifier {

    private static String passcode;
    private static ArrayList<ListObject> mAllLists;
    private static final int MINIMUM_PASSCODE_LENGTH = 6;

    public static void setVariables(String passcode, ArrayList<ListObject> mAllLists){
        AuthVerifier.passcode = passcode;
        AuthVerifier.mAllLists = mAllLists;
    }

    public static AuthEnum getStatus(){
        if(passcode.length() < MINIMUM_PASSCODE_LENGTH && passcode.length() > 0){
            return AuthEnum.PASSCODE_TOO_SHORT;
        }else if(passcode.equals("")){
            return AuthEnum.NO_PASSCODE_ENTERED;
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if(ActivityHelper.verifyPasscode(passcode, mAllLists)){
                return AuthEnum.PASSCODE_EXISTS;
            }
        }
        return AuthEnum.PASSCODE_WORKS;
    }

    public static int getMinimumPasscodeLength() {
        return MINIMUM_PASSCODE_LENGTH;
    }
}
