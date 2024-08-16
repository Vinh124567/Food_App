package com.example.myapplication.View.viewmodel


import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Firebase.FirebaseAuthManager
import com.example.myapplication.GoogleSigning.GoogleSignInManager
import com.example.myapplication.model.User
import com.example.myapplication.repository.NewsRemoteRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel(app: Application, private val newsRemoteRepository: NewsRemoteRepository) : AndroidViewModel(app) {

    private val googleSignInManager = GoogleSignInManager(app)
    private val firebaseAuthManager = FirebaseAuthManager()

    val authResult = MutableLiveData<Pair<FirebaseUser?,Boolean?>>()
    val authError = MutableLiveData<String?>()

    val userDetail=MutableLiveData<User?>()
    val successLiveData= MutableLiveData<String>()


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
                    authError.postValue("Error: ${response.message()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                authError.postValue("Exception: ${e.message}")
            }
        }
    }



    fun signUpWithEmail(email: String, password: String) {
        firebaseAuthManager.signUpWithEmail(email, password,
            onSuccess = { user ->
                authResult.value = Pair(user,null)
            },
            onFailure = { e ->
                authError.value = e.message
            })
    }

    fun signInWithEmail(email: String, password: String) {
        firebaseAuthManager.signInWithEmail(email, password,
            onSuccess = { user ->
                authResult.value = Pair(user,null)
            },
            onFailure = { e ->
                authError.value = e.message
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
                        authResult.value = Pair(user,isNewUser)
                    },
                    onFailure = { e ->
                        authError.value = e.message
                    })
            },
            onFailure = {
                authError.value = it.message
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
                        authError.postValue("User not found or empty response")
                    }
                } else {
                    authError.postValue("Error: ${response.message()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                authError.postValue("Exception: ${e.message}")
            }
        }
    }



    fun signOut(){
        googleSignInManager.signOut()
    }

}
