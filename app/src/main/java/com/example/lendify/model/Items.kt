package com.example.lendify.model
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Items(val text :String ?=null,
                 val bild: String ?=null,
                 val user:String ?=null,
                 val price: Int ?=null,
                 val id: String?=null) {
}