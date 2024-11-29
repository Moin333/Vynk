package com.example.vynkapp.data

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {

    // Sign up with email and password
    suspend fun signUp(email: String, password: String): Boolean {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            true // Sign up successful
        } catch (e: Exception) {
            false // Sign up failed
        }
    }

    // Login with email and password
    suspend fun login(email: String, password: String): Boolean {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            true // Login successful
        } catch (e: Exception) {
            false // Login failed
        }
    }

    // Check if user is logged in
    fun isUserLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    // Sign out the user
    fun logout() {
        firebaseAuth.signOut()
    }
}
