package com.example.mov_application.onBoarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mov_application.R
import android.widget.Button
import com.example.mov_application.sign.SignInActivity
import com.example.mov_application.utils.Preferences

class OnboardingOneScreenActivity : AppCompatActivity() {

    lateinit var preference: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_one_screen)

        var btn_to_login = findViewById(R.id.btn_to_login) as Button
        var btn_lanjut_on_boarding2 = findViewById(R.id.btn_lanjut_on_boarding2) as Button

        preference = Preferences(this)

        if (preference.getValues("onboarding").equals("1")) {
            finishAffinity()
            var intent = Intent(this@OnboardingOneScreenActivity, SignInActivity::class.java )
            startActivity(intent)
        }

        btn_to_login.setOnClickListener {
            preference.setValues("onboarding", "1")
            finishAffinity()

            var intent = Intent(this@OnboardingOneScreenActivity, SignInActivity::class.java )
            startActivity(intent)
        }

        btn_lanjut_on_boarding2.setOnClickListener {
            var intent = Intent(this@OnboardingOneScreenActivity, OnboardingTwoScreenActivity::class.java )
            startActivity(intent)
        }

    }
}