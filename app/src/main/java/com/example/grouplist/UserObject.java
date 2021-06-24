package com.example.grouplist;

import java.util.ArrayList;

public class UserObject {

    private String firstName;
    private String lastName;
    private ArrayList<ListObject> groups;

    private UserObject(){}

    public UserObject(String firstName, String lastName, ArrayList<ListObject> groups) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.groups = groups;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ArrayList<ListObject> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<ListObject> groups) {
        this.groups = groups;
    }
}
