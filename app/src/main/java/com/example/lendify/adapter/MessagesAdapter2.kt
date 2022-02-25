package com.example.lendify.adapter

import android.content.ContentValues.TAG
import android.content.Context
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lendify.R
import com.example.lendify.model.Messages
import com.example.lendify.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase

class MessagesAdapter2(private val add_message: Context, private val messageList :ArrayList<Messages>): RecyclerView.Adapter<MessagesAdapter2.ViewHolder>() {

    val MSG_RECEIVE_CODE = 1
    val MSG_SENT_CODE = 2
    var firebaseUser: FirebaseUser? = null
    var bool_receive: Boolean? = null

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
        //holder.messages_received.text = "${content[position].message}"
        //val message = messages[position]
        val MSG = messageList[position]
        Log.i(TAG, MSG.toString())
        if (firebaseUser!!.uid.toString() == MSG.receiverID.toString()) {
            holder.msg_receive.text = MSG.message
        }
        else {
            holder.msg_send.text = MSG.message
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
}