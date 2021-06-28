package com.example.grouplist;

import java.util.ArrayList;

public class ListObject {

    private ArrayList<ListItem> items;
    private String listName;
    private ArrayList<String> members;
    private String encryptedPasscode;
    private String fireBaseID;

    public ListObject(){}

    public ListObject(ArrayList<ListItem> items, String listName, ArrayList<String> members, String encryptedPasscode) {
        this.items = items;
        this.listName = listName;
        this.members = members;
        this.encryptedPasscode = encryptedPasscode;
    }

    public ArrayList<ListItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<ListItem> items) {
        this.items = items;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }

    public String getFireBaseID() {
        return fireBaseID;
    }

    public void setFireBaseID(String fireBaseID) {
        this.fireBaseID = fireBaseID;
    }

    public String getEncryptedPasscode() {
        return encryptedPasscode;
    }

    public void setEncryptedPasscode(String encryptedPasscode) {
        this.encryptedPasscode = encryptedPasscode;
    }
}
