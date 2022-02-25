package com.example.lendify.data

import android.content.ContentValues.TAG
import android.util.Log
import com.example.lendify.R
import com.example.lendify.model.Items
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.delay
import kotlinx.coroutines.*

class Datasource {

    private var database = FirebaseDatabase.getInstance("https://lendify-6cd5f-default-rtdb.europe-west1.firebasedatabase.app").getReference("angebot")
    private var database_user = FirebaseDatabase.getInstance("https://lendify-6cd5f-default-rtdb.europe-west1.firebasedatabase.app").getReference("user")
    var username: String = ""
    var itemlist = arrayListOf<Items>()
    var itemlist2 = arrayListOf<Items>()
    private var ref = FirebaseAuth.getInstance()
    fun loadItems():ArrayList<Items> {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (itemSnapshot in snapshot.children) {

                        itemlist.add(
                            Items(
                                itemSnapshot.child("text").value.toString(),
                                itemSnapshot.child("bild").value.toString(),
                                itemSnapshot.child("user").value.toString(),
                                itemSnapshot.child("price").value.toString().toInt(),
                                itemSnapshot.child("id").value.toString(),
                                itemSnapshot.child("userID").value.toString()
                            )
                        )
                        //Log.i(TAG, itemlist.toString())
                    }

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        //itemlist.add(Items(null, "Bilder/rwb2.jpg", null))
        //itemlist.add(Items("testtext1","Bilder/rwb2.jpg",15))
        // itemlist.add(Items("testtext1","Bilder/rwb2.jpg",10))
        //itemlist.add(Items("testtext1","Bilder/2022_02_16_20_16_55",15))
        //itemlist.add(Items("testtext1","Bilder/2022_02_16_20_30_13",15))

        /* database.child("1").get().addOnSuccessListener {
            itemlist.add(Items(it.child("text").value.toString(),it.child("bild").value.toString(),it.child("price").value.toString().toInt()))
            Log.i(TAG, itemlist.toString())

        }
        Log.i(TAG, itemlist.toString())*/

        /*return arrayListOf<Items>(
            Items("text","Bilder/rwb2.jpg",10),
        )*/
        return itemlist
    }

        fun loadItems2(): ArrayList<Items> {

            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (itemSnapshot in snapshot.children) {
                            if(itemSnapshot.child("user").value.toString()==ref.currentUser!!.email.toString())
                            itemlist2.add(
                                Items(
                                    itemSnapshot.child("text").value.toString(),
                                    itemSnapshot.child("bild").value.toString(),
                                    itemSnapshot.child("user").value.toString(),
                                    itemSnapshot.child("price").value.toString().toInt(),

                                )
                            )

                            Log.i(TAG, itemlist2.toString())
                            Log.i(TAG, itemSnapshot.child("user").value.toString())

                        }

                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })


            return itemlist2
        }
    }