package com.example.lendify

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lendify.adapter.ItemAdapter
import com.example.lendify.adapter.ItemAdapter2
import com.example.lendify.data.Datasource
import com.example.lendify.databinding.FragmentFirstBinding
import com.example.lendify.databinding.FragmentSecondBinding
import com.example.lendify.databinding.FragmentThirdBinding

class thirdFragment : Fragment(R.layout.fragment_third) {

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
        val myDataset = Datasource().loadItems()
        binding.recyclerView.adapter = ItemAdapter(myDataset)

        binding.buttonAdd.setOnClickListener{
            val intent = Intent(context,UploadActivity::class.java)
            startActivity(intent)
        }


    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}