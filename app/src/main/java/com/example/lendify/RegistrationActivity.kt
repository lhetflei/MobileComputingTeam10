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
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class RegistrationActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegistrationBinding
    private var auth = FirebaseAuth.getInstance()
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ButtonRegister.setOnClickListener {
            register()
        }
        binding.TextView1.setOnClickListener { val intent = Intent(this,MainActivity::class.java)
            startActivity(intent) }

    }

    private fun register() {
        if (binding.EditTextEmail.text.isNotEmpty() && binding.EditTextPassword.text.isNotEmpty() && binding.registerName.text.isNotEmpty() && binding.editTextTextPassword.text.isNotEmpty()) {
            val user_name= binding.registerName.text.toString().trim()
            val email = binding.EditTextEmail.text.toString().trim()
            val password = binding.EditTextPassword.text.toString().trim()
            val passwordConfirm = binding.editTextTextPassword.text.toString().trim()

            if (password == passwordConfirm) {
                auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(this) {
                    //Saving user in realtime database
                    val user: FirebaseUser? = auth.currentUser
                    val userID: String = user!!.uid
                    databaseReference = FirebaseDatabase.getInstance("https://lendify-6cd5f-default-rtdb.europe-west1.firebasedatabase.app").getReference("user").child(userID)
                    val hashMap: HashMap<String, String> = HashMap()
                    hashMap.put("userAvatar", "")
                    hashMap.put("userID", userID)
                    hashMap.put("userName", user_name)
                    databaseReference.setValue(hashMap)

                Toast.makeText(applicationContext, "Sie haben Sich erfolgreich registriert. Loggen Sie sich nun ein !", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            }
            else Toast.makeText(applicationContext,"Passwort Stimmt nicht überein",Toast.LENGTH_SHORT).show()

            if (binding.EditTextEmail.text.isNullOrEmpty() && binding.EditTextPassword.text.isNullOrEmpty()) {
                Toast.makeText(
                    applicationContext,
                    "Bitte geben Sie eine Email-Adresse und ein gültiges Passwort ein!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}