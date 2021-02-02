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

//new bottom navigation code
       /* navigation = findViewById(R.id.bottom_navigation);
        navigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_home));
        navigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_account));
        navigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_explore));

        getSupportFragmentManager().beginTransaction().replace(R.id.root_container, new HomeFragment()).commit();
        navigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model item) {
                // YOUR CODES


                return null;
            }
        });
        navigation.setOnReselectListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model item) {
                // YOUR CODES


                return null;
            }
        });

        navigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model item) {
                // YOUR CODES
                Fragment fragmentSelected = null;
                switch ((item.getId()))
                {
                    case Home_ID:
                        fragmentSelected = new HomeFragment();
                        break;
                    case PROFILE_ID:
                        fragmentSelected = new HomeFragment();
                        break;
                    case USERS_ID:
                        fragmentSelected = new HomeFragment();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.root_container,fragmentSelected).commit();

                return null;
            }
        });

/**/
        //Actionbar and its title
        actionBar = getSupportActionBar();
        actionBar.setTitle("Profile");

        //init
        firebaseAuth = FirebaseAuth.getInstance();
        //bottom navigation
        navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(selectedListener);
        //home fragment transaction
        actionBar.setTitle("Home"); //change actionbar title
        HomeFragment fragment1 = new HomeFragment();
        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.root_container,fragment1,"");
        ft1.commit();


    }
   private BottomNavigationView.OnNavigationItemSelectedListener selectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    //handle item clicks
                    switch (menuItem.getItemId())
                    {
                        case R.id.nav_home:
                            //home fragment transaction
                            actionBar.setTitle("Home"); //change actionbar title
                            HomeFragment fragment1 = new HomeFragment();
                            FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                            ft1.replace(R.id.root_container,fragment1,"");
                            ft1.commit();
                            return true;
                        case R.id.nav_profile:
                            //profile fragment transaction
                            actionBar.setTitle("Profile"); //change actionbar title
                            ProfileFragment fragment2 = new ProfileFragment();
                            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                            ft2.replace(R.id.root_container,fragment2,"");
                            ft2.commit();
                            return true;

                        case R.id.nav_users:
                            //users fragment transaction
                            actionBar.setTitle("Users"); //change actionbar title
                            UsersFragment fragment3 = new UsersFragment();
                            FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                            ft3.replace(R.id.root_container,fragment3,"");
                            ft3.commit();
                            return true;

                    }
                    return false;

                }
            };
    private void checkUserStatus()
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