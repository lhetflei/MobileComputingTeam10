package com.example.lendify

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.lendify.databinding.ActivityUploadBinding
import com.example.lendify.model.Items
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import java.util.jar.Manifest


class UploadActivity : AppCompatActivity() {

    lateinit var binding: ActivityUploadBinding
    lateinit var ImageUri: Uri
    private var ref = FirebaseAuth.getInstance()
    val formatter = java.text.SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
    val now = Date()
    val fileName = formatter.format(now)
    var database = FirebaseDatabase.getInstance("https://lendify-6cd5f-default-rtdb.europe-west1.firebasedatabase.app").getReference()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.setTitle("Gegenstand zum Verleih freigeben")
        binding.buttonUpload.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE);
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                else{
                    pickImageFromGallery();
                }
            }
            else{
                pickImageFromGallery();
            }
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery()
                }
                else{
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){

            //Firebase Image Upload

            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Bild wird hochgeladen...")
            progressDialog.setCancelable(false)
            progressDialog.show()

            val storageReference = FirebaseStorage.getInstance().getReference("Bilder/$fileName")
            binding.firebaseImage.setImageURI(data?.data)
            ImageUri = data?.data!!

            storageReference.putFile(ImageUri).
            addOnSuccessListener {
                binding.firebaseImage.setImageURI(null)
                Toast.makeText(this, "Das Bild wurde erfolgreich hochgeladen!", Toast.LENGTH_SHORT).show()
                if (progressDialog.isShowing) progressDialog.dismiss()
                val intent = Intent(this,thirdFragment::class.java)
                startActivity(intent)
            }.addOnFailureListener{
                if (progressDialog.isShowing) progressDialog.dismiss()
                Toast.makeText(this, "Upload fehlgeschlagen!", Toast.LENGTH_SHORT).show()
            }

            uploaddatabase()
        }
    }
    fun uploaddatabase()
    {
        val add =Items(binding.editTextTextPersonName.text.toString(),"Bilder/$fileName",ref.currentUser!!.email.toString(),binding.editTextTextPersonName2.text.toString().toInt())
        database.child("angebot").child(ref.uid.toString()+now.toString()).setValue(add)
    }
}
