package com.example.grouplist.Objects;

public class CompletedListItem extends Item{

    public CompletedListItem(){}

    public CompletedListItem(String itemName, String location, String quantity) {
        super(itemName, location, quantity);
        setType(Type.COMPLETED_LIST_ITEM);
    }
}
