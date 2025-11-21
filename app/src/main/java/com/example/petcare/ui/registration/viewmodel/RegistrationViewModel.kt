package com.example.petcare.ui.registration.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petcare.ui.registration.model.Gender
import com.example.petcare.ui.registration.model.RegistrationEvent
import com.example.petcare.ui.registration.model.RegistrationUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegistrationViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegistrationUiState())
    val uiState: StateFlow<RegistrationUiState> = _uiState.asStateFlow()

    fun onEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.OnPetNameChanged -> {
                _uiState.value = _uiState.value.copy(petName = event.name)
            }
            is RegistrationEvent.OnGenderSelected -> {
                _uiState.value = _uiState.value.copy(selectedGender = event.gender)
            }
            is RegistrationEvent.OnYearsSelected -> {
                _uiState.value = _uiState.value.copy(selectedYears = event.years)
            }
            is RegistrationEvent.OnMonthsSelected -> {
                _uiState.value = _uiState.value.copy(selectedMonths = event.months)
            }
            RegistrationEvent.OnContinueRegistration -> {
                // Можно добавить валидацию или сохранение данных
                _uiState.value = _uiState.value.copy(isLoading = true)
                viewModelScope.launch {
                    // Имитация загрузки
                    kotlinx.coroutines.delay(500)
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
            }
            RegistrationEvent.OnContinueToDoctors -> {
                // Логика перехода к врачам
                _uiState.value = _uiState.value.copy(isLoading = true)
                viewModelScope.launch {
                    // Имитация загрузки
                    kotlinx.coroutines.delay(500)
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
            }
        }
    }

    fun isRegistrationValid(): Boolean {
        val state = _uiState.value
        return state.petName.isNotEmpty() && state.selectedGender != null
    }

    fun isAgeSelectionValid(): Boolean {
        val state = _uiState.value
        return state.selectedYears != null && state.selectedMonths != null
    }
}