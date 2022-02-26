package com.example.lendify

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.lendify.databinding.FragmentFirstBinding
import com.example.lendify.databinding.FragmentSecondBinding
import com.example.lendify.model.Items
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.io.File

class firstFragment : Fragment(R.layout.fragment_first) {

    private var _binding:FragmentFirstBinding?=null
    private val binding get()=_binding!!
    private var ref = FirebaseAuth.getInstance()
    var ImageUri: Uri? = null
    val localfile = File.createTempFile("tempImage","jpg")
    var database2 =
        FirebaseDatabase.getInstance("https://lendify-6cd5f-default-rtdb.europe-west1.firebasedatabase.app")
            .getReference()
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentFirstBinding.inflate(inflater,container,false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ButtonEditName.setOnClickListener {
            ref.signOut()
            val intent = Intent(activity,MainActivity::class.java)
            startActivity(intent)
        }
        var database = FirebaseDatabase.getInstance("https://lendify-6cd5f-default-rtdb.europe-west1.firebasedatabase.app").getReference()
        binding.TextViewEmail.text=ref.currentUser!!.email!!.toString()
        binding.ButtonEditEmail.setOnClickListener { updatemail()}
        binding.ButtonEditPasswort.setOnClickListener { updatepasswort() }
        binding.button.setOnClickListener{
            pickImageFromGallery()
        }
        Log.i(TAG,ref.uid.toString() )

        database.child(ref.uid.toString()).get().addOnSuccessListener {
            var temp = it.value.toString()
            var storageRef = FirebaseStorage.getInstance().reference.child(temp)
            storageRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                binding.imageView3.setImageBitmap(Bitmap.createScaledBitmap(bitmap,125,125,true))
            }

        }
    }
    fun updatemail(){
        if(binding.editTextTextEmailAddress.text.toString()!=""||binding.editTextTextPassword2.text.toString()!=""){
        ref.signInWithEmailAndPassword(ref.currentUser!!.email.toString(),binding.editTextTextPassword2.text.toString().trim())
        ref.currentUser!!.updateEmail(binding.editTextTextEmailAddress.text.toString().trim()).addOnSuccessListener {
            binding.TextViewEmail.text=binding.editTextTextEmailAddress.text
            Toast.makeText(activity,"Email geändert",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{e->
            Toast.makeText(activity,"Fehler: ${e.message}",Toast.LENGTH_SHORT).show()
        }

        }else Toast.makeText(activity,"Neue Email eingeben",Toast.LENGTH_SHORT).show()

    }
    fun updatepasswort(){
        if(binding.editTextTextPassword3.text.toString()!=""||binding.editTextTextPassword4.text.toString()!=""||binding.editTextTextPassword4.text.toString()!=""){
        if(binding.editTextTextPassword4.text.toString().trim()==binding.editTextTextPassword5.text.toString().trim()) {
            ref.signInWithEmailAndPassword(ref.currentUser!!.email.toString(),binding.editTextTextPassword3.text.toString().trim())
            ref.currentUser!!.updatePassword(binding.editTextTextPassword4.text.toString().trim()).addOnSuccessListener {
                Toast.makeText(activity, "Passwort erfolgreich geändert", Toast.LENGTH_SHORT).show()
            }
                .addOnFailureListener{ e->
                    Toast.makeText(activity,"Passwort ändern fehlgeschlagen Grund: ${e.message}",Toast.LENGTH_LONG).show()}
        }
        else
        {
            Toast.makeText(activity,"Passwort ändern fehlgeschlagen",Toast.LENGTH_SHORT).show()
        }
    }
        else
            Toast.makeText(activity,"Passwort ändern fehlgeschlagen",Toast.LENGTH_SHORT).show()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000
        //permission code
        private val PERMISSION_CODE = 1001
        //camera code
        private val IMAGE_TAKE_CODE = 1002
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, firstFragment.IMAGE_PICK_CODE)
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
            firstFragment.IMAGE_PICK_CODE ->{
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                }
                else{
                    Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
            firstFragment.IMAGE_TAKE_CODE ->{
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                }
                else{
                    Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null){

            when(requestCode){
                firstFragment.IMAGE_PICK_CODE ->{

                    ImageUri = data?.data!!
                    binding.imageView3.setImageURI(ImageUri)



                }
            }
        }
        else{
            //TODO first fragment anstatt activity aufrufen
            val intent = Intent(context,PersonalActivity::class.java)
            startActivity(intent)
        }
        uploaddatabase()
    }

    private fun uploaddatabase() {

        val storageReference = FirebaseStorage.getInstance().getReference("Bilder/${ref.uid.toString()}")

        database2.child(ref.uid.toString()).setValue("Bilder/${ref.uid.toString()}")
        database2.child("user").child(ref.uid!!).child("userAvatar").setValue("Bilder/${ref.uid.toString()}")

        storageReference.putFile(ImageUri!!).
        addOnSuccessListener {
            Toast.makeText(context, "Das Bild wurde erfolgreich hochgeladen!", Toast.LENGTH_SHORT).show()
            val intent = Intent(context,thirdFragment::class.java)
            startActivity(intent)
            ImageUri = null
        }.addOnFailureListener{
            Toast.makeText(context, "Upload fehlgeschlagen!", Toast.LENGTH_SHORT).show()
        }
    }


}