package com.example.mov_application.sign

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.mov_application.R
import android.widget.EditText
import android.widget.Toast
import com.example.mov_application.HomeScreenActivity
import com.example.mov_application.utils.Preferences
import com.google.firebase.database.*


lateinit var iusername:String
lateinit var ipassword:String
lateinit var nDatabase: DatabaseReference
lateinit var preference: Preferences

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        var btn_sign_in = findViewById(R.id.btn_sign_in) as Button
        var btn_lanjut_sign_up = findViewById(R.id.btn_lanjut_sign_up) as Button
        var et_username = findViewById(R.id.input_username) as EditText
        var et_password = findViewById(R.id.input_password) as EditText

        nDatabase = FirebaseDatabase.getInstance().getReference("User")
        preference = Preferences(this)

        preference.setValues("onboarding", "1")
        if (preference.getValues("status").equals("1")) {
            finishAffinity()

            var goHome = Intent(this@SignInActivity, HomeScreenActivity::class.java)
            startActivity(goHome)
        }

        btn_sign_in.setOnClickListener {
            iusername = et_username.text.toString()
            ipassword = et_password.text.toString()

            if (iusername.equals("")) {
                et_username.error = "Silahkan tulis username anda"
                et_username.requestFocus()
            } else if (ipassword.equals("")) {
                et_password.error = "Silahkan tulis password anda"
                et_password.requestFocus()
            } else {
                pushLogin(iusername, ipassword)
            }
        }

        btn_lanjut_sign_up.setOnClickListener {
            var goSignUp = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(goSignUp)
        }
    }

    private fun pushLogin(iusername: String, ipassword: String) {
        nDatabase.child(iusername).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignInActivity, databaseError.message , Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var user = dataSnapshot.getValue(User::class.java)
                if (user == null) {
                    Toast.makeText(this@SignInActivity, "Username tidak ditemukan", Toast.LENGTH_LONG).show()
                } else {
                    if (user.password.equals(ipassword)) {

                        preference.setValues("nama", user.nama.toString())
                        preference.setValues("username", user.username.toString())
                        preference.setValues("url", user.url.toString())
                        preference.setValues("email", user.email.toString())
                        preference.setValues("saldo", user.saldo.toString())
                        preference.setValues("status", "1")

                        var intent = Intent(this@SignInActivity, HomeScreenActivity::class.java)
                        startActivity((intent))
                    } else {
                        Toast.makeText(this@SignInActivity, "Password anda salah", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    class User {
        var email : String ?= ""
        var nama : String ?= ""
        var password : String ?= ""
        var url : String ?= ""
        var username : String ?= ""
        var saldo : String ?= ""
    }
}