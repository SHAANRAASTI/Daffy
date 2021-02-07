package com.shaan.sanctify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import meow.bottomnavigation.MeowBottomNavigation;

public class DashboardActivity extends AppCompatActivity {
   // MeowBottomNavigation navigation;
    BottomNavigationView navigation;

   /* private final  static int Home_ID=1;
    private final  static int PROFILE_ID=2;
    private final  static int USERS_ID=3;*/
    //firebase auth
    FirebaseAuth firebaseAuth;
    ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        //init
        firebaseAuth = FirebaseAuth.getInstance();
        //bottom navigation
        navigation = findViewById(R.id.bottom_nav);
        navigation.setOnNavigationItemSelectedListener(onNav);
        //home fragment transaction
       // actionBar.setTitle("Home"); //change actionbar title
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                new Fragment4()).commit();


    }
   private BottomNavigationView.OnNavigationItemSelectedListener onNav =
           item -> {

               Fragment selected = null;
               switch (item.getItemId()){

                   case R.id.home_bottom:
                       selected = new Fragment4();
                       break;

                   case R.id.ask_bottom:
                       selected = new Fragment2();
                       break;

                   case R.id.queue_bottom:
                       selected = new Fragment3();
                       break;


                   case R.id.profile_bottom:
                       selected = new Fragment1();
                       break;

               }

               getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                       selected).commit();


               return true;

           };

  /*  private void checkUserStatus()
    {
        FirebaseUser user = firebaseAuth.getCurrentUser();
    if(user != null)
    {
        //user is signed  is stay here
        //set email of logged in user

    }
    else
    {
        //user not signed in to main activity
        startActivity(new Intent(DashboardActivity.this, MainActivity.class));
        finish();
    }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStart() {
        //check on start of app
        checkUserStatus();
        super.onStart();

    }*/

}