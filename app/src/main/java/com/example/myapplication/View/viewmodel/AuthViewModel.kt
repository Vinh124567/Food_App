package com.example.myapplication.View.viewmodel
import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.myapplication.Firebase.FirebaseAuthManager
import com.example.myapplication.GoogleSigning.GoogleSignInManager
import com.example.myapplication.ImageUtils.ImageUtils
import com.example.myapplication.api.RetrofitInstance
import com.example.myapplication.model.User
import com.example.myapplication.repository.NewsRemoteRepository
import com.google.firebase.auth.FirebaseUser
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class AuthViewModel(app: Application, private val newsRemoteRepository: NewsRemoteRepository) : AndroidViewModel(app) {

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> get() = _loadingState

    private val googleSignInManager = GoogleSignInManager(app)
    private val firebaseAuthManager = FirebaseAuthManager()

    val authResult = MutableLiveData<Pair<FirebaseUser?,Boolean?>>()
    val authError = MutableLiveData<String?>()

    val userDetail=MutableLiveData<User?>()
    val successLiveData= MutableLiveData<String>()
    val errorLiveData= MutableLiveData<String>()


    init{
        authResult.observeForever { (user, _) ->
            user?.let {
                getUserDetail(it.uid)
            }
        }
    }


    fun signUpWithEmail(email: String, password: String) {
        viewModelScope.launch {
            try {
                _loadingState.value = true
                val user = firebaseAuthManager.signUpWithEmail(email, password)
                authResult.value = Pair(user, null)
            } catch (e: Exception) {
                authError.value = e.message
            }finally {
                _loadingState.value = false
            }
        }
    }

    fun signInWithEmail(email: String, password: String) {
        viewModelScope.launch {
            try {
                _loadingState.value = true
                val user = firebaseAuthManager.signInWithEmail(email, password)
                authResult.value = Pair(user, null)
            } catch (e: Exception) {
                authError.value = e.message
            }finally {
                _loadingState.value = false
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

    fun createUser(user: User, fileUri: Uri?) {
        viewModelScope.launch {
            try {
                _loadingState.value = true
                val context = getApplication<Application>().applicationContext
                val file = fileUri?.let { ImageUtils.getFileFromUri(context, it) }

                // Kiểm tra xem người dùng có chọn ảnh hay không
                val body: MultipartBody.Part? = if (file != null) {
                    val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    MultipartBody.Part.createFormData("image", file.name, requestFile)
                } else {
                    null // Nếu không có ảnh, đặt body là null
                }

                val userJson = Gson().toJson(user)
                val userRequestBody = userJson.toRequestBody("application/json".toMediaTypeOrNull())

                // Gửi yêu cầu lên server, ảnh có thể là null
                val response = RetrofitInstance.api.createUser(userRequestBody, body)
                if (response.isSuccessful) {
                    response.body()?.let { userResponse ->
                        successLiveData.postValue(userResponse.message)
                        Glide.get(context).clearMemory()
                        Thread {
                            Glide.get(context).clearDiskCache()
                        }.start()
                    }
                } else {
                    authError.postValue("Error: ${response.message()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                errorLiveData.postValue("Lỗi: ${e.message}")
            } finally {
                _loadingState.value = false
            }
        }
    }



    fun signOut(){
        googleSignInManager.signOut()
    }

}
