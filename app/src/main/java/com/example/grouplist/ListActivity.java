package com.example.grouplist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        View bottomSheet = findViewById( R.id.bottom_sheet);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setPeekHeight(100);

        createList();
        buildRecyclerView();
        configureButtons();

    }

    public void createNewContactDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View popupView = getLayoutInflater().inflate(R.layout.popup, null);
        newpopup_itemName = (EditText) popupView.findViewById(R.id.popup_itemname);
        newpopup_itemLocation = (EditText) popupView.findViewById(R.id.popup_location);
        newpopup_quantity = (EditText) popupView.findViewById(R.id.popup_quantity);
        newpopup_save = (Button) popupView.findViewById(R.id.save_button);
        newpopup_cancel = (Button) popupView.findViewById(R.id.cancel_button);

        dialogBuilder.setView(popupView);
        dialog = dialogBuilder.create();
        dialog.show();

        newpopup_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(newpopup_itemName.getText().toString().equals("")){
                    Context context = getApplicationContext();
                    CharSequence text = "Enter an item name";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }else if(newpopup_itemLocation.getText().toString().equals("")){
                    addItem(newpopup_itemName.getText().toString(), "None");
                }else{
                    addItem(newpopup_itemName.getText().toString(), newpopup_itemLocation.getText().toString());
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
                createNewContactDialog();
            }
        });

        returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void addItem(String itemName, String location){
        mList.add(new ListItem(itemName, location));
        mAdapter.notifyDataSetChanged();
    }

    public void removeItem(int position){
        mList.remove(position);
        mAdapter.notifyDataSetChanged();
    }

    public void popup(int position){
        //TODO bring a popup
        mAdapter.notifyDataSetChanged();
    }

    private void createList(){
        mList = new ArrayList<>();
        mList.add(new ListItem("Toilet Paper", "Walmart"));
        mList.add(new ListItem("Cheese", "Walmart"));
        mList.add(new ListItem("Chicken", "Chicken"));
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
                popup(position);
            }

            @Override
            public void onCompleteClick(int position) {
                removeItem(position);
            }
        });
    }
}