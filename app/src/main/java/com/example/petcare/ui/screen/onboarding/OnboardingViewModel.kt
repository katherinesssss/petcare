package com.example.petcare.ui.screen.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.petcare.data.repository.OnboardingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val repository: OnboardingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    init {
        loadOnboardingData()
    }

    fun onEvent(event: OnboardingEvent) {
        when (event) {
            OnboardingEvent.OnStartButtonClick -> {
                completeOnboarding()
            }
        }
    }

    private fun loadOnboardingData() {
        val data = repository.getOnboardingData()
        _uiState.value = _uiState.value.copy(
            title = data.title,
            description = data.description,
            buttonText = data.buttonText
        )
    }

    private fun completeOnboarding() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            kotlinx.coroutines.delay(500)

            repository.completeOnboarding()
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                isCompleted = true
            )
        }
    }
}

// Добавляем Factory класс
class OnboardingViewModelFactory(
    private val repository: OnboardingRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OnboardingViewModel::class.java)) {
            return OnboardingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}