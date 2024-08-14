package com.example.myapplication.model

data class Order(
    var id:Int,
    var userId: String?,
    var foodId:Int,
    var quantity:Int,
    var state:String,
    var total:Int,
)