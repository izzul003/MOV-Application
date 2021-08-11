package com.example.mov_application.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mov_application.R
import android.widget.EditText
import android.widget.Button
import android.widget.Toast
import com.google.firebase.database.*

class SignUpActivity : AppCompatActivity() {

    lateinit var sUsername: String
    lateinit var sPassword: String
    lateinit var sNama: String
    lateinit var sEmail: String
    lateinit var mFirebaseInstance: FirebaseDatabase
    lateinit var mDatabaseReference: DatabaseReference
    lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        var daftar_username = findViewById(R.id.daftar_username) as EditText
        var daftar_password = findViewById(R.id.daftar_password) as EditText
        var daftar_nama = findViewById(R.id.daftar_name) as EditText
        var daftar_email = findViewById(R.id.daftar_email) as EditText
        var btn_lanjutkan_signup_photo = findViewById(R.id.btn_lanjutkan_signup_photo) as Button
        mFirebaseInstance = FirebaseDatabase.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference()
        mDatabaseReference = mFirebaseInstance.getReference("User")

        btn_lanjutkan_signup_photo.setOnClickListener {
            sUsername = daftar_username.text.toString()
            sPassword = daftar_password.text.toString()
            sNama = daftar_nama.text.toString()
            sEmail = daftar_email.text.toString()

            if (sUsername.equals("")) {
                daftar_username.error = "Silahkan isi username anda"
                daftar_username.requestFocus()
            } else if (sPassword.equals("")) {
                daftar_password.error = "Silahkan isi password anda"
                daftar_password.requestFocus()
            } else if (sNama.equals("")) {
                daftar_nama.error = "Silahkan isi nama anda"
                daftar_nama.requestFocus()
            } else if (sEmail.equals("")) {
                daftar_email.error = "Silahkan isi email anda"
                daftar_email.requestFocus()
            } else {
                saveNewUser(sUsername, sPassword, sNama, sEmail)
            }
        }
    }

    private fun saveNewUser(sUsername: String, sPassword: String, sNama: String, sEmail: String) {
        var user = SignInActivity.User()
        user.username = sUsername
        user.password = sPassword
        user.nama = sNama
        user.email = sEmail

        if (sUsername != null) {
            checkingUser(sUsername, user)
        }
    }

    private fun checkingUser(sUsername: String, data: SignInActivity.User) {
        mDatabaseReference.child(sUsername).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignUpActivity, "" + databaseError.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var user = dataSnapshot.getValue(SignInActivity.User::class.java)

                if (user == null) {
                    mDatabaseReference.child(sUsername).setValue(data)

                    var goSignupPhotoscreenActivity = Intent(this@SignUpActivity, SignupPhotoscreenActivity::class.java).putExtra("nama", data?.nama)
                    startActivity(goSignupPhotoscreenActivity)
                } else {
                    Toast.makeText(this@SignUpActivity, "User sudah digunakan", Toast.LENGTH_LONG).show()
                }
            }

        })
    }
}