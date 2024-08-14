package com.example.myapplication.repository

import com.example.myapplication.api.RetrofitInstance
import com.example.myapplication.model.Order
import com.example.myapplication.model.User

class NewsRemoteRepository {
    suspend fun getAllFood() =
        RetrofitInstance.api.getAllFood()

//    suspend fun getAllFood(): Response<List<Food>> {
//        return RetrofitInstance.api.getAllFood()
//    }

    suspend fun getNewFood()=RetrofitInstance.api.getNewFood()
    suspend fun getUserDetail(userId:String)=RetrofitInstance.api.getUser(userId)
    suspend fun createUser(user: User)=RetrofitInstance.api.createUser(user)

    suspend fun addNewOrder(order: Order)=RetrofitInstance.api.newOrder(order)
    suspend fun getOrder(userId:String)=RetrofitInstance.api.getOrder(userId)
}