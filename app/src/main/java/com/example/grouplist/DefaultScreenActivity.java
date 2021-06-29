package com.example.grouplist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.grouplist.Auth.AuthConditional;
import com.example.grouplist.Auth.AuthEncrypt;
import com.example.grouplist.Objects.ListItem;
import com.example.grouplist.Objects.ListObject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DefaultScreenActivity extends AppCompatActivity {

    private ImageButton enterIDButton;
    private Button enterNewListButton;
    private TextView enterListPasscodeText;
    private ScrollView preexistingListsView;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText newpopup_listName, newpopup_passcode;
    private Button newpopup_cancel, newpopup_save;

    public static String encryptedPasscode;//this string must always be updated before changing activity

    private DatabaseReference mRef;

    private ArrayList<ListItem> items;
    private String listName;
    private ArrayList<String> members;

    private ArrayList<ListObject> mAllLists;

    private final static String TAG = "DefaultScreenActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRef = FirebaseDatabase.getInstance().getReference("lists");

        enterIDButton = findViewById(R.id.EnterListIDButton);
        enterNewListButton = findViewById(R.id.CreateNewListButton);
        enterListPasscodeText = findViewById(R.id.listPasscodeText);
        preexistingListsView = findViewById(R.id.ListsView);
        newpopup_passcode = findViewById(R.id.passcode);

        mAllLists = new ArrayList<>();

        readFromFirebase();

        configureButtons();
    }

    private void configureButtons(){
        enterNewListButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewContactDialog();
                //animation
            }
        });
        enterIDButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ActivityHelper.verifyPasscode(enterListPasscodeText.getText().toString(), mAllLists)){
                    encryptedPasscode = AuthEncrypt.encrypt(enterListPasscodeText.getText().toString());
                    openListActivity();
                }else{
                    ActivityHelper.makeToast("Incorrect passcode", getApplicationContext());
                }
            }
        });
    }

    private void readFromFirebase(){//TODO get all users list
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    mAllLists.add(dataSnapshot.getValue(ListObject.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void createNewContactDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View popupView = getLayoutInflater().inflate(R.layout.new_list_popup, null);
        newpopup_listName = popupView.findViewById(R.id.enter_list_name);
        newpopup_save = popupView.findViewById(R.id.new_list_save);
        newpopup_cancel = popupView.findViewById(R.id.new_list_cancel);
        newpopup_passcode = popupView.findViewById(R.id.passcode);

        dialogBuilder.setView(popupView);
        dialog = dialogBuilder.create();
        dialog.show();

        newpopup_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthConditional.setVariables(newpopup_passcode.getText().toString(), mAllLists);
                String msg = AuthConditional.getMessage();
                if(!AuthConditional.doesVerify()) {
                    ActivityHelper.makeToast(msg, getApplicationContext());
                }else{
                    if(newpopup_listName.getText().toString().equals("")){
                        ActivityHelper.makeToast("Enter a list name", getApplicationContext());
                    }else{
                        encryptedPasscode = AuthEncrypt.encrypt(newpopup_passcode.getText().toString());
                        updateVariables(newpopup_listName.getText().toString(), new ArrayList<>(), new ArrayList<>());
                        syncToFirebase();
                        openListActivity();
                    }
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

    private void updateVariables(String listName, ArrayList<ListItem> items, ArrayList<String> members){
        this.listName = listName;
        this.items = items;
        this.members = members;
    }

    private void syncToFirebase(){
        ListObject list = new ListObject(items, listName, members, encryptedPasscode);
        String id = mRef.push().getKey();
        list.setFireBaseID(id);

        mRef.child(id).setValue(list);
    }

    public void openListActivity(){
        Intent intent = new Intent(this, com.example.grouplist.ListActivity.class);
        startActivity(intent);
    }
}