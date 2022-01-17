package com.example.lendify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.lendify.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ButtonLogin.setOnClickListener {
            val intent = Intent(this,PersonalActivity::class.java)
            startActivity(intent)
        }
        binding.TextViewRegister.setOnClickListener { val intent = Intent(this,RegistrationActivity::class.java)
            startActivity(intent) }

    }




}