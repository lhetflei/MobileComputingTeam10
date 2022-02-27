package com.example.lendify

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lendify.adapter.ItemAdapter
import com.example.lendify.data.Datasource
import com.example.lendify.databinding.FragmentSecondBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class secondFragment : Fragment(R.layout.fragment_second) {
    private var _binding: FragmentSecondBinding?=null
    private val binding get()=_binding!!
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentSecondBinding.inflate(inflater,container,false)
        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //RecyclerView.LayoutManager
        GlobalScope.launch(Dispatchers.Main) {
            val myDataset = Datasource().loadItems()
            delay(350) //warten auf datenbank
            try {

                //getItemList()
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
                delay(500)
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