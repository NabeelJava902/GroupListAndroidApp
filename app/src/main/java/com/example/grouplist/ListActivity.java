package com.example.grouplist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.grouplist.Objects.CompletedListItem;
import com.example.grouplist.Objects.ListItem;
import com.example.grouplist.Objects.ListObject;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private RecyclerViewBuilder itemListBuilder;
    private RecyclerViewBuilder completedListBuilder;

    private ArrayList<ListItem> mList;
    public static ArrayList<CompletedListItem> mCompletedList;

    private FloatingActionButton addItemButton;
    private ImageButton returnButton;

    private Popup popup;

    private TextView listName;
    private static ListObject currentList;

    private static final DatabaseReference mListRef = FirebaseDatabase.getInstance().getReference("lists");
    private ArrayList<ListObject> mAllLists;
    private static final String TAG = "ListActivity";

    private boolean isNeedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        initiate();

        readFromFirebase();

        configureBottomSheet();

        buildRecyclerView();
        configureButtons();
    }

    private void configureBottomSheet(){
        @SuppressLint("ResourceType") LinearLayout linearLayout = findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    private void readFromFirebase(){
        final int[] currentListIndex = new int[1];
        mListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
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
                listName.setText(currentList.getListName());
                if(currentList.getItems() != null){
                    mList.clear();
                    mList.addAll(currentList.getItems());
                }
                notifyAdapter();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void notifyAdapter(){
        itemListBuilder.getAdapter().notifyDataSetChanged();
        completedListBuilder.getAdapter().notifyDataSetChanged();
    }

    @SuppressLint("ResourceType")
    private void initiate(){
        mList = new ArrayList<>();
        mAllLists = new ArrayList<>();
        mCompletedList = new ArrayList<>();
        listName = findViewById(R.id.listName);
        isNeedIndex = true;
        popup = new Popup(this, getLayoutInflater().inflate(R.layout.popup, null),
                getLayoutInflater().inflate(R.layout.assure_dialog, null));
    }

    private void configureButtons(){
        addItemButton = findViewById(R.id.addItemButton);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.createNewDialog();
            }
        });

        returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mList.clear();
                notifyAdapter();
                finish();
            }
        });
    }

    public static void addItem(String itemName, String location, String quantity){
        if(currentList.getItems() == null){
            currentList.setItems(new ArrayList<>());
        }
        currentList.getItems().add(new ListItem(itemName, location, quantity));
        mListRef.child(currentList.getFireBaseID()).setValue(currentList);
    }

    public void removeItem(int position){
        ListItem currentItem = currentList.getItems().get(position);
        mCompletedList.add(new CompletedListItem(currentItem.getItemName(), currentItem.getLocationName(), currentItem.getQuantity()));
        currentList.getItems().remove(position);
        mListRef.child(currentList.getFireBaseID()).setValue(currentList);
        if (currentList.getItems().isEmpty()){
            mList.clear();
            notifyAdapter();
        }
    }

    public static void changeValue(int position, ValueType valueType, String string){
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

    @SuppressLint({"ResourceType", "CutPasteId"})
    private void buildRecyclerView() {
        itemListBuilder = new RecyclerViewBuilder(findViewById(R.id.recyclerView),
                new RecyclerAdapter(mList, null, null), this);

        itemListBuilder.getAdapter().setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                popup.createEditDialog(position, mList);
            }

            @Override
            public void onCompleteClick(int position) {
                removeItem(position);
            }
        });

        completedListBuilder = new RecyclerViewBuilder(findViewById(R.id.bottom_sheet_recycler),
                new RecyclerAdapter(null, null, mCompletedList), this);

        completedListBuilder.getAdapter().setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //do nothing
            }

            @Override
            public void onCompleteClick(int position) {
                popup.createAssureDialog(position);
            }
        });
    }
}