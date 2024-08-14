package com.example.myapplication.model

import java.io.Serializable
import java.time.LocalDateTime

data class OrderDTO (
    val id:Int,
    val foodName:String,
    val total :Int,
    val quantity:Int,
    val image:String,
    val state:String,
    val localDateTime: LocalDateTime
): Serializable