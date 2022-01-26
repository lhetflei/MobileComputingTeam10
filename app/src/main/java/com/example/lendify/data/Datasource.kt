package com.example.lendify.data

import com.example.lendify.R
import com.example.lendify.model.Items

class Datasource {

    fun loadItems():List<Items>{
        return listOf<Items>(
                    Items(R.string.test1,R.drawable.rwb),
                    Items(R.string.test2,R.drawable.ic_launcher_background),
                    Items(R.string.test3,R.drawable.ic_settings)
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