package com.example.grouplist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

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

    private BottomSheetBehavior bottomSheetBehavior;

    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<ListItem> mList;

    private FloatingActionButton addItemButton;
    private ImageButton returnButton;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText newpopup_itemName, newpopup_itemLocation, newpopup_quantity;
    private Button newpopup_cancel, newpopup_save;

    private TextView listName;

    private ListObject currentList;

    private final DatabaseReference mListRef = FirebaseDatabase.getInstance().getReference("lists");
    private ArrayList<ListObject> mAllLists;
    private static final String TAG = "ListActivity";

    private boolean isNeedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setupDialog();
        initiate();

        readFromFirebase();

        View bottomSheet = findViewById( R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setPeekHeight(120);

        buildRecyclerView();
        configureButtons();
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
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void initiate(){
        mList = new ArrayList<>();
        mAllLists = new ArrayList<>();
        listName = findViewById(R.id.listName);
        isNeedIndex = true;
    }

    private void setupDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View popupView = getLayoutInflater().inflate(R.layout.popup, null);
        newpopup_itemName = popupView.findViewById(R.id.popup_itemname);
        newpopup_itemLocation = popupView.findViewById(R.id.popup_location);
        newpopup_quantity = popupView.findViewById(R.id.popup_quantity);
        newpopup_save = popupView.findViewById(R.id.save_button);
        newpopup_cancel = popupView.findViewById(R.id.cancel_button);

        dialogBuilder.setView(popupView);
        dialog = dialogBuilder.create();
    }

    public void createNewDialog(){
        dialog.show();
        newpopup_itemName.setText("");
        newpopup_itemLocation.setText("");
        newpopup_quantity.setText("");
        newpopup_save.setOnClickListener(new View.OnClickListener() {
            String locationText, itemText, quantityText;
            @Override
            public void onClick(View view) {
                if(newpopup_itemName.getText().toString().equals("")){
                    ActivityHelper.makeToast("Enter an item name", getApplicationContext());
                    return;
                }else{
                    itemText = newpopup_itemName.getText().toString();
                }
                if(newpopup_itemLocation.getText().toString().equals("")){
                    locationText = "None";
                }else{
                    locationText = newpopup_itemLocation.getText().toString();
                }
                if(newpopup_quantity.getText().toString().equals("")){
                    quantityText = "1";
                }else{
                    quantityText = newpopup_quantity.getText().toString();
                }
                addItem(itemText, locationText, quantityText);
                mAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        newpopup_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void createEditDialog(int position){
        dialog.show();
        ListItem currentItem = mList.get(position);
        newpopup_itemName.setText(currentItem.getItemName());
        newpopup_itemLocation.setText(currentItem.getLocationName());
        newpopup_quantity.setText(currentItem.getQuantity());
        newpopup_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(newpopup_itemName.getText().toString().equals("")){
                    ActivityHelper.makeToast("Enter an item name", getApplicationContext());
                }else{
                    changeValue(position, ValueType.ITEM_NAME, newpopup_itemName.getText().toString());
                }
                if(!(newpopup_itemLocation.getText().toString().equals(""))){
                    changeValue(position, ValueType.ITEM_LOCATION, newpopup_itemLocation.getText().toString());
                }
                if(!(newpopup_quantity.getText().toString().equals(""))){
                    changeValue(position, ValueType.ITEM_QUANTITY, newpopup_quantity.getText().toString());
                }
                dialog.dismiss();
            }
        });

        newpopup_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void configureButtons(){
        addItemButton = findViewById(R.id.addItemButton);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewDialog();
            }
        });

        returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mList.clear();
                mAdapter.notifyDataSetChanged();
                finish();
            }
        });
    }

    public void addItem(String itemName, String location, String quantity){
        if(currentList.getItems() == null){
            currentList.setItems(new ArrayList<>());
        }
        currentList.getItems().add(new ListItem(itemName, location, quantity));
        mListRef.child(currentList.getFireBaseID()).setValue(currentList);
    }

    public void removeItem(int position){
        currentList.getItems().remove(position);
        mListRef.child(currentList.getFireBaseID()).setValue(currentList);
        if (currentList.getItems().isEmpty()){
            mList.clear();
            mAdapter.notifyDataSetChanged();
        }
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

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecyclerAdapter(mList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                createEditDialog(position);
            }

            @Override
            public void onCompleteClick(int position) {
                removeItem(position);
            }
        });
    }
}