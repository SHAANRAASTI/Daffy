package com.shaan.daffy.splashscreen

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.google.firebase.auth.FirebaseUser
import com.shaan.daffy.LoginActivity
import com.shaan.daffy.R

private const val ARG_BACKGROUND_COLOR = "param1"
private const val ARG_RESOURCE = "param2"
private const val ARG_TITLE = "param3"

class DummyFragment : Fragment() {
    private var param1: Int? = null
    private var param2: Int? = null
    private var param3: String? = null
    var currentUser: FirebaseUser? = null
    var onBoardingScreen: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        arguments?.let {
            param1 = it.getInt(ARG_BACKGROUND_COLOR)
            param2 = it.getInt(ARG_RESOURCE)
            param3 = it.getString(ARG_TITLE)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?


    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dummy, container, false).apply {
            setBackgroundColor(param1 ?: Color.RED)
            val skip_btn = findViewById<Button>(R.id.skip_btn)

            skip_btn.setOnClickListener {

                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            }

            findViewById<LottieAnimationView>(R.id.lottieAnimationView).setAnimation(
                    param2 ?: R.raw.s1
            )
            findViewById<LottieAnimationView>(R.id.lottieAnimationView).repeatCount =
                    LottieDrawable.INFINITE
            findViewById<LottieAnimationView>(R.id.lottieAnimationView).repeatMode =
                    LottieDrawable.REVERSE
            findViewById<LottieAnimationView>(R.id.lottieAnimationView).playAnimation()

            findViewById<TextView>(R.id.fragment_textview).text =
                    param3 ?: "It gives voice and a platform to anyone willing to engage"
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int, param2: Int, param3: String) =
                DummyFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_BACKGROUND_COLOR, param1)
                        putInt(ARG_RESOURCE, param2)
                        putString(ARG_TITLE, param3)
                    }
                }
    }
}
