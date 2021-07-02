package com.example.grouplist.Objects;

import java.util.ArrayList;

public class UserObject {

    private String email;
    private String fullName;
    private String firebaseID;
    private ArrayList<UserListObject> groups;

    private UserObject(){}

    public UserObject(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    public String getFirebaseID() {
        return firebaseID;
    }

    public void setFirebaseID(String firebaseID) {
        this.firebaseID = firebaseID;
    }

    public boolean hasJoinedGroup(String firebaseID){
        if(groups != null){
            for (UserListObject group : groups) {
                if (group.getFirebaseID().equals(firebaseID)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void addGroup(UserListObject list){
        if(groups == null){
            groups = new ArrayList<>();
        }
        groups.add(list);
    }

    public void removeGroup(UserListObject list){
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

    public ArrayList<UserListObject> getGroups() {
        if(groups == null){
            groups = new ArrayList<>();
        }
        return (groups == null) ? null:groups;
    }

    public void setGroups(ArrayList<UserListObject> groups) {
        this.groups = groups;
    }
}
