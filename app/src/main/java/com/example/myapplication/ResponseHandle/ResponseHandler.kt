package com.example.myapplication.ResponseHandle

import retrofit2.Response

class ResponseHandler {

    companion object {
        suspend fun <T> handleResponse(
            response: Response<T>,
            onSuccess: (T) -> Unit,
            onError: (String) -> Unit
        ) {
            if (response.isSuccessful) {
                response.body()?.let(onSuccess) ?: onError("No data found")
            } else {
                onError("Error: ${response.message()}")
            }
        }
    }
}