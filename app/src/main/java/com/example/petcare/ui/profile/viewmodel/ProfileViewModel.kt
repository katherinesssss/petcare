package com.example.petcare.ui.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petcare.ui.profile.model.ProfileEvent
import com.example.petcare.ui.profile.model.ProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfileData()
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.OnLogoutClick -> logout()
            ProfileEvent.OnRetryLoadData -> loadProfileData()
        }
    }

    private fun loadProfileData() {
        _uiState.value = _uiState.value.copy(isLoading = true)

        viewModelScope.launch {
            try {
                // Имитация загрузки данных из API/базы
                kotlinx.coroutines.delay(1000)

                // В реальном приложении здесь был бы вызов репозитория
                // val userData = userRepository.getUserData()

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Не удалось загрузить данные профиля"
                )
            }
        }
    }

    private fun logout() {
        _uiState.value = _uiState.value.copy(isLoading = true)

        viewModelScope.launch {
            try {
                // Имитация выхода из аккаунта
                kotlinx.coroutines.delay(500)

                // В реальном приложении:
                // authRepository.logout()
                // userRepository.clearUserData()

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isLoggedOut = true
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Не удалось выйти из аккаунта"
                )
            }
        }
    }
}