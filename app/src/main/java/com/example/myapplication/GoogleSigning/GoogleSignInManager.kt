package com.example.myapplication.GoogleSigning

import android.content.Context
import android.content.Intent
import com.example.myapplication.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class GoogleSignInManager(private val context: Context) {

    private val googleSignInClient: GoogleSignInClient by lazy {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        GoogleSignIn.getClient(context, gso)
    }

    fun getSignInIntent(): Intent {
        return googleSignInClient.signInIntent
    }


suspend fun handleSignInResult(data: Intent?): AuthCredential {
    val task = GoogleSignIn.getSignedInAccountFromIntent(data)
    return try {
        val account = task.await() // Chờ kết quả từ task
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        credential
    } catch (e: ApiException) {
        throw e
    }
}


    fun signOut() {
        googleSignInClient.signOut()
    }
}
