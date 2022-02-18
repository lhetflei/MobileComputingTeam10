package com.example.lendify

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lendify.adapter.ItemAdapter
import com.example.lendify.data.Datasource
import com.example.lendify.databinding.FragmentSecondBinding
import com.example.lendify.model.Items
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.squareup.okhttp.Dispatcher
import kotlinx.coroutines.*
import java.io.File

class secondFragment : Fragment(R.layout.fragment_second) {
    private var database = FirebaseDatabase.getInstance("https://lendify-6cd5f-default-rtdb.europe-west1.firebasedatabase.app").getReference("angebot")
    var itemlist = arrayListOf<Items>()
    private var _binding: FragmentSecondBinding?=null
    private val binding get()=_binding!!
    private val myDataset = arrayListOf<Items>()
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentSecondBinding.inflate(inflater,container,false)
        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GlobalScope.launch(Dispatchers.Main) {
            val myDataset = Datasource().loadItems()
            delay(350)
            Log.i(TAG, "main")
            binding.recyclerView.adapter = ItemAdapter(myDataset)
        }




    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}