package com.example.lendify

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Camera
import android.graphics.ImageDecoder
import android.hardware.camera2.CameraManager
import android.icu.text.SimpleDateFormat
import android.media.Image
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.lendify.databinding.ActivityUploadBinding
import com.example.lendify.model.Items
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.net.URI
import java.util.*
import java.util.jar.Manifest


class UploadActivity : AppCompatActivity() {

    lateinit var binding: ActivityUploadBinding
    var ImageUri: Uri? = null
    var ImageBytes: ByteArray? = null
    private var ref = FirebaseAuth.getInstance()
    val formatter = java.text.SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
    val now = Date()
    val fileName = formatter.format(now)
    var database =
        FirebaseDatabase.getInstance("https://lendify-6cd5f-default-rtdb.europe-west1.firebasedatabase.app")
            .getReference()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.setTitle("Gegenstand zum Verleih freigeben")

        binding.imageViewGallery.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE);
                    requestPermissions(permissions, PERMISSION_CODE);
                } else {
                    pickImageFromGallery();
                }
            } else {
                pickImageFromGallery();
            }
        }


        binding.imageViewCamera.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    val permissions = arrayOf(android.Manifest.permission.CAMERA);
                    requestPermissions(permissions, PERMISSION_CODE);
                } else {
                    takeImageFromCamera();
                }
            } else {
                takeImageFromCamera();
            }

        }

        binding.buttonUpload.setOnClickListener {
            upload()
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

        val storageReference = FirebaseStorage.getInstance().getReference("Bilder/$fileName")

        if (ImageUri != null && ImageBytes == null)
        {
            //Firebase Image Upload Gallery
            storageReference.putFile(ImageUri!!).
            addOnSuccessListener {
                binding.firebaseImage.setImageURI(null)
                Toast.makeText(this, "Das Bild wurde erfolgreich hochgeladen!", Toast.LENGTH_SHORT).show()
                if (progressDialog.isShowing) progressDialog.dismiss()
                val intent = Intent(this,thirdFragment::class.java)
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
                Toast.makeText(this, "Das Bild wurde erfolgreich hochgeladen!", Toast.LENGTH_SHORT).show()
                if (progressDialog.isShowing) progressDialog.dismiss()
                val intent = Intent(this,thirdFragment::class.java)
                startActivity(intent)
                ImageBytes = null
            }.addOnFailureListener{
                if (progressDialog.isShowing) progressDialog.dismiss()
                Toast.makeText(this, "Upload fehlgeschlagen!", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(this, "Bitte wählen Sie ein Bild zum hochladen aus!", Toast.LENGTH_SHORT).show()
            progressDialog.dismiss()
        }
    }

    /*private fun createImageFile(): File?{

        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val taken_image = File.createTempFile(fileName, ".jpg", storageDir)

        imagePath = taken_image.absolutePath
        return taken_image
    }*/

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
            /*PERMISSION_CODE->{
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                }
                else{
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }*/
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

        if (resultCode == Activity.RESULT_OK){

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
        uploaddatabase()
    }
    fun uploaddatabase()
    {
        val add =Items(binding.editTextTextPersonName.text.toString(),"Bilder/$fileName",ref.currentUser!!.email.toString(),binding.editTextTextPersonName2.text.toString().toInt(),ref.uid.toString()+now.toString())
        database.child("angebot").child(ref.uid.toString()+now.toString()).setValue(add)
    }
}
