package com.example.myapplication.View.Fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Food
import com.example.myapplication.repository.NewsRemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FoodViewModel(app: Application, private val newsRemoteRepository: NewsRemoteRepository) : AndroidViewModel(app) {

    val foodLiveData = MutableLiveData<List<Food>>()
    val errorLiveData = MutableLiveData<String>()

    fun fetchAllFood() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    newsRemoteRepository.getAllFood()
                }

                if (response.isSuccessful) {
                    foodLiveData.value = response.body() ?: emptyList()
                } else {
                    errorLiveData.value = "Error: ${response.code()}"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                errorLiveData.value = "Exception: ${e.message}"
            }
        }
    }
}
