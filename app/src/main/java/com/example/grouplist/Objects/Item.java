package com.example.grouplist.Objects;

public abstract class Item {

    private String mItemName, mLocation, mQuantity;
    protected Type type;

    public Item(){}

    public Item(String itemName, String location, String quantity) {
        mItemName = itemName;
        mLocation = location;
        mQuantity = quantity;
    }

    public String getItemName() {
        return mItemName;
    }

    public void setItemName(String itemName) {
        mItemName = itemName;
    }

    public String getLocationName() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public String getQuantity() {
        return mQuantity;
    }

    public void setQuantity(String quantity) {
        mQuantity = quantity;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
