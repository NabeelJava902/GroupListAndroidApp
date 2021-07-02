package com.example.grouplist.Objects;

public class ListItem extends Item{

    public ListItem(){}

    public ListItem(String itemName, String location, String quantity) {
        super(itemName, location, quantity);
        setType(Type.LIST_ITEM);
    }
}
