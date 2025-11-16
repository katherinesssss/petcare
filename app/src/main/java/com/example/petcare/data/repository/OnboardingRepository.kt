package com.example.petcare.data.repository

class OnboardingRepository {

    fun completeOnboarding() {
        // Логика сохранения прохождения онбординга
    }

    fun getOnboardingData(): OnboardingData {
        return OnboardingData(
            title = "Добрый день,\nэто PetCare",
            description = "Мы — сервис заботы для питомцев и их хозяев.",
            buttonText = "Начать"
        )
    }
}

data class OnboardingData(
    val title: String,
    val description: String,
    val buttonText: String
)