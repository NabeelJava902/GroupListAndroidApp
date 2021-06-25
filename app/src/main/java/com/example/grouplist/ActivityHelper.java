package com.example.grouplist;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityHelper {

    public static boolean verifyPasscode(String passcode, ArrayList<ListObject> listObjects){//TODO 6
        for(ListObject object : listObjects){
            if(object.getRawPasscode().equals(passcode)){
                return true;
            }
        }
        return false;
    }

    public static int findList(String passcode, ArrayList<ListObject> listObjects){//TODO 6
        int listIndex = 0;
        for(int i=0; i<listObjects.size(); i++){
            if(listObjects.get(i).getRawPasscode().equals(passcode)){
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
