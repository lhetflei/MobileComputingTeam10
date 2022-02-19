package com.example.lendify

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import java.lang.Exception

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
            delay(250) //warten auf datenbank
            try {
                var adapter = ItemAdapter(myDataset)
                binding.recyclerView.adapter = adapter
                adapter.setOnItemClickListener(object : ItemAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {
                        Log.i(TAG, myDataset[position].user.toString() )
                        val recipient = myDataset[position].user.toString()
                        val subject = "Lendify"
                        val mIntent = Intent(Intent.ACTION_SEND)
                        mIntent.data = Uri.parse("mailto:")
                        mIntent.type = "text/plain"
                        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
                        mIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
                        startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
                    }

                })
            }
            catch (e:Exception)
            {
                //reload falls datenbank zu langsam
                val intent = Intent(activity,PersonalActivity::class.java)
                startActivity(intent)
            }
        }




    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}