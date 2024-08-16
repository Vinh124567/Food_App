package com.example.myapplication.View

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.View.viewmodel.AuthViewModel
import com.example.myapplication.View.viewmodel.FoodViewModel
import com.example.myapplication.View.viewmodel.ViewModelProvider.NewsViewModelProviderFactory
import com.example.myapplication.View.viewmodel.OrderViewModel
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.repository.NewsRemoteRepository

class MainActivity : AppCompatActivity() {
    lateinit var foodViewModel: FoodViewModel
    lateinit var orderViewModel: OrderViewModel
    lateinit var authViewModel: AuthViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val newsRemoteRepository = NewsRemoteRepository()
        val viewModelProviderFactory =
            NewsViewModelProviderFactory(application, newsRemoteRepository)


        foodViewModel = ViewModelProvider(this, viewModelProviderFactory)[FoodViewModel::class.java]
        orderViewModel = ViewModelProvider(this, viewModelProviderFactory)[OrderViewModel::class.java]
        authViewModel = ViewModelProvider(this, viewModelProviderFactory)[AuthViewModel::class.java]

        }
}
