package com.example.myapplication.model

data class FoodResponse<T>(
    val statusCode: Int,
    val message: String,
    val data: T?= null
)