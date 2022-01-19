package com.example.lendify

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.lendify.databinding.ActivityMainBinding
import com.example.lendify.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegistrationActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegistrationBinding
    private val ref = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ButtonRegister.setOnClickListener { register() }


    }

    private fun register() {
        if (binding.EditTextEmail.text.isNotEmpty() && binding.EditTextPassword.text.isNotEmpty()) {
            val email = binding.EditTextEmail.text.toString().trim()
            val password = binding.EditTextPassword.text.toString().trim()
            ref.createUserWithEmailAndPassword(
                email, password
            )
            Toast.makeText(
                applicationContext, "Sie haben Sich erfolgreich registriert. Loggen Sie sich nun ein !", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        if (binding.EditTextEmail.text.isNullOrEmpty() && binding.EditTextPassword.text.isNullOrEmpty()) {
            Toast.makeText(applicationContext, "Bitte geben Sie eine Email-Adresse und ein gültiges Passwort ein!", Toast.LENGTH_SHORT).show()
        }
    }
}