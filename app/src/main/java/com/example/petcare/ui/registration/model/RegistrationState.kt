package com.example.petcare.ui.registration.model

data class RegistrationUiState(
    val petName: String = "",
    val selectedGender: Gender? = null,
    val selectedYears: Int? = null,
    val selectedMonths: Int? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

enum class Gender {
    MALE,
    FEMALE
}