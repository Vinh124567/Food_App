package com.example.myapplication.View.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.FoodResponse
import com.example.myapplication.model.Order
import com.example.myapplication.model.OrderDTO
import com.example.myapplication.repository.NewsRemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderViewModel(app: Application, private val newsRemoteRepository: NewsRemoteRepository) : AndroidViewModel(app) {

    val errorLiveData = MutableLiveData<String>()
    val newOrderResult: MutableLiveData<FoodResponse<Order>> = MutableLiveData()
    val listOrderResult: MutableLiveData<List<OrderDTO>> = MutableLiveData()

    fun addNewOrder(order: Order) {
        viewModelScope.launch {
            try {
                val response =withContext(Dispatchers.IO) {
                    newsRemoteRepository.addNewOrder(order)
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

    fun getOrder(userId:String){
        viewModelScope.launch {
            try {
                val response =withContext(Dispatchers.IO) {
                    newsRemoteRepository.getOrder(userId)
                }
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if(responseBody!=null) {
                        listOrderResult.value = responseBody.data ?: emptyList()
                    }
                } else {
                    errorLiveData.postValue("Error: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                errorLiveData.postValue("Exception: ${e.message}")
            }
        }
    }
}
