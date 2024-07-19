package com.example.myapplication.View

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.View.Fragment.FoodViewModel
import com.example.myapplication.View.Fragment.NewsViewModelProviderFactory
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.repository.NewsRemoteRepository

class MainActivity : AppCompatActivity() {
    lateinit var foodViewModel: FoodViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsRemoteRepository = NewsRemoteRepository()
        val viewModelProviderFactory =
            NewsViewModelProviderFactory(application, newsRemoteRepository)

        foodViewModel = ViewModelProvider(this, viewModelProviderFactory)[FoodViewModel::class.java]

        }
    }
