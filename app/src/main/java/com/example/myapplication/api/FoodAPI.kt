package com.example.myapplication.api

import com.example.myapplication.model.Food
import retrofit2.Response
import retrofit2.http.GET

interface FoodAPI {

    @GET("foodname")
    suspend fun getAllFood(): Response<List<Food>>
}
