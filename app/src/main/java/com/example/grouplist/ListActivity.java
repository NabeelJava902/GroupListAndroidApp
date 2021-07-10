package com.example.grouplist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.grouplist.databinding.ActivityListBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;

    public static final DatabaseReference mListRef = FirebaseDatabase.getInstance().getReference("lists");
    private static final String TAG = "ListActivity";

    @SuppressLint("StaticFieldLeak")
    public static TextView listName;

    @SuppressLint("StaticFieldLeak")
    public static ListManager listManager;

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.grouplist.databinding.ActivityListBinding binding = ActivityListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarTemporary.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(this);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_temporary);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        initiate();

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
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_temporary);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        int id = item.getItemId();
        item.setChecked(true);
        drawer.closeDrawers();
        switch(id){
            case R.id.nav_delete:
                listManager.getPopup().createDeleteDialog(this);
                break;
            case R.id.nav_leave:
                listManager.getPopup().createLeaveDialog(this);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}