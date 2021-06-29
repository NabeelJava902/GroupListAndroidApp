package com.example.grouplist;

import android.content.Context;
import android.widget.Toast;

import com.example.grouplist.Auth.AuthDecrypt;
import com.example.grouplist.Objects.ListObject;
import com.example.grouplist.Objects.UserObject;

import java.util.ArrayList;

public class ActivityHelper {

    public static boolean verifyPasscode(String passcode, ArrayList<ListObject> listObjects){
        for(ListObject object : listObjects){
            String decryptedPasscode = AuthDecrypt.decrypt(object.getEncryptedPasscode());
            if(decryptedPasscode.equals(passcode)){
                return true;
            }
        }
        return false;
    }

    public static boolean verifyEmail(String email, ArrayList<UserObject> userObjects){
        for(UserObject userObject : userObjects){
            if(userObject.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    public static int findList(String passcode, ArrayList<ListObject> listObjects){
        int listIndex = 0;
        for(int i=0; i<listObjects.size(); i++){
            String decryptedPasscode = AuthDecrypt.decrypt(listObjects.get(i).getEncryptedPasscode());
            if(decryptedPasscode.equals(passcode)){
                listIndex = i;
            }
        }
        return listIndex;
    }

    public static void makeToast(String msg, Context context){
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, msg, duration);
        toast.show();
    }
}
