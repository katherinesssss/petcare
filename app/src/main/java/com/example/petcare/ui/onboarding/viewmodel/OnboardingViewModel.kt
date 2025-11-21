package com.example.petcare.ui.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petcare.data.repository.OnboardingRepository
import com.example.petcare.ui.onboarding.model.OnboardingEvent
import com.example.petcare.ui.onboarding.model.OnboardingUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val repository: OnboardingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    fun onEvent(event: OnboardingEvent) {
        when (event) {
            OnboardingEvent.OnStartButtonClick -> completeOnboarding()
        }
    }

    private fun completeOnboarding() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                repository.setShouldShowOnboarding(false)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isCompleted = true
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Не удалось завершить онбординг"
                )
            }
        }
    }
}