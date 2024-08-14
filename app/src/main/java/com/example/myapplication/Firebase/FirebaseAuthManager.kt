package com.example.myapplication.Firebase

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FirebaseAuthManager {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }


    fun signInWithCredential(credential: AuthCredential, onSuccess: (FirebaseUser, Boolean) -> Unit, onFailure: (Exception) -> Unit) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result
                    val user = result?.user
                    val isNewUser = result?.additionalUserInfo?.isNewUser ?: false

                    if (user != null) {
                        onSuccess(user, isNewUser)
                    } else {
                        onFailure(Exception("User is null"))
                    }
                } else {
                    task.exception?.let { onFailure(it) }
                }
            }
    }

    fun signUpWithEmail(email: String, password: String, onSuccess: (FirebaseUser) -> Unit, onFailure: (Exception) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    if (user != null) {
                        onSuccess(user)
                    } else {
                        onFailure(Exception("User is null"))
                    }
                } else {
                    task.exception?.let { onFailure(it) }
                }
            }
    }

    fun signInWithEmail(email: String, password: String, onSuccess: (FirebaseUser) -> Unit, onFailure: (Exception) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    if (user != null) {
                        onSuccess(user)
                    } else {
                        onFailure(Exception("User is null"))
                    }
                } else {
                    task.exception?.let { onFailure(it) }
                }
            }
    }

    fun signOut() {
        firebaseAuth.signOut()
    }
}
