package com.example.mov_application.onBoarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mov_application.R
import android.widget.Button

class OnboardingTwoScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_two_screen)

        var btn_lanjut_on_boarding3 = findViewById(R.id.btn_lanjut_on_boarding3) as Button

        btn_lanjut_on_boarding3.setOnClickListener {
            var intent = Intent(this@OnboardingTwoScreenActivity, OnboardingThreeScreenActivity::class.java)
            startActivity(intent)
        }

    }
}