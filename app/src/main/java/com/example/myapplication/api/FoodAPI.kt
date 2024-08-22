package com.example.myapplication.api

import com.example.myapplication.model.Food
import com.example.myapplication.model.FoodResponse
import com.example.myapplication.model.Order
import com.example.myapplication.model.OrderDTO
import com.example.myapplication.model.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface FoodAPI {
    @GET("food/getallfood")
    suspend fun getAllFood(): Response<FoodResponse<List<Food>>>

    @GET("food/getnewfood")
    suspend fun getNewFood(): Response<FoodResponse<List<Food>>>

    @POST("order/neworder")
    suspend fun newOrder(@Body order:Order):Response<FoodResponse<Order>>

    @GET("order/getorder/{userId}")
    suspend fun getOrder(@Path("userId") userId:String):Response<FoodResponse<List<OrderDTO>>>

    @GET("user/getuser/{userId}")
    suspend fun getUser(@Path("userId") userId:String):Response<FoodResponse<User>>

    @Multipart
    @POST("user/upload")
    suspend fun createUser(
        @Part("user") user: RequestBody,
        @Part image: MultipartBody.Part?
    ): Response<FoodResponse<Unit>>


}
