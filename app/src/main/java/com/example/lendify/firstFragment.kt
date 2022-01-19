package com.example.lendify

import android.content.Intent
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
import com.google.firebase.ktx.Firebase

class firstFragment : Fragment(R.layout.fragment_first) {

    private var _binding:FragmentFirstBinding?=null
    private val binding get()=_binding!!
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentFirstBinding.inflate(inflater,container,false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
    }