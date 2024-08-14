package com.example.myapplication.View.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Firebase.FirebaseAuthManager
import com.example.myapplication.GoogleSigning.GoogleSignInManager
import com.example.myapplication.model.Food
import com.example.myapplication.model.FoodReponse
import com.example.myapplication.model.Order
import com.example.myapplication.model.OrderDTO
import com.example.myapplication.model.User
import com.example.myapplication.repository.NewsRemoteRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FoodViewModel(app: Application, private val newsRemoteRepository: NewsRemoteRepository) : AndroidViewModel(app) {

    private lateinit var googleSignInManager: GoogleSignInManager
    private lateinit var firebaseAuthManager: FirebaseAuthManager

    val listFoodLiveData = MutableLiveData<List<Food>>()
    val listNewFoodLiveData = MutableLiveData<List<Food>>()

    val newOrderResult: MutableLiveData<FoodReponse<Order>> = MutableLiveData()
    val listOrderResult: MutableLiveData<List<OrderDTO>> = MutableLiveData()


    val errorLiveData = MutableLiveData<String>()
    val successLiveData= MutableLiveData<String>()

    val emailResult = MutableLiveData<FirebaseUser?>()

    val userDetail=MutableLiveData<User?>()

    val googleSignInResult = MutableLiveData<Pair<FirebaseUser?,Boolean>>()
    val signInError = MutableLiveData<String?>()

    fun initializeManagers(context: Context) {
        googleSignInManager = GoogleSignInManager(context)
        firebaseAuthManager = FirebaseAuthManager()
    }


    private val _count = MutableLiveData<Int>()
    val count: LiveData<Int> get() = _count


    fun setCount(newCount: Int) {
        _count.value = newCount
    }

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

    fun getAllFood() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    newsRemoteRepository.getAllFood()
                }
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        listFoodLiveData.value = responseBody.data ?: emptyList()
                    } else {
                        errorLiveData.value = "No food found"
                    }
                } else {
                    errorLiveData.value = "Error: ${response.message()}"
                }
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
                if (response.isSuccessful) {
                    response.body()?.let { responseSuccess ->
                        val foodList = responseSuccess.data ?: emptyList()
                        listNewFoodLiveData.value = foodList
                    }
                } else {
                    errorLiveData.value = "Error: ${response.message()}"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                errorLiveData.value = "Exception: ${e.message}"
            }
        }
    }



    fun createUser(user: User) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    newsRemoteRepository.createUser(user)
                }
                if (response.isSuccessful) {
                    response.body()?.let { responseSuccess ->
                        successLiveData.postValue(responseSuccess.message)
                    }
                } else {
                    errorLiveData.postValue("Error: ${response.message()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                errorLiveData.postValue("Exception: ${e.message}")
            }
        }
    }



    fun signUpWithEmail(email: String, password: String) {
        firebaseAuthManager.signUpWithEmail(email, password,
            onSuccess = { user ->
                emailResult.value = user
            },
            onFailure = { e ->
                signInError.value = e.message
            })
    }

    fun signInWithEmail(email: String, password: String) {
        firebaseAuthManager.signInWithEmail(email, password,
            onSuccess = { user ->
                emailResult.value = user
            },
            onFailure = { e ->
                signInError.value = e.message
            })
    }

    fun getGoogleSignInIntent(): Intent {
        return googleSignInManager.getSignInIntent()
    }

    fun signInWithGoogle(data: Intent?) {
        googleSignInManager.handleSignInResult(data,
            onSuccess = { credential ->
                firebaseAuthManager.signInWithCredential(credential,
                    onSuccess = { user,isNewUser ->
                        googleSignInResult.value = Pair(user, isNewUser)
                    },
                    onFailure = { e ->
                        signInError.value = e.message
                    })
            },
            onFailure = {
                signInError.value = it.message
            })
    }

    fun getUserDetail(userId: String) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    newsRemoteRepository.getUserDetail(userId)
                }
                if (response.isSuccessful) {
                    val foodResponse = response.body()
                    if (foodResponse != null && foodResponse.data != null) {
                        userDetail.postValue(foodResponse.data)
                    } else {
                        errorLiveData.postValue("User not found or empty response")
                    }
                } else {
                    errorLiveData.postValue("Error: ${response.message()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                errorLiveData.postValue("Exception: ${e.message}")
            }
        }
    }



    fun signOut(){
        googleSignInManager.signOut()
    }



    
}
