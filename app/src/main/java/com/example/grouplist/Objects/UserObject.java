package com.example.grouplist.Objects;

import java.util.ArrayList;

public class UserObject {

    private String email;
    private String fullName;
    private ArrayList<ListObject> groups;

    private UserObject(){}

    public UserObject(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    public void addGroup(ListObject list){
        groups.add(list);
    }

    public void removeGroup(ListObject list){
        groups.remove(list);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public ArrayList<ListObject> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<ListObject> groups) {
        this.groups = groups;
    }
}
