package com.example.lendify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lendify.databinding.ActivityMainBinding
import com.example.lendify.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ButtonRegister.setOnClickListener{register()}




    }
    fun register(){
        if(binding.EditTextName.text.isNotEmpty()&&binding.EditTextEmail.text.isNotEmpty()&&binding.EditTextPassword.text.isNotEmpty())
        binding.TextView1.text="Erfolg"
        else
            binding.TextView1.text="Fehler"
    }

}