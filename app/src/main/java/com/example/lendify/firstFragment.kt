package com.example.lendify

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.lendify.databinding.FragmentFirstBinding
import com.example.lendify.databinding.FragmentSecondBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class firstFragment : Fragment(R.layout.fragment_first) {

    private var _binding:FragmentFirstBinding?=null
    private val binding get()=_binding!!
    private var ref = FirebaseAuth.getInstance()
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
        binding.TextViewEmail.text=ref.currentUser!!.email!!.toString()
        binding.ButtonEditEmail.setOnClickListener {updatemail()}
        binding.ButtonEditPasswort.setOnClickListener { updatepasswort() }






    }
    fun updatemail(){
        ref.signInWithEmailAndPassword(ref.currentUser!!.email.toString(),binding.editTextTextPassword2.text.toString().trim())
        ref.currentUser!!.updateEmail(binding.editTextTextEmailAddress.text.toString().trim())
        binding.TextViewEmail.text=binding.editTextTextEmailAddress.text
    }
    fun updatepasswort(){
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
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
    }