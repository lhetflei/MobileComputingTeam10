package com.example.lendify.data

import com.example.lendify.R
import com.example.lendify.model.Items

class Datasource {

    fun loadItems():List<Items>{
        return listOf<Items>(
                    Items(R.string.test1),
                    Items(R.string.test2),
                    Items(R.string.test3)
        )
    }
}