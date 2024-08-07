package com.example.myapplication.model

data class Order (
     var id:Int,
    var userId:Int,
    var foodId:Int,
    var quantity:Int,
    var status:String,
    var total:Int,
)