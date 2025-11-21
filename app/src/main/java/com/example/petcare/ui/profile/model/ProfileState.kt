package com.example.petcare.ui.profile.model

data class ProfileUiState(
    val userData: UserData = UserData(),
    val isLoading: Boolean = false,
    val isLoggedOut: Boolean = false,
    val error: String? = null
)

data class UserData(
    val fullName: String = "Александр Иванов",
    val email: String = "alex.ivanov@example.com",
    val phone: String = "+7 (912) 345-67-89",
    val registrationDate: String = "15 января 2024",
    val avatarInitials: String = "АИ"
)