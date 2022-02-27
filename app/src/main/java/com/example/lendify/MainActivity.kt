package com.example.lendify

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lendify.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var ref = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ButtonLogin.setOnClickListener {
            validateLogin()
        }
        binding.TextViewRegister.setOnClickListener { val intent = Intent(this,RegistrationActivity::class.java)
            startActivity(intent) }
        checkUser()

    }


    private fun checkUser() {

        val firebaseUser = ref.currentUser
        if (firebaseUser != null)
        {
            val intent = Intent(this,PersonalActivity::class.java)
            startActivity(intent)
            finish()
        }
    } //Prüfen ob User eingeloggt ist

    private fun validateLogin(){

        if(binding.EditTextPasswordL.text.toString().isEmpty()||binding.EditTextNameL.text.toString().isEmpty()) {
            if (binding.EditTextPasswordL.text.toString().isEmpty()) {
                binding.EditTextPasswordL.error = "Bitte geben Sie ein Passwort ein!"
            }
            if (binding.EditTextNameL.text.toString().isEmpty()) {
                binding.EditTextNameL.error = "Bitte geben Sie eine Email ein!"

            }
        }else {
                firebaseLogin()
            }

    } //Gültigkeit der Eingabedaten prüfen

    private fun firebaseLogin(){
        val email = binding.EditTextNameL.text.toString().trim()
        val password = binding.EditTextPasswordL.text.toString().trim()


        ref.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val intent = Intent(this,PersonalActivity::class.java)
                startActivity(intent)
            }
            .addOnFailureListener{ e->
                Toast.makeText(this, "Login fehlgeschlagen. Grund: ${e.message}", Toast.LENGTH_SHORT).show()
            }


    }



}

