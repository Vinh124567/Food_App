package com.example.myapplication.repository

import com.example.myapplication.api.RetrofitInstance
import com.example.myapplication.model.LoginRequest
import com.example.myapplication.model.User
import retrofit2.Response

class NewsRemoteRepository {
    suspend fun getAllFood() =
        RetrofitInstance.api.getAllFood()

//    suspend fun getAllFood(): Response<List<Food>> {
//        return RetrofitInstance.api.getAllFood()
//    }

    suspend fun login(email: String, password: String): Response<User> {
        val loginRequest = LoginRequest(email, password)
        return RetrofitInstance.api.login(loginRequest)
    }


    suspend fun getNewFood()=RetrofitInstance.api.getNewFood()
}