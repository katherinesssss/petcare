package com.example.petcare.ui.onboarding.model

sealed class OnboardingEvent {
    object OnStartButtonClick : OnboardingEvent()
}