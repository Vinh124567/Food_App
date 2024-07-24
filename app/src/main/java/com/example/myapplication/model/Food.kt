package com.example.myapplication.model

import java.io.Serializable
import java.time.LocalDateTime

data class Food(
        val id: String,
        val name: String,
        val image: String,
        val price:Int,
        val description:String,
        val createdAt: LocalDateTime
    ): Serializable