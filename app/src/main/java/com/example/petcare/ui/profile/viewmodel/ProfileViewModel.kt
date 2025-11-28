package com.example.petcare.ui.profile.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.petcare.data.repository.UserRepository
import com.example.petcare.ui.profile.model.ProfileUiState
import com.example.petcare.ui.profile.model.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserRepository.getInstance(application)

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    // Состояния для редактирования
    private val _isEditingName = MutableStateFlow(false)
    val isEditingName: StateFlow<Boolean> = _isEditingName.asStateFlow()

    private val _isEditingPhone = MutableStateFlow(false)
    val isEditingPhone: StateFlow<Boolean> = _isEditingPhone.asStateFlow()

    private val _editingName = MutableStateFlow("")
    val editingName: StateFlow<String> = _editingName.asStateFlow()

    private val _editingPhone = MutableStateFlow("")
    val editingPhone: StateFlow<String> = _editingPhone.asStateFlow()

    init {
        loadProfileData()
    }

    fun loadProfileData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val profileData = repository.getProfileData()
                if (profileData != null) {
                    _uiState.value = _uiState.value.copy(
                        userData = profileData,
                        isLoading = false,
                        error = null
                    )
                    // Инициализируем поля редактирования текущими значениями
                    _editingName.value = profileData.fullName
                    _editingPhone.value = profileData.phone
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Не удалось загрузить данные профиля"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Ошибка загрузки: ${e.message}"
                )
            }
        }
    }

    // Редактирование имени
    fun startEditingName() {
        _editingName.value = _uiState.value.userData.fullName
        _isEditingName.value = true
    }

    fun cancelEditingName() {
        _isEditingName.value = false
        _editingName.value = _uiState.value.userData.fullName
    }

    fun updateEditingName(value: String) {
        _editingName.value = value
    }

    fun saveName() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val result = repository.updateFullName(_editingName.value)
            if (result.isSuccess) {
                _isEditingName.value = false
                loadProfileData() // Перезагружаем данные после успешного обновления
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Не удалось обновить имя: ${result.exceptionOrNull()?.message}"
                )
            }
        }
    }

    // Редактирование телефона
    fun startEditingPhone() {
        _editingPhone.value = _uiState.value.userData.phone
        _isEditingPhone.value = true
    }

    fun cancelEditingPhone() {
        _isEditingPhone.value = false
        _editingPhone.value = _uiState.value.userData.phone
    }

    fun updateEditingPhone(value: String) {
        _editingPhone.value = value
    }

    fun savePhone() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val result = repository.updatePhone(_editingPhone.value)
            if (result.isSuccess) {
                _isEditingPhone.value = false
                loadProfileData() // Перезагружаем данные после успешного обновления
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Не удалось обновить телефон: ${result.exceptionOrNull()?.message}"
                )
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
            _uiState.value = _uiState.value.copy(isLoggedOut = true)
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}