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
        viewModelScope.launch {
            try {
                val user = firebaseAuthManager.signUpWithEmail(email, password)
                authResult.value = Pair(user, null)
            } catch (e: Exception) {
                authError.value = e.message
            }
        }
    }

    fun signInWithEmail(email: String, password: String) {
        viewModelScope.launch {
            try {
                val user = firebaseAuthManager.signInWithEmail(email, password)
                authResult.value = Pair(user, null)
            } catch (e: Exception) {
                authError.value = e.message
            }
        }
    }


    fun signInWithGoogle(data: Intent?) {
        viewModelScope.launch {
            try {
                val credential = googleSignInManager.handleSignInResult(data)
                val (user, isNewUser) = firebaseAuthManager.signInWithCredential(credential)
                authResult.value = Pair(user, isNewUser)
            } catch (e: Exception) {
                authError.value = e.message
            }
        }
    }

    fun getGoogleSignInIntent(): Intent {
        return googleSignInManager.getSignInIntent()
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
