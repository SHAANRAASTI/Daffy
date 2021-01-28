package com.shaan.sanctify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    //firebase auth
    FirebaseAuth firebaseAuth;

    //views
    TextView mProfieTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        //Actionbar and its title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Profile");

        //init
        firebaseAuth = FirebaseAuth.getInstance();

        //init views
        mProfieTv = findViewById(R.id.profileTv);
    }
    private void checkUserStatus()
    {
        FirebaseUser user = firebaseAuth.getCurrentUser();
    if(user != null)
    {
        //user is signed  is stay here
        //set email of logged in user
        mProfieTv.setText(user.getEmail());
    }
    else
    {
        //user not signed in to main activity
        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
        finish();
    }
    }

    @Override
    protected void onStart() {
        //check on start of app
        checkUserStatus();
        super.onStart();

    }
    /*inflate options menu*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }
    /* hndle menu item clicks*/

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_logout)
        {
        firebaseAuth.signOut();
        checkUserStatus();
        }

        return super.onOptionsItemSelected(item);
    }
}