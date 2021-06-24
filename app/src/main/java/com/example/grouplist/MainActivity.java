package com.example.grouplist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageButton enterIDButton;
    private Button enterNewListButton;
    private TextView enterListIDText, appNameText;
    private ScrollView preexistingListsView;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText newpopup_listName;
    private Button newpopup_cancel, newpopup_save;

    public static String listName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enterIDButton = findViewById(R.id.EnterListIDButton);
        enterNewListButton = findViewById(R.id.CreateNewListButton);
        enterListIDText = findViewById(R.id.EnterListIDText);
        appNameText = findViewById(R.id.AppNameText);
        preexistingListsView = findViewById(R.id.ListsView);

        enterNewListButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //load list items based on id
                createNewContactDialog();
                //animation
            }
        });
    }

    public void createNewContactDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View popupView = getLayoutInflater().inflate(R.layout.new_list_popup, null);
        newpopup_listName = (EditText) popupView.findViewById(R.id.enter_list_name);
        newpopup_save = (Button) popupView.findViewById(R.id.new_list_save);
        newpopup_cancel = (Button) popupView.findViewById(R.id.new_list_cancel);

        dialogBuilder.setView(popupView);
        dialog = dialogBuilder.create();
        dialog.show();

        newpopup_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(newpopup_listName.getText().toString().equals("")){
                    Context context = getApplicationContext();
                    CharSequence text = "Enter a list name";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }else{
                    listName = newpopup_listName.getText().toString();
                    openListActivity();
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

    public void openListActivity(){
        Intent intent = new Intent(this, com.example.grouplist.ListActivity.class);
        startActivity(intent);
    }
}