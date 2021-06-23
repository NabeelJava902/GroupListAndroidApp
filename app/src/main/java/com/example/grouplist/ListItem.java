package com.example.grouplist;

import android.widget.ImageButton;

public class ListItem {

    private String itemName, location, quantity;

    public ListItem(String itemName, String location) {
        this.itemName = itemName;
        this.location = location;
        this.quantity = "1";
    }

    public void changeText1(String text){
        itemName = text;
    }

    public String getItemName() {
        return itemName;
    }

    public void setListName(String itemName) {
        this.itemName = itemName;
    }

    public String getLocationName() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

}
