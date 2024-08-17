package com.example.myapplication.Firebase

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class FirebaseAuthManager {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }


    suspend fun signInWithCredential(credential: AuthCredential): Pair<FirebaseUser, Boolean> {
        val result = firebaseAuth.signInWithCredential(credential).await()
        val user = result.user ?: throw Exception("User is null")
        val isNewUser = result.additionalUserInfo?.isNewUser ?: false
        return Pair(user, isNewUser)
    }



    suspend fun signUpWithEmail(email: String, password: String): FirebaseUser {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            authResult.user ?: throw Exception("User is null")
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun signInWithEmail(email: String, password: String): FirebaseUser {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            authResult.user ?: throw Exception("User is null")
        } catch (e: Exception) {
            throw e
        }
    }

    fun signOut() {
        firebaseAuth.signOut()
    }
}
