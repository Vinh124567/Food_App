//package com.example.myapplication.api
//
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//class RetrofitInstance {
//    companion object {
//        private val retrofit by lazy {
//            val logging = HttpLoggingInterceptor()
//            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
//            val client = OkHttpClient.Builder()
//                .addInterceptor(logging)
//                .build()
//
//            Retrofit.Builder()
//                .baseUrl("http://10.0.2.2:8081")
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build()
//        }
//
//        val api by lazy {
//            retrofit.create(FoodAPI::class.java)
//        }
//    }
//}

package com.example.myapplication.api

import com.example.myapplication.adapter.LocalDateTimeAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime

class RetrofitInstance {
    companion object {
        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            val gson: Gson = GsonBuilder()
                .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
                .create()

            Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8081")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
        }

        val api by lazy {
            retrofit.create(FoodAPI::class.java)
        }
    }
}
