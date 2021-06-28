package com.example.grouplist;

import android.widget.EditText;

import java.util.ArrayList;

public class DialogHelper {

    private static ArrayList<Boolean> emptyMap = new ArrayList<>();

    public static ArrayList<Boolean> getEmpty(EditText text1, EditText text2, EditText text3){
        ArrayList<EditText> texts = new ArrayList<>();
        texts.add(text1);
        texts.add(text2);
        texts.add(text3);
        for(int i=0; i<texts.size(); i++){
            if(texts.get(i).getText().toString().equals("")){
                emptyMap.add(true);
            }else{
                emptyMap.add(false);
            }
        }
        return emptyMap;
    }
}
