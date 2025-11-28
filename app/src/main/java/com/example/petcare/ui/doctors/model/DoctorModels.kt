// Doctor.kt
package com.example.petcare.ui.doctors.model

data class Doctor(
    val id: String,
    val name: String,
    val specialization: String,
    val experienceYears: Int, // Оставляем только это поле
    val rating: Double,
    val reviews: Int,
    val imageUrl: String? = null,
    val education: String = "",
    val description: String = "",
    val languages: List<String> = emptyList(),
    val services: List<String> = emptyList(),
    val price: String = "",
    val availableSlots: List<String> = emptyList(),
    val location: String = ""
)