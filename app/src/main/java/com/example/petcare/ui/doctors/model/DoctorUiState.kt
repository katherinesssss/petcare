// DoctorUiState.kt
package com.example.petcare.ui.doctors.model

data class DoctorUiState( // Добавьте 'data' перед class
    val doctors: List<Doctor> = emptyList(),
    val filteredDoctors: List<Doctor> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val selectedDoctor: Doctor? = null
)