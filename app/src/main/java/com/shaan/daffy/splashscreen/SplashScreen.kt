package com.shaan.daffy.splashscreen

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.shaan.daffy.DashboardActivity
import com.shaan.daffy.LoginActivity
import com.shaan.daffy.R
import com.shaan.daffy.models.All_UserMmber
import kotlinx.android.synthetic.main.activity_splashscreen.*
import kotlinx.android.synthetic.main.fragment_dummy.*

class SplashScreen : AppCompatActivity() {

    var onBoardingScreen: SharedPreferences? = null
    var currentUser: FirebaseUser? = null
    val firebaseDatabase: FirebaseDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)



            //create new handler
            Handler().postDelayed({
                onBoardingScreen = getSharedPreferences("OnBoardingScreen", MODE_PRIVATE)
                val isFirstTime = onBoardingScreen?.getBoolean("firstTime", true)
                if (currentUser == null) {
                    if (isFirstTime == true) {
                        //move to OnBoarding activity
                        val editor = onBoardingScreen?.edit()
                        editor?.putBoolean("firstTime", false)
                        editor?.commit()

                        val intent = Intent(applicationContext, SplashScreen::class.java)
                        startActivity(intent)
                    }/*else {
                        //move to activity_mainlogin activity
                        val intent = Intent(this@SplashScreen, LoginActivity::class.java)
                        startActivity(intent)
                    }*/
                } else {



                    var getdata = object : ValueEventListener{
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.exists())
                            {
                                val All_UserMmber: All_UserMmber? = dataSnapshot.getValue(All_UserMmber::class.java)
                                if (All_UserMmber != null) {
                                    // move to MainActivity activity
                                    val intent = Intent(this@SplashScreen, LoginActivity::class.java)
                                    startActivity(intent)
                                }
                            }
                            else {
                                // move to Profile activity
                                val intent = Intent(this@SplashScreen, DashboardActivity::class.java)
                                startActivity(intent)
                            }

                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                    }
                    firebaseDatabase?.getReference("All Users")?.child(currentUser!!.uid)?.addValueEventListener(getdata)
                    firebaseDatabase?.getReference("All Users")?.child(currentUser!!.uid)?.addListenerForSingleValueEvent(getdata)

        }

},2500)

        viewpager.adapter = CustomFragmentPagerAdapter(supportFragmentManager)



        viewpager.setCurrentItem(titleArray.count() * 10, false)

        fun onStart() {
            super.onStart()
            // get current user
            currentUser = FirebaseAuth.getInstance().currentUser
        }


}


}


