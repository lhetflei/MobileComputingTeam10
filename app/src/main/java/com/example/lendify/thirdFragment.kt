package com.example.lendify

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lendify.adapter.ItemAdapter
import com.example.lendify.adapter.ItemAdapter2
import com.example.lendify.data.Datasource
import com.example.lendify.databinding.FragmentFirstBinding
import com.example.lendify.databinding.FragmentSecondBinding
import com.example.lendify.databinding.FragmentThirdBinding
import com.example.lendify.model.Items
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class thirdFragment : Fragment(R.layout.fragment_third) {
    private var database = FirebaseDatabase.getInstance("https://lendify-6cd5f-default-rtdb.europe-west1.firebasedatabase.app").getReference("angebot")
    private var _binding: FragmentThirdBinding?=null
    private val binding get()=_binding!!
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentThirdBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        GlobalScope.launch(Dispatchers.Main) {
            val myDataset = Datasource().loadItems2()
            delay(350)
            Log.i(ContentValues.TAG, "main")
            try {
                var adapter = ItemAdapter(myDataset)
                binding.recyclerView.adapter = adapter
                adapter.setOnItemClickListener(object : ItemAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {

                        database.child(myDataset[position].id.toString()).removeValue()
                        Log.i(TAG, database.child(myDataset[position].id.toString()).toString())
                       // database.child(myDataset[position].text.toString()).parent!!.toString()
                        Toast.makeText(context,"Gegenstand erfolgreich entfernt",Toast.LENGTH_SHORT).show()
                        val intent = Intent(context, PersonalActivity::class.java)
                        startActivity(intent)
                    }

                })
            } catch (e: Exception) {
            }

            binding.buttonAdd.setOnClickListener {
                val intent = Intent(context, UploadActivity::class.java)
                startActivity(intent)
            }
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}