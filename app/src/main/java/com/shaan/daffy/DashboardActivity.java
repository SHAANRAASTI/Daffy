package com.shaan.daffy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_navigation_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            //display requestfragment
            case R.id.nav_aboutus:
                Intent intent_aboutus = new Intent(DashboardActivity.this, AboutUs.class);
                startActivity(intent_aboutus);
                return true;
           /* case R.id.nav_feedback:
                Intent intent_feedback = new Intent(DashboardActivity.this, Send_Feedback.class);
                startActivity(intent_feedback);
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }



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

                   case R.id.chat:
                       selected = new Fragment5();
                       break;



                   case R.id.profile_bottom:
                       selected = new Fragment1();
                       break;

               }

               getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                       selected).commit();


               return true;

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
        // super.onBackPressed();
        Toast.makeText(DashboardActivity.this,"There is no back action, Please logout",Toast.LENGTH_LONG).show();
        return;
    }

    @Override
    protected void onStart() {
        //check on start of app
        checkUserStatus();
        super.onStart();

    }

}