package com.example.lendify.model

data class Items(val text :String ?=null,
                 val bild: String ?=null,
                 val user:String ?=null,
                 val price: Int ?=null,
                 val id: String?=null,
                    val userID: String? = "") {
}