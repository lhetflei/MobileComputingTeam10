package com.example.lendify.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lendify.ChatActivity
import com.example.lendify.R
import com.example.lendify.fourthFragment
import com.example.lendify.model.Items
import com.example.lendify.model.User
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File
import java.lang.Exception

class ItemAdapter3(private val add_item: Context, private val dataset: ArrayList<Items>): RecyclerView.Adapter<ItemAdapter3.ViewHolder>() {

    val localfile = File.createTempFile("tempImage","jpg")
    //val item = dataset[0]

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val text :TextView =view.findViewById(R.id.item_title)
        val price :TextView =view.findViewById(R.id.item_price)
        val bild : ImageView =view.findViewById(R.id.item_image)
        val name: TextView = view.findViewById(R.id.text_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item3, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataset[position]
        holder.text.text = item.text
        //holder.price.text = item.price.toString()+"€/Tag"
        holder.price.text = item.price.toString()
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
    override fun getItemCount(): Int {
        return dataset.size
    }
}