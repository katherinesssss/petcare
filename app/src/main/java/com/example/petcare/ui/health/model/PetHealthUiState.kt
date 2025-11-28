package com.example.petcare.ui.health.model

data class PetHealthUiState(
    val pets: List<Pet> = emptyList(),
    val selectedPet: Pet? = null,
    val isLoading: Boolean = false,
    val selectedPeriod: String = "Н",
    val showAddPetDialog: Boolean = false
)