package com.example.grouplist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageButton enterIDButton,  enterNewListButton;
    private TextView enterListIDText, appNameText;
    private ScrollView preexistingListsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enterIDButton = findViewById(R.id.EnterListIDButton);
        enterNewListButton = findViewById(R.id.EnterListIDButton);
        enterListIDText = findViewById(R.id.EnterListIDText);
        appNameText = findViewById(R.id.AppNameText);
        preexistingListsView = findViewById(R.id.ListsView);

        enterIDButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //load list items based on id
                openListActivity();
                //animation
            }
        });
    }

    public void openListActivity(){
        Intent intent = new Intent(this, com.example.grouplist.ListActivity.class);
        startActivity(intent);
    }

}