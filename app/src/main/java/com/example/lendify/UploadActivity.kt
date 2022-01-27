package com.example.lendify

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lendify.databinding.ActivityMainBinding
import com.example.lendify.databinding.ActivityUploadBinding
import com.google.firebase.storage.FirebaseStorage
import java.util.*


class UploadActivity : AppCompatActivity() {

    lateinit var binding: ActivityUploadBinding
    lateinit var ImageUri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_upload)

        binding.buttonUpload.setOnClickListener{
            pickImage()
        }

        binding.imageViewGallery.setOnClickListener{
            pickImage()
        }
    }

    private fun pickImage() {
        val intent = Intent()
        intent.type = "images/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 100)
    }

    private fun uploadImage() {

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading File ...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val formatter = java.text.SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)
        val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")

        storageReference.putFile(ImageUri).
        addOnSuccessListener {

            Toast.makeText(this, "Erfolgreich hochgeladen!", Toast.LENGTH_SHORT).show()
            if (progressDialog.isShowing) progressDialog.dismiss()
        }
            .addOnFailureListener{
                if (progressDialog.isShowing) progressDialog.dismiss()
                Toast.makeText(this, "Fehlgeschalgen", Toast.LENGTH_SHORT).show()
            }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK){
            ImageUri = data?.data!!
            //Image View anzeigen

        }
    }
}