// DoctorViewModel.kt
package com.example.petcare.ui.doctors.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petcare.ui.doctors.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*

class DoctorViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DoctorUiState())
    val uiState: StateFlow<DoctorUiState> = _uiState.asStateFlow()

    private val _appointments = MutableStateFlow<List<DoctorAppointment>>(emptyList())
    val appointments: StateFlow<List<DoctorAppointment>> = _appointments.asStateFlow()

    private val _showAppointmentDialog = MutableStateFlow(false)
    val showAppointmentDialog: StateFlow<Boolean> = _showAppointmentDialog.asStateFlow()

    private var _selectedDoctorForAppointment: Doctor? = null
    val selectedDoctorForAppointment: Doctor? get() = _selectedDoctorForAppointment

    init {
        loadDoctors()
        loadMockAppointments()
    }

    fun showAppointmentDialog(doctor: Doctor) {
        _selectedDoctorForAppointment = doctor
        _showAppointmentDialog.value = true
    }

    fun hideAppointmentDialog() {
        _showAppointmentDialog.value = false
        _selectedDoctorForAppointment = null
    }

    fun addAppointment(appointment: DoctorAppointment) {
        viewModelScope.launch {
            _appointments.value = _appointments.value + appointment
            hideAppointmentDialog()
        }
    }

    fun cancelAppointment(appointmentId: String) {
        viewModelScope.launch {
            _appointments.value = _appointments.value.map { appointment ->
                if (appointment.id == appointmentId) {
                    appointment.copy(status = AppointmentStatus.CANCELLED)
                } else appointment
            }
        }
    }

    private fun loadMockAppointments() {
        val mockAppointments = listOf(
            DoctorAppointment(
                id = "1",
                doctorId = "1",
                doctorName = "Др. Иван Петров",
                doctorSpecialization = "Ветеринар-терапевт",
                date = "15.12.2023",
                time = "10:00",
                petName = "Барсик",
                reason = "Ежегодный осмотр"
            ),
            DoctorAppointment(
                id = "2",
                doctorId = "2",
                doctorName = "Др. Мария Сидорова",
                doctorSpecialization = "Ветеринар-дерматолог",
                date = "20.12.2023",
                time = "14:30",
                petName = "Шарик",
                reason = "Кожные проблемы"
            )
        )
        _appointments.value = mockAppointments
    }
    // DoctorViewModel.kt - добавьте этот метод
    fun onSearchQueryChanged(query: String) {
        _uiState.value = _uiState.value.copy(
            searchQuery = query,
            filteredDoctors = if (query.isBlank()) {
                _uiState.value.doctors
            } else {
                _uiState.value.doctors.filter { doctor ->
                    doctor.name.contains(query, ignoreCase = true) ||
                            doctor.specialization.contains(query, ignoreCase = true) ||
                            doctor.services.any { it.contains(query, ignoreCase = true) }
                }
            }
        )
    }
    private fun loadDoctors() {
        val mockDoctors = listOf(
            Doctor(
                id = "1",
                name = "Др. Иван Петров",
                specialization = "Ветеринар-терапевт",
                experienceYears = 12,
                rating = 4.8,
                reviews = 127,
                education = "Московская государственная академия ветеринарной медицины",
                description = "Специализируется на лечении мелких домашних животных. Имеет большой опыт работы с кошками и собаками.",
                languages = listOf("Русский", "Английский"),
                services = listOf("Консультация", "Диагностика", "Вакцинация"),
                location = "Ветеринарная клиника 'Друг', ул. Пушкина, 15",
                availableSlots = listOf("Пн 10:00-14:00", "Ср 15:00-18:00", "Пт 09:00-12:00"),
                price = "1 500 руб"
            ),
            Doctor(
                id = "2",
                name = "Др. Мария Сидорова",
                specialization = "Ветеринар-дерматолог",
                experienceYears = 8,
                rating = 4.9,
                reviews = 89,
                education = "Санкт-Петербургская государственная академия ветеринарной медицины",
                description = "Эксперт в области дерматологии животных. Помогает решать проблемы с кожей и шерстью.",
                languages = listOf("Русский", "Французский"),
                services = listOf("Дерматология", "Аллергология", "Трихология"),
                location = "Ветеринарный центр 'Зоодоктор', пр. Ленина, 45",
                availableSlots = listOf("Вт 11:00-16:00", "Чт 13:00-17:00", "Сб 10:00-14:00"),
                price = "2 000 руб"
            ),
            Doctor(
                id = "3",
                name = "Др. Алексей Козлов",
                specialization = "Ветеринар-хирург",
                experienceYears = 15,
                rating = 4.7,
                reviews = 203,
                education = "Новосибирский государственный аграрный университет",
                description = "Специалист по хирургическим вмешательствам любой сложности. Проводит операции на органах брюшной полости.",
                languages = listOf("Русский", "Немецкий"),
                services = listOf("Хирургия", "Стоматология", "Травматология"),
                location = "Ветеринарная клиника 'Айболит', ул. Гагарина, 28",
                availableSlots = listOf("Пн 08:00-12:00", "Ср 14:00-18:00", "Пт 10:00-16:00"),
                price = "3 500 руб"
            ),
            Doctor(
                id = "4",
                name = "Др. Елена Васнецова",
                specialization = "Ветеринар-кардиолог",
                experienceYears = 10,
                rating = 4.9,
                reviews = 156,
                education = "Казанская государственная академия ветеринарной медицины",
                description = "Специализируется на заболеваниях сердечно-сосудистой системы у животных. Проводит ЭКГ и УЗИ сердца.",
                languages = listOf("Русский", "Английский", "Испанский"),
                services = listOf("Кардиология", "УЗИ", "ЭКГ"),
                location = "Кардиологический центр 'ВетКардио', ул. Мира, 12",
                availableSlots = listOf("Вт 09:00-13:00", "Чт 15:00-19:00", "Сб 11:00-15:00"),
                price = "2 800 руб"
            ),
            Doctor(
                id = "5",
                name = "Др. Сергей Орлов",
                specialization = "Ветеринар-офтальмолог",
                experienceYears = 7,
                rating = 4.6,
                reviews = 94,
                education = "Российский университет дружбы народов",
                description = "Эксперт по заболеваниям глаз у животных. Проводит диагностику и лечение катаракты, глаукомы.",
                languages = listOf("Русский", "Английский"),
                services = listOf("Офтальмология", "Микрохирургия", "Диагностика"),
                location = "Ветеринарная клиника 'ЗооВижн', ул. Садовая, 34",
                availableSlots = listOf("Пн 13:00-17:00", "Ср 09:00-13:00", "Пт 14:00-18:00"),
                price = "2 200 руб"
            ),
            Doctor(
                id = "6",
                name = "Др. Анна Морозова",
                specialization = "Ветеринар-невролог",
                experienceYears = 9,
                rating = 4.8,
                reviews = 112,
                education = "Московская государственная академия ветеринарной медицины",
                description = "Специалист по заболеваниям нервной системы. Лечит эпилепсию, парезы, нарушения координации.",
                languages = listOf("Русский", "Французский"),
                services = listOf("Неврология", "Реабилитация", "Физиотерапия"),
                location = "Неврологический центр 'НейроВет', ул. Ленина, 67",
                availableSlots = listOf("Вт 14:00-18:00", "Чт 10:00-14:00", "Сб 09:00-13:00"),
                price = "2 600 руб"
            ),
            Doctor(
                id = "7",
                name = "Др. Дмитрий Соколов",
                specialization = "Ветеринар-онколог",
                experienceYears = 11,
                rating = 4.7,
                reviews = 78,
                education = "Санкт-Петербургская государственная академия ветеринарной медицины",
                description = "Специалист по диагностике и лечению онкологических заболеваний у животных.",
                languages = listOf("Русский", "Английский"),
                services = listOf("Онкология", "Химиотерапия", "Биопсия"),
                location = "Онкологический центр 'ВетОнко', ул. Чехова, 23",
                availableSlots = listOf("Пн 15:00-19:00", "Ср 11:00-15:00", "Пт 08:00-12:00"),
                price = "3 200 руб"
            ),
            Doctor(
                id = "8",
                name = "Др. Ольга Павлова",
                specialization = "Ветеринар-репродуктолог",
                experienceYears = 6,
                rating = 4.5,
                reviews = 65,
                education = "Воронежский государственный аграрный университет",
                description = "Специалист по репродуктивному здоровью животных. Занимается вопросами разведения и родовспоможения.",
                languages = listOf("Русский"),
                services = listOf("Репродуктология", "УЗИ", "Акушерство"),
                location = "Ветеринарная клиника 'Здоровье', ул. Центральная, 45",
                availableSlots = listOf("Вт 08:00-12:00", "Чт 16:00-20:00", "Сб 14:00-18:00"),
                price = "1 800 руб"
            )
        )
        _uiState.value = _uiState.value.copy(doctors = mockDoctors)
    }
}