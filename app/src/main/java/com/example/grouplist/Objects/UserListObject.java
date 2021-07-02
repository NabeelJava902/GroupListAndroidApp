package com.example.grouplist.Objects;

public class UserListObject {

    private String listName;
    private String encryptedPasscode;
    private String firebaseID;

    public UserListObject(){}

    public UserListObject(String listName, String encryptedPasscode, String firebaseID) {
        this.listName = listName;
        this.encryptedPasscode = encryptedPasscode;
        this.firebaseID = firebaseID;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getEncryptedPasscode() {
        return encryptedPasscode;
    }

    public void setEncryptedPasscode(String encryptedPasscode) {
        this.encryptedPasscode = encryptedPasscode;
    }

    public String getFirebaseID() {
        return firebaseID;
    }

    public void setFirebaseID(String firebaseID) {
        this.firebaseID = firebaseID;
    }
}
