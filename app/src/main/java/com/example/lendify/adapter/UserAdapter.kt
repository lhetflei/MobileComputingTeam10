package com.example.lendify.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lendify.ChatActivity
import com.example.lendify.R
import com.example.lendify.fourthFragment
import com.example.lendify.model.User
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File
import java.lang.Exception

val localfile = File.createTempFile("tempImage","jpg")

class UserAdapter(private val add_user: Context, private val userList: ArrayList<User>): RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var listedUser: ConstraintLayout = view.findViewById(R.id.listed_user)
        val user_avatar: CircleImageView = view.findViewById(R.id.user_avatar)
        val user_name: TextView = view.findViewById(R.id.user_name)
        val user_last_msg: TextView = view.findViewById(R.id.user_last_msg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_user_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.user_name.text = user.userName
        Glide.with(add_user).load(user.userAvatar).placeholder(R.drawable.ic_baseline_person_24)

        //User avatar
        if (user.userAvatar != "") {
            var storageRef = FirebaseStorage.getInstance().reference.child(user.userAvatar.toString())
            storageRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                try {
                    holder.user_avatar.setImageBitmap(Bitmap.createScaledBitmap(bitmap,550, 500, true))
                }
                catch(e: Exception)
                {
                    holder.user_avatar.setImageBitmap(bitmap)
                }
            }
        }

        //Get receiverID for message
        holder.listedUser.setOnClickListener(){
            val intent = Intent(add_user, ChatActivity::class.java)
            intent.putExtra("userID", user.userID)
            add_user.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}