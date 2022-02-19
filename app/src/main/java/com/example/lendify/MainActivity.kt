package com.example.lendify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.lendify.adapter.ItemAdapter
import com.example.lendify.data.Datasource
import com.example.lendify.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var ref = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ButtonLogin.setOnClickListener {
            validateLogin()
            firebaseLogin()
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
        val email = binding.EditTextNameL.text.toString().trim()
        val password = binding.EditTextPasswordL.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.EditTextNameL.error = "Falsches E-Mail format!"
        }
        else if (TextUtils.isEmpty(password)){
            binding.EditTextPasswordL.error = "Bitte geben Sie ein Passwort ein!"
        }
        else{
            firebaseLogin()
        }
    } //Gültigkeit der Eingabedaten prüfen

    private fun firebaseLogin(){
        val email = binding.EditTextNameL.text.toString().trim()
        val password = binding.EditTextPasswordL.text.toString().trim()

        //progressDialog.show()
        ref.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val firebaseUser = ref.currentUser
                val email = firebaseUser!!.email
                val intent = Intent(this,PersonalActivity::class.java)
                startActivity(intent)
            }
            .addOnFailureListener{ e->
                Toast.makeText(this, "Login fehlgeschlagen. Grund: ${e.message}", Toast.LENGTH_SHORT).show()
            }


    } //Firebase Login



}

