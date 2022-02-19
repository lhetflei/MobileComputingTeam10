package com.example.lendify

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.lendify.data.Datasource
import com.example.lendify.databinding.ActivityPersonalBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class PersonalActivity : AppCompatActivity() {

    lateinit var binding: ActivityPersonalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bottomNavigationView=findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController=findNavController(R.id.fragment)
        bottomNavigationView.setupWithNavController(navController)


    }
}