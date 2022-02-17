package com.example.lendify.adapter

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.lendify.PersonalActivity
import com.example.lendify.R
import com.example.lendify.model.Items
import com.example.lendify.secondFragment
import com.example.lendify.thirdFragment
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.okhttp.Dispatcher
import kotlinx.coroutines.*
import java.io.File

class ItemAdapter(
    private val dataset: ArrayList<Items>
    ) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    val localfile = File.createTempFile("tempImage","jpg")

    class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        val title :TextView =view.findViewById(R.id.item_title)
        val price :TextView =view.findViewById(R.id.item_price)
        val image :ImageView =view.findViewById(R.id.item_image)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ItemAdapter.ItemViewHolder(adapterLayout)
    }

    override fun getItemCount() = dataset.size



    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

            val item = dataset[position]
            holder.title.text = item.title
            holder.price.text = item.price.toString()
            var storageRef = FirebaseStorage.getInstance().reference.child(item.image.toString())
            storageRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                holder.image.setImageBitmap(bitmap)
            }

    }
}