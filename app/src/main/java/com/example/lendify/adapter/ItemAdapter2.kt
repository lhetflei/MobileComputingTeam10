package com.example.lendify.adapter

import android.app.Activity
import android.content.Context
import android.service.autofill.Dataset
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

class ItemAdapter2(
    private val context: thirdFragment,
    private val dataset: List<Items>
    ) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {



    class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        val textView: TextView =view.findViewById(R.id.item_title)
        val imageView: ImageView = view.findViewById(R.id.item_image)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ItemAdapter.ItemViewHolder(adapterLayout)
    }

    override fun getItemCount() = dataset.size



    override fun onBindViewHolder(holder: ItemAdapter.ItemViewHolder, position: Int) {
        val item = dataset[position]
        //holder.textView.text = context.resources.getString(item.stringResourceId)
       // holder.imageView.setImageResource(item.imageResourceId)
    }
}