package com.example.grouplist.Objects;

import android.widget.ImageButton;

public class ListItem{

    private String mItemName, mLocation, mQuantity;

    public ListItem(){}

    public ListItem(String itemName, String location, String quantity) {
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

}
