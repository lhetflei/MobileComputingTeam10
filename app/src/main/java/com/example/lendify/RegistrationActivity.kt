package com.example.lendify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lendify.databinding.ActivityMainBinding
import com.example.lendify.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegistrationActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegistrationBinding
    val ref = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ButtonRegister.setOnClickListener{register()}




    }
    fun register(){
        binding.ButtonRegister.setOnClickListener{
            val email = binding.EditTextEmail.text.toString().trim()
            val password = binding.EditTextPassword.text.toString().trim()

            ref.createUserWithEmailAndPassword(
                email, password
            )
        }
    }

}
/*if(binding.EditTextName.text.isNotEmpty()&&binding.EditTextEmail.text.isNotEmpty()&&binding.EditTextPassword.text.isNotEmpty())
        binding.TextView1.text="Erfolg"
        else
            binding.TextView1.text="Fehler"*/