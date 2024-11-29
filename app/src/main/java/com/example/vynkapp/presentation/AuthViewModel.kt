package com.example.vynkapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vynkapp.data.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _isAuthenticated = MutableStateFlow(authRepository.isUserLoggedIn())
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated

    fun login(email: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = authRepository.login(email, password)
            _isAuthenticated.value = success
            onResult(success)
        }
    }

    fun signUp(email: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = authRepository.signUp(email, password)
            _isAuthenticated.value = success
            onResult(success)
        }
    }

    fun logout() {
        authRepository.logout()
        _isAuthenticated.value = false
    }
}
