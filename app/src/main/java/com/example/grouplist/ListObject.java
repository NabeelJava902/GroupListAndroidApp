package com.example.grouplist;

import java.util.ArrayList;

public class ListObject {

    private ArrayList<ListItem> items;
    private String listName;
    private ArrayList<String> members;
    private String rawPasscode;
    private String fireBaseID;

    public ListObject(){}

    public ListObject(ArrayList<ListItem> items, String listName, ArrayList<String> members, String rawPasscode) {
        this.items = items;
        this.listName = listName;
        this.members = members;
        this.rawPasscode = rawPasscode;
    }

    public ArrayList<ListItem> getItems() {
        return items;
    }

    public String getRawPasscode() {
        return rawPasscode;
    }

    public void setRawPasscode(String rawPasscode) {
        this.rawPasscode = rawPasscode;
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
}
