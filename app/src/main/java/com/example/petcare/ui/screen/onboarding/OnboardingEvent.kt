package com.example.petcare.ui.screen.onboarding

sealed class OnboardingEvent {
    object OnStartButtonClick : OnboardingEvent()
}

data class OnboardingUiState(
    val title: String = "",
    val description: String = "",
    val buttonText: String = "",
    val isLoading: Boolean = false,
    val isCompleted: Boolean = false
)