package com.example.petcare.ui.onboarding.model

data class OnboardingUiState(
    val isLoading: Boolean = false,
    val isCompleted: Boolean = false,
    val error: String? = null
)

