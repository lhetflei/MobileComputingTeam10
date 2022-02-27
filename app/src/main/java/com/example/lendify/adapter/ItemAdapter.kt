package com.example.lendify.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lendify.R
import com.example.lendify.model.Items
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class ItemAdapter(
    private val dataset: ArrayList<Items>
    ) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    val localfile = File.createTempFile("tempImage","jpg")
    val item = dataset[0]
    class ItemViewHolder(private val view: View,listener:onItemClickListener): RecyclerView.ViewHolder(view){
        val text :TextView =view.findViewById(R.id.item_title)
        val price :TextView =view.findViewById(R.id.item_price)
        val bild :ImageView =view.findViewById(R.id.item_image)
        val name: TextView = view.findViewById(R.id.text_name)

        init{
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
    private lateinit var mListener:onItemClickListener
    interface onItemClickListener {
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener:onItemClickListener){
        mListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item3, parent, false)
        return ItemAdapter.ItemViewHolder(adapterLayout,mListener)
    }

    override fun getItemCount() = dataset.size



    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.text.text = item.text
        holder.price.text = item.price.toString()+"€/Tag"
        holder.name.text = item.userID.toString()

            var storageRef = FirebaseStorage.getInstance().reference.child(item.bild.toString())
            storageRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                try {
                    holder.bild.setImageBitmap(Bitmap.createScaledBitmap(bitmap,550, 500, true))
                }
                catch(e:Exception)
                {
                    holder.bild.setImageBitmap(bitmap)
                }

            }


    }
}