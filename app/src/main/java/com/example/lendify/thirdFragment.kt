package com.example.lendify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
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

        val images = listOf<Image>(
            Image("Images 1", R.drawable.bild),
            Image("Images 2", R.drawable.bild2),
            Image("Images 3", R.drawable.bild3),

        )

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = ImageAdapter(this, images)


    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}