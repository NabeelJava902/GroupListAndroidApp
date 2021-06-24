package com.example.grouplist;

import java.util.ArrayList;

public class ListObject {

    private ArrayList<ListItem> items;
    private String listName;
    private ArrayList<String> members;
    private String id;

    public ListObject(){}

    public ListObject(ArrayList<ListItem> items, String listName, ArrayList<String> members) {
        this.items = items;
        this.listName = listName;
        this.members = members;
    }

    public ArrayList<ListItem> getItems() {
        return items;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
