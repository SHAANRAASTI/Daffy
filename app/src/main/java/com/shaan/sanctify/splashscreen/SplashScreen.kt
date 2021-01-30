package com.shaan.sanctify.splashscreen

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseUser
import com.shaan.sanctify.MainActivity
import com.shaan.sanctify.R
import kotlinx.android.synthetic.main.activity_splashscreen.*
import kotlinx.android.synthetic.main.fragment_dummy.*

class SplashScreen : AppCompatActivity() {
    var onBoardingScreen: SharedPreferences? = null
    var currentUser: FirebaseUser? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)



        viewpager.adapter = CustomFragmentPagerAdapter(supportFragmentManager)








        viewpager.setCurrentItem(titleArray.count() * 10, false)

}}
