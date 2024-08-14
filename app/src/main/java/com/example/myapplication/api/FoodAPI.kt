package com.example.myapplication.api

import com.example.myapplication.model.Food
import com.example.myapplication.model.FoodReponse
import com.example.myapplication.model.Order
import com.example.myapplication.model.OrderDTO
import com.example.myapplication.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FoodAPI {
    @GET("food/getallfood")
    suspend fun getAllFood(): Response<FoodReponse<List<Food>>>

    @GET("food/getnewfood")
    suspend fun getNewFood(): Response<FoodReponse<List<Food>>>

    @POST("order/neworder")
    suspend fun newOrder(@Body order:Order):Response<FoodReponse<Order>>

    @GET("order/getorder/{userId}")
    suspend fun getOrder(@Path("userId") userId:String):Response<FoodReponse<List<OrderDTO>>>

    @GET("user/getuser/{userId}")
    suspend fun getUser(@Path("userId") userId:String):Response<FoodReponse<User>>

    @POST("user/createuser")
    suspend fun createUser(@Body user: User):Response<FoodReponse<Unit>>
}
