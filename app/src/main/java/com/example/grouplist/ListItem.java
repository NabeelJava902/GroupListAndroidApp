package com.example.grouplist;

public class ListItem {

    private String itemName, location, quantity;

    public ListItem(String itemName, String location) {
        this.itemName = itemName;
        this.location = location;
        this.quantity = "1";
    }

    public String getListName() {
        return itemName;
    }

    public void setListName(String itemName) {
        this.itemName = itemName;
    }

    public String getLocation() {
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
