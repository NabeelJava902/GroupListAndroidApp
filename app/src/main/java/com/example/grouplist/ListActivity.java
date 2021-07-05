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

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ListActivity extends AppCompatActivity {

    public static final DatabaseReference mListRef = FirebaseDatabase.getInstance().getReference("lists");
    private static final String TAG = "ListActivity";

    @SuppressLint("StaticFieldLeak")
    public static TextView listName;

    @SuppressLint("StaticFieldLeak")
    public static ListManager listManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        initiate();
        listManager.initiate();

        readFromFirebase();

        configureBottomSheet();

        buildRecyclerView();
        configureButtons();
    }

    @SuppressLint("ResourceType")
    private void initiate(){
        @SuppressLint("InflateParams") View popupView = getLayoutInflater().inflate(R.layout.popup, null);
        @SuppressLint("InflateParams") View assureView = getLayoutInflater().inflate(R.layout.assure_dialog, null);
        listName = findViewById(R.id.listName);
        listManager = new ListManager(this, popupView, assureView);
    }

    private void configureBottomSheet(){
        @SuppressLint("ResourceType") LinearLayout linearLayout = findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    private void readFromFirebase(){
        mListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listManager.update(snapshot.getChildren());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    @SuppressLint({"ResourceType", "CutPasteId"})
    public void buildRecyclerView() {
        listManager.setItemListBuilder(new RecyclerViewBuilder(findViewById(R.id.recyclerView),
                new RecyclerAdapter(listManager.getList(), null, null), this));

        listManager.getItemListBuilder().getAdapter().setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                listManager.getPopup().createEditDialog(position, listManager.getList());
            }

            @Override
            public void onCompleteClick(int position) {
                listManager.removeItem(position);
            }
        });

        listManager.setCompletedListBuilder(new RecyclerViewBuilder(findViewById(R.id.bottom_sheet_recycler),
                new RecyclerAdapter(null, null, listManager.getCompletedList()), this));

        listManager.getCompletedListBuilder().getAdapter().setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //do nothing
            }

            @Override
            public void onCompleteClick(int position) {
                listManager.getPopup().createAssureDialog(position);
            }
        });
    }

    private void configureButtons(){
        FloatingActionButton addItemButton = findViewById(R.id.addItemButton);
        addItemButton.setOnClickListener(view -> listManager.getPopup().createNewDialog());

        ImageButton returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(view -> {
            listManager.clearList();
            listManager.notifyAdapter();
            finish();
        });
    }
}