package com.example.myapplication.View.viewmodel.ViewModelProvider

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.View.viewmodel.AuthViewModel
import com.example.myapplication.View.viewmodel.FoodViewModel
import com.example.myapplication.View.viewmodel.OrderViewModel
import com.example.myapplication.repository.NewsRemoteRepository


class NewsViewModelProviderFactory(
    val app: Application,
    private val newsRemoteRepository: NewsRemoteRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(FoodViewModel::class.java) -> {
                FoodViewModel(app, newsRemoteRepository) as T
            }
            modelClass.isAssignableFrom(OrderViewModel::class.java) -> {
                OrderViewModel(app, newsRemoteRepository) as T
            }
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                AuthViewModel(app, newsRemoteRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
