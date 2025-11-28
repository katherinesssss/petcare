// DoctorAppointment.kt
package com.example.petcare.ui.doctors.model



data class DoctorAppointment(
    val id: String,
    val doctorId: String,
    val doctorName: String, // Добавьте это поле
    val doctorSpecialization: String, // И это поле
    val date: String,
    val time: String,
    val petName: String,
    val reason: String,
    val status: AppointmentStatus = AppointmentStatus.CONFIRMED
)

enum class AppointmentStatus {
    CONFIRMED,
    COMPLETED,
    CANCELLED
}