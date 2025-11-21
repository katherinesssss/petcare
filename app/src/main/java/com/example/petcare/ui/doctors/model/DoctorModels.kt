package com.example.petcare.ui.doctors.model

data class DoctorUiState(
    val allDoctors: List<Doctor> = emptyList(),
    val filteredDoctors: List<Doctor> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

data class Doctor(
    val id: String,
    val name: String,
    val specialization: String,
    val experience: String,
    val rating: Double,
    val reviews: Int
)