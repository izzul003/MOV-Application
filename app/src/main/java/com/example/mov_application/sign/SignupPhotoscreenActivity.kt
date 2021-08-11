package com.example.mov_application.sign

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mov_application.HomeScreenActivity
import com.example.mov_application.R
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.util.*
import com.example.mov_application.utils.Preferences

class SignupPhotoscreenActivity : AppCompatActivity(), PermissionListener {
    val REQUEST_IMAGE_CAPTURE = 1
    var statusAdd: Boolean = false
    lateinit var fileUrl: Uri
    lateinit var storage: FirebaseStorage
    lateinit var storageReferensi: StorageReference
    lateinit var preferences: Preferences

    var text_name = findViewById(R.id.text_name) as TextView
    var iv_add_profile = findViewById(R.id.iv_add_profile) as ImageView
    var iv_profile = findViewById(R.id.iv_profile) as ImageView
    var btn_submit_signup = findViewById(R.id.btn_submit_signup) as Button
    var btn_upload_nanti = findViewById(R.id.btn_upload_nanti) as Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_photoscreen)

        preferences = Preferences(this)
        storage = FirebaseStorage.getInstance()
        storageReferensi = storage.getReference()

        text_name.text = intent.getStringExtra("nama")

        iv_add_profile.setOnClickListener {
            if (statusAdd) {
                statusAdd = false
                btn_submit_signup.visibility = View.VISIBLE
                iv_add_profile.setImageResource(R.drawable.ic_add_photo)
                iv_profile.setImageResource(R.drawable.ic_user)
            } else {
                Dexter.withActivity(this)
                        .withPermission(android.Manifest.permission.CAMERA)
                        .withListener(this)
                        .check()
            }
        }

        btn_upload_nanti.setOnClickListener {
            finishAffinity()
            var goHomeScreenActivity = Intent(this@SignupPhotoscreenActivity, HomeScreenActivity::class.java)
            startActivity(goHomeScreenActivity)
        }

        btn_submit_signup.setOnClickListener {
            if (fileUrl != null) {
                var progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading...")
                progressDialog.show()

                var ref = storageReferensi.child("images/"+UUID.randomUUID().toString())
                ref.putFile(fileUrl)
                    .addOnSuccessListener {
                        progressDialog.dismiss()
                        Toast.makeText(this, "Uploaded", Toast.LENGTH_LONG).show()

                        ref.downloadUrl.addOnSuccessListener {
                            preferences.setValues("url", it.toString())
                        }
                    }
                    .addOnFailureListener {
                        progressDialog.dismiss()
                        Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()
                    }
                    .addOnProgressListener {
                        taskSnapshot -> var progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                        progressDialog.setMessage("upload" + progress.toInt() + "%")
                    }

                finishAffinity()

                var goHomeScreenActivity = Intent(this@SignupPhotoscreenActivity, HomeScreenActivity::class.java)
                startActivity(goHomeScreenActivity)
            } else {
                Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
                    takePictureIntent ->
                    takePictureIntent.resolveActivity(packageManager)?.also {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                    }
                }
            }
        }
    }

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {

    }

    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {

    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        Toast.makeText(this, "Anda tidak bisa menambahkan photo Profile", Toast.LENGTH_LONG).show()
    }

    override fun onBackPressed() {
        Toast.makeText(this, "Tergesah ? Klik tombol upload nanti saja", Toast.LENGTH_LONG).show()
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && requestCode == Activity.RESULT_OK) {
            var bitmap = data?.extras?.get("data") as Bitmap

            statusAdd = true
            fileUrl = data.getData()!!

            Glide.with(this)
                .load(bitmap)
                .apply(RequestOptions.circleCropTransform())
                .into(iv_profile)

            btn_submit_signup.visibility = View.VISIBLE
            iv_add_profile.setImageResource(R.drawable.ic_btn_delete)
        }
    }
}