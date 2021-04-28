package com.example.mov_application.onBoarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mov_application.R
import android.widget.Button
import com.example.mov_application.sign.SignInActivity

class OnboardingThreeScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_three_screen)

        var btn_lanjut_sign_in = findViewById(R.id.btn_lanjut_sign_in) as Button
        btn_lanjut_sign_in.setOnClickListener {
            var intent = Intent(this@OnboardingThreeScreenActivity, SignInActivity::class.java)
            startActivity(intent)
        }

    }
}