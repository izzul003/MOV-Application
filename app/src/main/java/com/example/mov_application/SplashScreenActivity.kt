package com.example.mov_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mov_application.onBoarding.OnboardingOneScreenActivity
import kotlinx.coroutines.*

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Start a coroutine
        GlobalScope.launch {
            delay(5000)
            var intent = Intent( this@SplashScreenActivity, OnboardingOneScreenActivity::class.java )
            startActivity(intent)
            finish()
        }
    }
}