package com.example.petcare.ui.registration.model

sealed class RegistrationEvent {
    data class OnPetNameChanged(val name: String) : RegistrationEvent()
    data class OnGenderSelected(val gender: Gender) : RegistrationEvent()
    data class OnYearsSelected(val years: Int) : RegistrationEvent()
    data class OnMonthsSelected(val months: Int) : RegistrationEvent()
    object OnContinueRegistration : RegistrationEvent()
    object OnContinueToDoctors : RegistrationEvent()
}