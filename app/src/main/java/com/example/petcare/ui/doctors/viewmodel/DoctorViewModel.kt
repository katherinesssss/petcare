package com.example.petcare.ui.doctors.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petcare.ui.doctors.model.Doctor
import com.example.petcare.ui.doctors.model.DoctorUiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DoctorViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DoctorUiState())
    val uiState: StateFlow<DoctorUiState> = _uiState.asStateFlow()

    init {
        loadDoctors()
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        filterDoctors()
    }

    private fun loadDoctors() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            kotlinx.coroutines.delay(500)

            val doctors = getDoctorsList()
            _uiState.update {
                it.copy(
                    allDoctors = doctors,
                    filteredDoctors = doctors,
                    isLoading = false
                )
            }
        }
    }

    private fun filterDoctors() {
        val state = _uiState.value
        val filtered = if (state.searchQuery.isBlank()) {
            state.allDoctors
        } else {
            state.allDoctors.filter { doctor ->
                doctor.name.contains(state.searchQuery, ignoreCase = true) ||
                        doctor.specialization.contains(state.searchQuery, ignoreCase = true)
            }
        }
        _uiState.update { it.copy(filteredDoctors = filtered) }
    }

    private fun getDoctorsList(): List<Doctor> {
        return listOf(
            Doctor("1", "Иван Петров", "Ветеринар-терапевт", "Опыт: 8 лет", 4.8, 124),
            Doctor("2", "Мария Сидорова", "Ветеринар-хирург", "Опыт: 12 лет", 4.9, 89),
            Doctor("3", "Алексей Козлов", "Ветеринар-дерматолог", "Опыт: 6 лет", 4.7, 67),
            Doctor("4", "Елена Николаева", "Ветеринар-кардиолог", "Опыт: 10 лет", 4.9, 156),
        )
    }
}