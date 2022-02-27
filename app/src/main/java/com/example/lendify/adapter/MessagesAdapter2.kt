package com.example.lendify.adapter

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lendify.R
import com.example.lendify.model.Messages
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.File


class MessagesAdapter2(private val add_message: Context, private val messageList :ArrayList<Messages>): RecyclerView.Adapter<MessagesAdapter2.ViewHolder>() {

    val MSG_RECEIVE_CODE = 1
    val MSG_SENT_CODE = 2
    var firebaseUser: FirebaseUser? = null
    var databaseReference: DatabaseReference? = null
    var avatar_databaseReference: DatabaseReference? = null
    var bool_receive: Boolean? = null
    var ImageUri: Uri? = null
    val localfile = File.createTempFile("tempImage","jpg")

    /*class ViewHolderSend(itemView: View): RecyclerView.ViewHolder(itemView) {
        val chat_message = itemView.findViewById<TextView>(R.id.message_rows_sent)
        val profil_image = itemView.findViewById<ImageView>(R.id.message_rows_sent_imageview)
    }

    class ViewHolderReceive(itemView: View): RecyclerView.ViewHolder(itemView) {
        val chat_message = itemView.findViewById<TextView>(R.id.message_rows_receive)
        val profil_image = itemView.findViewById<ImageView>(R.id.message_rows_receive_imageview)
    }*/

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val msg_receive = itemView.findViewById<TextView>(R.id.message_rows_receive)
        val avatar_receive = itemView.findViewById<ImageView>(R.id.message_rows_receive_imageview)
        val msg_send = itemView.findViewById<TextView>(R.id.message_rows_sent)
        val avatar_send = itemView.findViewById<ImageView>(R.id.message_rows_sent_imageview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == MSG_RECEIVE_CODE){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_rows_receive, parent,false)
            bool_receive = true
            return ViewHolder(view)
        }
        else
        {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_rows_sent, parent,false)
            bool_receive = false
            return ViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        databaseReference = FirebaseDatabase.getInstance("https://lendify-6cd5f-default-rtdb.europe-west1.firebasedatabase.app/").getReference()
        avatar_databaseReference = FirebaseDatabase.getInstance("https://lendify-6cd5f-default-rtdb.europe-west1.firebasedatabase.app/").getReference("user")
        val MSG = messageList[position]
        Log.i(TAG, MSG.toString())
        if (firebaseUser!!.uid.toString() == MSG.receiverID.toString()) {
            holder.msg_receive.text = MSG.message
            //Glide.with(add_message).load(holder.avatar_receive).placeholder(R.drawable.ic_baseline_person_24)

            if(MSG.userAvatar != "") {
                var storageRef =
                    FirebaseStorage.getInstance().reference.child(MSG.userAvatar.toString())
                storageRef.getFile(localfile).addOnSuccessListener {
                    val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                    try {
                        holder.avatar_receive.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 550, 500, true))
                    } catch (e: Exception) {
                        Glide.with(add_message).load(R.drawable.ic_baseline_person_24).into(holder.avatar_receive)
                    }
                }


                /*try {
                    holder.avatar_receive.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 550, 500, true))
                } catch (e: Exception) {
                    Glide.with(add_message).load(holder.avatar_receive).placeholder(R.drawable.ic_baseline_person_24)
                }*/

                /*else {
                    Glide.with(add_message).load(holder.avatar_receive).placeholder(R.drawable.ic_baseline_person_24)
                }*/
            }
        }
        else {
            holder.msg_send.text = MSG.message

            if(MSG.userAvatar != "") {
                var storageRef =
                    FirebaseStorage.getInstance().reference.child(MSG.userAvatar.toString())
                storageRef.getFile(localfile).addOnSuccessListener {
                    val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                    try {
                        holder.avatar_send.setImageBitmap(
                            Bitmap.createScaledBitmap(
                                bitmap,
                                550,
                                500,
                                true
                            )
                        )
                    } catch (e: Exception) {
                        Glide.with(add_message).load(holder.avatar_send).placeholder(R.drawable.ic_baseline_person_24)
                    }
                }

                /*else {
                    Glide.with(add_message).load(holder.avatar_receive).placeholder(R.drawable.ic_baseline_person_24)
                }*/
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        firebaseUser = FirebaseAuth.getInstance().currentUser
        if (messageList[position].senderID == firebaseUser!!.uid) {
            return MSG_SENT_CODE
        }
        else {
            return MSG_RECEIVE_CODE
        }

    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    private fun get_avatar() {
        databaseReference = FirebaseDatabase.getInstance("https://lendify-6cd5f-default-rtdb.europe-west1.firebasedatabase.app/").getReference()
        databaseReference!!.child(firebaseUser!!.uid.toString()).get().addOnSuccessListener {
            var image = it.value.toString()
            var storageRef = FirebaseStorage.getInstance().reference.child(image)
            storageRef.getFile(localfile).addOnCanceledListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)

            }
        }
    }
}