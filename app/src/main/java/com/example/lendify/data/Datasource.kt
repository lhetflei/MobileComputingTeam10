package com.example.lendify.data

import android.content.ContentValues.TAG
import android.util.Log
import com.example.lendify.R
import com.example.lendify.model.Items
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class Datasource {


    fun loadItems():List<Items>{

        return listOf<Items>(
            Items(R.string.test1,R.drawable.rwb),
            Items(R.string.test1,R.drawable.ic_settings),
            Items(R.string.test1,R.drawable.ic_launcher_background),
            Items(R.string.test1,R.drawable.rwb)

        )
    }
    fun loadItems2():List<Items>{
        return listOf<Items>(
            Items(R.string.test1,R.drawable.rwb),
            Items(R.string.test2,R.drawable.rwb),
            Items(R.string.test3,R.drawable.rwb)
        )
    }
}