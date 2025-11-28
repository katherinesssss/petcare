package com.example.petcare.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.petcare.data.local.entity.UserEntity
import com.example.petcare.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserRepository.getInstance(application)

    // UI States
    private val _registerState = MutableStateFlow<AuthState>(AuthState.Idle)
    val registerState: StateFlow<AuthState> = _registerState

    private val _loginState = MutableStateFlow<AuthState>(AuthState.Idle)
    val loginState: StateFlow<AuthState> = _loginState

    private val _currentUser = MutableStateFlow<UserState>(UserState.NotLoggedIn)
    val currentUser: StateFlow<UserState> = _currentUser

    // Form data
    var username by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    var loginCredential by mutableStateOf("")
    var loginPassword by mutableStateOf("")

    fun updateUsername(value: String) { username = value }
    fun updateEmail(value: String) { email = value }
    fun updatePassword(value: String) { password = value }
    fun updateConfirmPassword(value: String) { confirmPassword = value }
    fun updateLoginCredential(value: String) { loginCredential = value }
    fun updateLoginPassword(value: String) { loginPassword = value }

    fun register() {
        viewModelScope.launch {
            _registerState.value = AuthState.Loading

            if (password != confirmPassword) {
                _registerState.value = AuthState.Error("Пароли не совпадают")
                return@launch
            }

            val result = repository.register(username, email, password)
            _registerState.value = when {
                result.isSuccess -> {
                    loadCurrentUser()
                    AuthState.Success("Регистрация успешна!")
                }
                else -> AuthState.Error(result.exceptionOrNull()?.message ?: "Ошибка регистрации")
            }
        }
    }

    fun login() {
        viewModelScope.launch {
            _loginState.value = AuthState.Loading
            val result = repository.login(loginCredential, loginPassword)
            _loginState.value = when {
                result.isSuccess -> {
                    loadCurrentUser()
                    AuthState.Success("Вход выполнен успешно!")
                }
                else -> AuthState.Error(result.exceptionOrNull()?.message ?: "Ошибка входа")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
            _currentUser.value = UserState.NotLoggedIn
            clearFormData()
        }
    }

    fun loadCurrentUser() {
        viewModelScope.launch {
            repository.getCurrentUser().collect { user ->
                _currentUser.value = if (user != null) {
                    UserState.LoggedIn(user)
                } else {
                    UserState.NotLoggedIn
                }
            }
        }
    }

    fun clearStates() {
        _registerState.value = AuthState.Idle
        _loginState.value = AuthState.Idle
    }

    private fun clearFormData() {
        username = ""
        email = ""
        password = ""
        confirmPassword = ""
        loginCredential = ""
        loginPassword = ""
    }

    init {
        loadCurrentUser()
    }
}

// States
sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val message: String) : AuthState()
    data class Error(val message: String) : AuthState()
}

sealed class UserState {
    object NotLoggedIn : UserState()
    data class LoggedIn(val user: UserEntity) : UserState()
}