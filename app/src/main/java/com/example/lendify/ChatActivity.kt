package com.example.lendify

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lendify.adapter.MessagesAdapter2
import com.example.lendify.adapter.UserAdapter
import com.example.lendify.databinding.ActivityChatBinding
import com.example.lendify.databinding.ActivityPersonalBinding
import com.example.lendify.model.Messages
import com.example.lendify.model.User
import com.google.api.Distribution
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase


class ChatActivity : AppCompatActivity() {

    lateinit var binding: ActivityChatBinding
    var firebaseUser: FirebaseUser? = null
    var databaseReference: DatabaseReference? = null
    private var messageList = ArrayList<Messages>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Get receiverID from UserAdapter
        var intent = getIntent()
        var userID = intent.getStringExtra("userID")
        firebaseUser = FirebaseAuth.getInstance().currentUser
        databaseReference = FirebaseDatabase.getInstance("https://lendify-6cd5f-default-rtdb.europe-west1.firebasedatabase.app/").getReference("user").child(userID!!)


        //Refresh Data
        databaseReference!!.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                supportActionBar?.title = user!!.userName
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        //Show messages in chat
        msg_show(firebaseUser!!.uid, userID)
        //Send message button
        binding.imageviewSend.setOnClickListener{
            if (binding.textMessage.text.isNotEmpty()) {
                var message = binding.textMessage.text.toString().trim()
                msg_send(message, userID, firebaseUser!!.uid)
                binding.textMessage.setText(null)
            }
            else {
                Toast.makeText(this, "Geben Sie zuerst eine Nachricht ein...", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun msg_show(receiverID: String, senderID: String) {
        //Refresh Data
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance("https://lendify-6cd5f-default-rtdb.europe-west1.firebasedatabase.app/").getReference("messages")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()

                //TODO Bild ändern

                for (dataSnapShot: DataSnapshot in snapshot.children) {
                    val message = dataSnapShot.getValue(Messages::class.java)

                    //if (user!!.userID != firebase.uid) {
                    if (message!!.receiverID.equals(receiverID) && message.senderID.equals(senderID) || message!!.receiverID.equals(senderID) && message!!.senderID.equals(receiverID))  {
                        messageList.add(message)
                    }
                }
                Log.i(TAG, messageList.toString())
                val message_a = MessagesAdapter2(this@ChatActivity, messageList)
                binding.recyclerViewMessages.adapter = message_a
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ChatActivity,"Error", Toast.LENGTH_SHORT).show()
            }
        })
    }
    //Upload message zo firebase
    private fun msg_send(message: String, receiverID: String, senderID: String) {
            var msg_send_database:DatabaseReference = FirebaseDatabase.getInstance("https://lendify-6cd5f-default-rtdb.europe-west1.firebasedatabase.app/").getReference()

            var hashMap: HashMap<String, String> = HashMap()
            hashMap.put("message", message)
            hashMap.put("receiverID", receiverID)
            hashMap.put("senderID", senderID)
            msg_send_database!!.child("messages").push().setValue(hashMap)
    }
}