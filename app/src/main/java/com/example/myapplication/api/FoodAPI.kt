package com.example.myapplication.api

import com.example.myapplication.model.Food
import com.example.myapplication.model.LoginRequest
import com.example.myapplication.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface FoodAPI {
    @GET("foodname")
    suspend fun getAllFood(): Response<List<Food>>

    @POST("userinfor/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<User>
}
