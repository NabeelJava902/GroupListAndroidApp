package com.example.grouplist.Objects;

import java.util.ArrayList;

public class ListObject{

    private ArrayList<ListItem> items;
    private ArrayList<CompletedListItem> completedListItems = new ArrayList<>();
    private String listName;
    private ArrayList<String> members;
    private String encryptedPasscode;
    private String fireBaseID;

    public ListObject(){}

    public ListObject(ArrayList<ListItem> items, String listName, ArrayList<String> members, ArrayList<CompletedListItem> completedListItems, String encryptedPasscode) {
        this.items = items;
        this.listName = listName;
        this.members = members;
        this.completedListItems = completedListItems;
        this.encryptedPasscode = encryptedPasscode;
    }

    public void clear(){
        items = null;
        completedListItems = null;
        members = null;
        listName = null;
        encryptedPasscode = null;
        fireBaseID = null;
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

    public ArrayList<CompletedListItem> getCompletedListItems() {
        return completedListItems;
    }

    public void setCompletedListItems(ArrayList<CompletedListItem> completedListItems) {
        this.completedListItems = completedListItems;
    }
}
