package com.example.grouplist;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.grouplist.Objects.UserObject;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private GoogleSignInOptions gso;
    private FirebaseAuth mAuth;
    public static FirebaseUser currentUser;

    private static final String TAG = "GOOGLEAUTH";
    private GoogleSignInClient mGoogleSignInClient;

    private Dialog dialog;

    private DatabaseReference mUserRef;

    private ArrayList<UserObject> mAllUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        mUserRef = FirebaseDatabase.getInstance().getReference("users");
        mAllUsers= new ArrayList<>();

        readFromFirebase();

        mAuth = FirebaseAuth.getInstance();
        createRequest();
        configureButtons();

    }

    private void configureButtons(){
        Button signInBtn = findViewById(R.id.google_signIn);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {

            if(result.getResultCode() == Activity.RESULT_OK){
                Intent intent = result.getData();

                dialog.show();
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    //Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                    assert account != null;
                    firebaseAuthWithGoogle(account.getIdToken());
                } catch (ApiException e) {
                    // Google Sign In failed, update UI appropriately
                    Log.w(TAG, "Google sign in failed", e);
                    dialog.dismiss();
                    // ...
                }
            }
        }
    });

    @Override
    public void onStart(){
        super.onStart();
        currentUser = mAuth.getCurrentUser();
        //updateUI, pass currentuser into method
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            currentUser = mAuth.getCurrentUser();
                            assert currentUser != null;
                            ActivityHelper.makeToast( "Signed in as " + currentUser.getDisplayName(), getApplicationContext());
                            syncToFirebase();
                            startDefaultScreenActivity();
                            dialog.dismiss();
                        } else {
                            // If sign in fails, display a message to the user.
                            ActivityHelper.makeToast("Failed to sign in", getApplicationContext());
                            //updateUI, pass null
                        }

                        // ...
                    }
                });
    }

    private void syncToFirebase(){
        String email = currentUser.getEmail();
        //check to see if id is not stored in database
        if(!ActivityHelper.verifyEmail(email, mAllUsers)){
            String id = mUserRef.push().getKey();
            UserObject userObject = new UserObject(currentUser.getDisplayName(), email);
            userObject.setFirebaseID(id);
            assert id != null;
            mUserRef.child(id).setValue(userObject);
        }
    }

    private void readFromFirebase(){
        mUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    mAllUsers.add(dataSnapshot.getValue(UserObject.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed data retrieval");
            }
        });
    }

    private void signIn(){
        resultLauncher.launch(new Intent(mGoogleSignInClient.getSignInIntent()));
    }

    private void signOut(){
        FirebaseAuth.getInstance().signOut();
    }//TODO utilize this

    private void startDefaultScreenActivity(){
        Intent intent = new Intent(this, DefaultScreenActivity.class);
        startActivity(intent);
    }

    private void createRequest(){
        gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        dialog = new Dialog(LoginActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_wait1);
        dialog.setCanceledOnTouchOutside(false);
    }
}