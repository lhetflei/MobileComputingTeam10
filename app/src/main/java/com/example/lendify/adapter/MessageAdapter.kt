package com.example.lendify.adapter
import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import com.example.lendify.model.Messages

import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.lendify.ChatActivity
import com.example.lendify.R

class MessageAdapter (var context: Context,
    messages: ArrayList<Messages>,
    sender: String,
    receiver: String
):RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    lateinit var messages: ArrayList<Messages>
    val MSG_SENT = 1
    val MSG_RECEIVE = 2
    var sender: String? = null
    var receiver: String? = null

    inner class SentMsgHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
        //var binding: SendMsgBinding = SendMsgBinding.bind(itemView)
    }
    inner class ReceiveMsgHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        //var binding: SendMsgBinding = SendMsgBinding.bind(itemView)
    }
    init {
        if (messages != null) {
            this.messages = messages
        }
        this.sender = sender
        this.receiver = receiver
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType) {
            MSG_SENT -> {
                val view =
                    LayoutInflater.from(context).inflate(R.layout.chat_rows_sent, parent, false)
                return SentMsgHolder(view)
            }
            MSG_RECEIVE -> {
                val view =
                    LayoutInflater.from(context).inflate(R.layout.chat_rows_receive, parent, false)
                return ReceiveMsgHolder(view)
            }
            /*else -> {
                //Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
                return null
            }*/

            else -> {
                return SentMsgHolder(null)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}