package com.example.myapplication.View.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.api.RetrofitInstance
import com.example.myapplication.model.Food
import com.example.myapplication.model.FoodReponse
import com.example.myapplication.model.Order
import com.example.myapplication.model.User
import com.example.myapplication.repository.NewsRemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FoodViewModel(app: Application, private val newsRemoteRepository: NewsRemoteRepository) : AndroidViewModel(app) {

    val foodLiveData = MutableLiveData<List<Food>>()
    val newFoodLiveData = MutableLiveData<List<Food>>()

    val newOrderResult: MutableLiveData<FoodReponse<Order>> = MutableLiveData()

    val errorLiveData = MutableLiveData<String>()
    val _loginResult = MutableLiveData<User>()



    private val foodDetail = MutableLiveData<Food>()
    val food: LiveData<Food> get() = foodDetail

    private val _count = MutableLiveData<Int>()
    val count: LiveData<Int> get() = _count

    fun setFood(newFood: Food) {
        foodDetail.value = newFood
    }

    fun setCount(newCount: Int) {
        _count.value = newCount
    }

    fun newOrder(order: Order) {
        viewModelScope.launch {
            try {
                val response =withContext(Dispatchers.IO) {
                    RetrofitInstance.api.newOrder(order)
                }
                if (response.isSuccessful && response.body() != null) {
                    newOrderResult.postValue(response.body())
                } else {
                    errorLiveData.postValue("Error: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                errorLiveData.postValue("Exception: ${e.message}")
            }
        }
    }

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
