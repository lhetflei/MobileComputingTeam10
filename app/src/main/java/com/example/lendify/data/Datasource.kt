package com.example.lendify.data

import android.content.ContentValues.TAG
import android.util.Log
import com.example.lendify.model.Items
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Datasource {

    private var database = FirebaseDatabase.getInstance("https://lendify-6cd5f-default-rtdb.europe-west1.firebasedatabase.app").getReference("angebot")

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

                    }

                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
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
                                    itemSnapshot.child("id").value.toString()

                                )
                            )

                            Log.i(TAG, itemlist2.toString())
                            Log.i(TAG, itemSnapshot.child("user").value.toString())

                        }

                    }

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })


            return itemlist2
        }
    }