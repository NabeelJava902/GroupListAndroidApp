package com.example.grouplist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import com.example.grouplist.Objects.CompletedListItem;
import com.example.grouplist.Objects.ListItem;
import com.example.grouplist.Objects.ListObject;
import com.google.firebase.database.DataSnapshot;

import static com.example.grouplist.ListActivity.mListRef;
import static com.example.grouplist.DefaultScreenActivity.mUserRef;
import static com.example.grouplist.ActivityHelper.iterateRefOBJ;
import static com.example.grouplist.DefaultScreenActivity.currentUser;

import java.util.ArrayList;

public class ListManager {

    private RecyclerViewBuilder itemListBuilder;
    private RecyclerViewBuilder completedListBuilder;

    private ArrayList<ListItem> mList;
    public static ArrayList<CompletedListItem> mCompletedList;

    private Popup popup;

    private ArrayList<ListObject> mAllLists;

    private boolean isNeedIndex;

    public static ListObject currentList;

    private final View popupView, assureView;
    private final Context context;

    private static final int ITEM_CAP = 10;

    public ListManager(Context context, View popupView, View assureView){
        this.popupView = popupView;
        this.assureView = assureView;
        this.context = context;
        initiate();
    }

    public void update(Iterable<DataSnapshot> dataSnapshots){
        final int[] currentListIndex = new int[1];
        for(DataSnapshot dataSnapshot : dataSnapshots){
            mAllLists.add(dataSnapshot.getValue(ListObject.class));
        }
        if(isNeedIndex) {
            for (int i = 0; i < mAllLists.size(); i++) {
                if (mAllLists.get(i).getEncryptedPasscode().equals(DefaultScreenActivity.encryptedPasscode)) {
                    currentListIndex[0] = i;
                    isNeedIndex = false;
                }
            }
        }
        currentList = mAllLists.get(currentListIndex[0]);
        ListActivity.listName.setText(currentList.getListName());
        if(currentList.getItems() != null){
            mList.clear();
            mList.addAll(currentList.getItems());
        }
        if(currentList.getCompletedListItems() != null){
            mCompletedList.clear();
            mCompletedList.addAll(currentList.getCompletedListItems());
        }
        notifyAdapter();
    }

    public void notifyAdapter(){
        itemListBuilder.getAdapter().notifyDataSetChanged();
        completedListBuilder.getAdapter().notifyDataSetChanged();
    }

    @SuppressLint("ResourceType")
    public void initiate(){
        mList = new ArrayList<>();
        mAllLists = new ArrayList<>();
        mCompletedList = new ArrayList<>();
        isNeedIndex = true;
        popup = new Popup(context, popupView, assureView);
    }

    public void addItem(String itemName, String location, String quantity){
        if(currentList.getItems() == null){
            currentList.setItems(new ArrayList<>());
        }
        currentList.getItems().add(new ListItem(itemName, location, quantity));
        mListRef.child(currentList.getFireBaseID()).setValue(currentList);
    }

    public void removeItem(int position){
        ListItem currentItem = currentList.getItems().get(position);
        addCompletedItem(currentItem);
        currentList.getItems().remove(position);
        mListRef.child(currentList.getFireBaseID()).setValue(currentList);
        if (currentList.getItems().isEmpty()){
            mList.clear();
            notifyAdapter();
        }
    }

    private void addCompletedItem(ListItem currentItem){
        if(mCompletedList.size() >= ITEM_CAP){
            mCompletedList.remove(mCompletedList.size()-1);
            currentList.getCompletedListItems().remove(currentList.getCompletedListItems().size()-1);
        }
        mCompletedList.add(0, new CompletedListItem(currentItem.getItemName(), currentItem.getLocationName(), currentItem.getQuantity()));
        currentList.getCompletedListItems().add(0, new CompletedListItem(currentItem.getItemName(), currentItem.getLocationName(), currentItem.getQuantity()));
    }

    public void changeValue(int position, ValueType valueType, String string){
        switch (valueType){
            case ITEM_NAME: currentList.getItems().get(position).setItemName(string);
                break;
            case ITEM_LOCATION: currentList.getItems().get(position).setLocation(string);
                break;
            case ITEM_QUANTITY: currentList.getItems().get(position).setQuantity(string);
                break;
        }
        mListRef.child(currentList.getFireBaseID()).setValue(currentList);
    }

    public void deleteList(String id){
        currentList.clear();
        mListRef.child(id).setValue(currentList);
        int position = iterateRefOBJ(currentUser.getGroups(), id);
        currentUser.removeGroup(position);
        mUserRef.child(currentUser.getFirebaseID()).setValue(currentUser);
    }

    public void removeUser(String id){
        int position = iterateRefOBJ(currentUser.getGroups(), id);
        currentUser.removeGroup(position);
        mUserRef.child(currentUser.getFirebaseID()).setValue(currentUser);
    }

    public Popup getPopup(){
        return popup;
    }

    public ArrayList<CompletedListItem> getCompletedList() {
        return mCompletedList;
    }

    public ListObject getCurrentList() {
        return currentList;
    }

    public void setItemListBuilder(RecyclerViewBuilder itemListBuilder) {
        this.itemListBuilder = itemListBuilder;
    }

    public RecyclerViewBuilder getItemListBuilder() {
        return itemListBuilder;
    }

    public void setCompletedListBuilder(RecyclerViewBuilder completedListBuilder) {
        this.completedListBuilder = completedListBuilder;
    }

    public RecyclerViewBuilder getCompletedListBuilder() {
        return completedListBuilder;
    }

    public ArrayList<ListItem> getList() {
        return mList;
    }
}