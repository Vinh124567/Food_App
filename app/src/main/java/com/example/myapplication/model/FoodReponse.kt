package com.example.myapplication.model

data class FoodReponse<T>(
    val statusCode: Int,
    val message: String,
    val data: T?= null
)