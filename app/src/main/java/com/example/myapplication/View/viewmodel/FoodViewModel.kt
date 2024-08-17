package com.example.myapplication.View.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.ResponseHandle.ResponseHandler
import com.example.myapplication.model.Food
import com.example.myapplication.repository.NewsRemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class FoodViewModel(app: Application, private val newsRemoteRepository: NewsRemoteRepository) : AndroidViewModel(app) {

    val listFoodLiveData = MutableLiveData<List<Food>>()
    val listNewFoodLiveData = MutableLiveData<List<Food>>()

    val errorLiveData = MutableLiveData<String>()
    val _count = MutableLiveData<Int>() 
    val count: LiveData<Int> get() = _count

    fun setCount(newCount: Int) {
        _count.value = newCount
    }

    init{
        getAllFood()
        getNewFood()
    }


    fun getAllFood() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    newsRemoteRepository.getAllFood()
                }
                ResponseHandler.handleResponse(response, {
                    listFoodLiveData.value = it.data ?: emptyList()
                }, { error ->
                    errorLiveData.value = error
                })
            } catch (e: IOException) {
                e.printStackTrace()
                errorLiveData.value = "Network Error: ${e.message}"
            } catch (e: Exception) {
                e.printStackTrace()
                errorLiveData.value = "Exception: ${e.message}"
            }
        }
    }

    fun getNewFood() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    newsRemoteRepository.getNewFood()
                }
                ResponseHandler.handleResponse(response, {
                    listNewFoodLiveData.value = it.data ?: emptyList()
                }, { error ->
                    errorLiveData.value = error
                })
            } catch (e: Exception) {
                e.printStackTrace()
                errorLiveData.value = "Exception: ${e.message}"
            }
        }
    }



}
