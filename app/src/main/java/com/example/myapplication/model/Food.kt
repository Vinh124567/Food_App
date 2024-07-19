package com.example.myapplication.model

import java.io.Serializable

data class Food(
        val id: String,
        val name: String,
        val image: String,
        val price:Int,
        val description:String
    ): Serializable