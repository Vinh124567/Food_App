package com.example.myapplication.View.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.repository.NewsRemoteRepository

class NewsViewModelProviderFactory(
    val app: Application, private val newsRemoteRepository: NewsRemoteRepository, ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FoodViewModel(app,newsRemoteRepository) as T
    }
}