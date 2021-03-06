package com.example.lendify

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lendify.databinding.ActivityUploadBinding
import com.example.lendify.model.Items
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.*


class UploadActivity : AppCompatActivity() {

    lateinit var binding: ActivityUploadBinding
    var ImageUri: Uri? = null
    var ImageBytes: ByteArray? = null
    private var ref = FirebaseAuth.getInstance()
    val formatter = java.text.SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
    val now = Date()
    val fileName = formatter.format(now)
    var username: String = ""
    var database = FirebaseDatabase.getInstance("https://lendify-6cd5f-default-rtdb.europe-west1.firebasedatabase.app").getReference()
    var database_user = FirebaseDatabase.getInstance("https://lendify-6cd5f-default-rtdb.europe-west1.firebasedatabase.app").getReference("user")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.setTitle("Gegenstand zum Verleih freigeben")

        Log.i(ContentValues.TAG, database.child("userID").toString())
        database_user.child(ref.uid!!).get().addOnSuccessListener {
            username = it.child("userName").value.toString()
        }

        binding.imageViewGallery.setOnClickListener {
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_CODE)
                } else {
                    pickImageFromGallery()
                }
            } else {
                pickImageFromGallery()
            }
        }

        binding.imageViewCamera.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    val permissions = arrayOf(android.Manifest.permission.CAMERA)
                    requestPermissions(permissions, PERMISSION_CODE)
                } else {
                    takeImageFromCamera()
                }
            } else {
                takeImageFromCamera()
            }

        }

        binding.buttonUpload.setOnClickListener {
            if (binding.editTextTextPersonName.text.isEmpty())
                {
                    Toast.makeText(this, "Geben Sie einen Namen f??r den Gegenstand ein...", Toast.LENGTH_SHORT).show()
                }
            if (binding.editTextTextPersonName2.text.isEmpty())
            {
                Toast.makeText(this, "Geben Sie einen Preis ein...", Toast.LENGTH_SHORT).show()
            }
            else {
                upload()
            }
        }
    }


    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    private fun takeImageFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, IMAGE_TAKE_CODE)
    }

    private fun upload() {

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Bild wird hochgeladen...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val storageReference = FirebaseStorage.getInstance().getReference("Bilder/$fileName"+ref.uid.toString())

        if (ImageUri != null && ImageBytes == null)
        {
            //Firebase Image Upload Gallery
            storageReference.putFile(ImageUri!!).
            addOnSuccessListener {
                binding.firebaseImage.setImageURI(null)
                uploaddatabase()
                Toast.makeText(this, "Das Bild wurde erfolgreich hochgeladen!", Toast.LENGTH_SHORT).show()
                if (progressDialog.isShowing) progressDialog.dismiss()
                val intent = Intent(this,PersonalActivity::class.java)
                startActivity(intent)
                ImageUri = null
            }.addOnFailureListener{
                if (progressDialog.isShowing) progressDialog.dismiss()
                Toast.makeText(this, "Upload fehlgeschlagen!", Toast.LENGTH_SHORT).show()
            }
        }
        if (ImageUri == null && ImageBytes != null)
        {
            //Firebase Image Upload Camera
            storageReference.putBytes(ImageBytes!!).
            addOnSuccessListener {
                binding.firebaseImage.setImageBitmap(null)
                uploaddatabase()
                Toast.makeText(this, "Das Bild wurde erfolgreich hochgeladen!", Toast.LENGTH_SHORT).show()
                if (progressDialog.isShowing) progressDialog.dismiss()
                val intent = Intent(this,PersonalActivity::class.java)
                startActivity(intent)
                ImageBytes = null
            }.addOnFailureListener{
                if (progressDialog.isShowing) progressDialog.dismiss()
                Toast.makeText(this, "Upload fehlgeschlagen!", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(this, "Bitte w??hlen Sie ein Bild zum hochladen aus!", Toast.LENGTH_SHORT).show()
            progressDialog.dismiss()
        }
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000
        //permission code
        private val PERMISSION_CODE = 1001
        //camera code
        private val IMAGE_TAKE_CODE = 1002
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            PERMISSION_CODE->{
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                }
                else{
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
            IMAGE_PICK_CODE->{
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                }
                else{
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
            IMAGE_TAKE_CODE->{
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                }
                else{
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //onActivityResult gallery & camera
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null){

            when(requestCode){
                IMAGE_TAKE_CODE->{

                    var takenImage: Bitmap = data?.extras?.get("data") as Bitmap
                    binding.firebaseImage.setImageBitmap(takenImage)
                    var bytes = ByteArrayOutputStream()
                    takenImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
                    ImageBytes = bytes.toByteArray()


                }
                IMAGE_PICK_CODE->{

                    ImageUri = data?.data!!
                    binding.firebaseImage.setImageURI(ImageUri)
                }
            }
        }
        else{
            val intent = Intent(this,UploadActivity::class.java)
            startActivity(intent)
        }
    }

    fun uploaddatabase()
    {
        val add =Items(binding.editTextTextPersonName.text.toString(),"Bilder/$fileName"+ref.uid.toString(),ref.currentUser!!.email.toString(),binding.editTextTextPersonName2.text.toString().toInt(),ref.uid.toString()+now.toString(), username.toString())
        database.child("angebot").child(ref.uid.toString()+now.toString()).setValue(add)
    }
}
