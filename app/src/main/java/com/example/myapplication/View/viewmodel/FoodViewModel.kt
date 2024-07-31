package com.example.myapplication.View.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Food
import com.example.myapplication.model.User
import com.example.myapplication.repository.NewsRemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FoodViewModel(app: Application, private val newsRemoteRepository: NewsRemoteRepository) : AndroidViewModel(app) {

    val foodLiveData = MutableLiveData<List<Food>>()
    val newFoodLiveData = MutableLiveData<List<Food>>()
    val errorLiveData = MutableLiveData<String>()
    val _loginResult = MutableLiveData<User>()

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

    fun fetchNewFood() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    newsRemoteRepository.getNewFood()
                }
                if (response.isSuccessful) {
                    newFoodLiveData.value = response.body() ?: emptyList()
                } else {
                    errorLiveData.value = "Error: ${response.code()}"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                errorLiveData.value = "Exception: ${e.message}"
            }
        }
    }

    fun requestLogin(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = newsRemoteRepository.login(email, password)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _loginResult.value = it
                    } ?: run {
                        errorLiveData.value = "Login failed: Response body is null"
                    }
                } else {
                    errorLiveData.value = response.errorBody()?.string() ?: "Unknown error"
                }
            } catch (e: Exception) {
                errorLiveData.value = "Exception: ${e.message}"
            }
        }
    }
    
}
