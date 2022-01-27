package com.example.lendify

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lendify.adapter.ItemAdapter
import com.example.lendify.data.Datasource
import com.example.lendify.databinding.FragmentSecondBinding
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class secondFragment : Fragment(R.layout.fragment_second) {

    private var _binding: FragmentSecondBinding?=null
    private val binding get()=_binding!!
    var storageRef = FirebaseStorage.getInstance().reference.child("Bilder/rwb2.jpg")
    val localfile = File.createTempFile("tempImage","jpg")
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentSecondBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myDataset = Datasource().loadItems()
        binding.recyclerView.adapter = ItemAdapter(this, myDataset)
        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap= BitmapFactory.decodeFile(localfile.absolutePath)
            binding.itemImage.setImageBitmap(bitmap)
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}