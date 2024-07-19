package com.example.myapplication.repository

import com.example.myapplication.api.RetrofitInstance

class NewsRemoteRepository {

    suspend fun getAllFood() =
        RetrofitInstance.api.getAllFood()
}