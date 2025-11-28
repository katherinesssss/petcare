package com.example.petcare.ui.health.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petcare.ui.health.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.petcare.ui.health.model.MetricType

class PetHealthViewModel : ViewModel() {

    private val _pets = MutableStateFlow<List<Pet>>(emptyList())
    val pets: StateFlow<List<Pet>> = _pets.asStateFlow()

    private val _selectedPet = MutableStateFlow<Pet?>(null)
    val selectedPet: StateFlow<Pet?> = _selectedPet.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _showAddPetDialog = MutableStateFlow(false)
    val showAddPetDialog: StateFlow<Boolean> = _showAddPetDialog.asStateFlow()

    init {
        loadPets()
    }

    fun selectPet(pet: Pet) {
        _selectedPet.value = pet
    }

    fun showAddPetDialog() {
        _showAddPetDialog.value = true
    }

    fun hideAddPetDialog() {
        _showAddPetDialog.value = false
    }

    fun addNewPet(petData: NewPetData) {
        viewModelScope.launch {
            val newPet = createPetFromData(petData)
            _pets.value = _pets.value + newPet
            _selectedPet.value = newPet
            hideAddPetDialog()
        }
    }

    fun updatePetHealthMetric(petId: String, metricType: MetricType, value: String) {
        viewModelScope.launch {
            _pets.value = _pets.value.map { pet ->
                if (pet.id == petId) {
                    val updatedMetrics = pet.healthData.healthMetrics.map { metric ->
                        if (metric.type == metricType) {
                            metric.copy(value = value)
                        } else metric
                    }
                    val updatedHealthData = pet.healthData.copy(healthMetrics = updatedMetrics)
                    pet.copy(healthData = updatedHealthData)
                } else pet
            }
            _selectedPet.value = _pets.value.find { it.id == petId }
        }
    }

    fun toggleActivityCompletion(petId: String, activity: String) {
        viewModelScope.launch {
            _pets.value = _pets.value.map { pet ->
                if (pet.id == petId) {
                    val updatedActivities = pet.healthData.dailyActivities.map { dailyActivity ->
                        if (dailyActivity.activity == activity) {
                            dailyActivity.copy(completed = !dailyActivity.completed)
                        } else dailyActivity
                    }
                    val updatedHealthData = pet.healthData.copy(dailyActivities = updatedActivities)
                    pet.copy(healthData = updatedHealthData)
                } else pet
            }
            _selectedPet.value = _pets.value.find { it.id == petId }
        }
    }

    private fun loadPets() {
        _isLoading.value = true

        viewModelScope.launch {
            kotlinx.coroutines.delay(500)

            val mockPets = listOf(
                createMockPet("1", "Барсик", PetType.CAT),
                createMockPet("2", "Шарик", PetType.DOG)
            )

            _pets.value = mockPets
            _selectedPet.value = mockPets.firstOrNull()
            _isLoading.value = false
        }
    }

    private fun createMockPet(id: String, name: String, type: PetType): Pet {
        return Pet(
            id = id,
            name = name,
            type = type,
            breed = when(type) {
                PetType.CAT -> "Британский"
                PetType.DOG -> "Лабрадор"
                else -> "Неизвестно"
            },
            birthDate = "15.03.2022",
            weight = 4.2,
            healthData = PetHealthData(
                healthScore = 85,
                lastVetVisit = "15.12.2023",
                nextVetVisit = "15.03.2024",
                healthMetrics = listOf(
                    HealthMetric(MetricType.ACTIVITY, "85", "%", HealthStatus.EXCELLENT, Trend.UP, "70-90%"),
                    HealthMetric(MetricType.WEIGHT, "4.2", "кг", HealthStatus.GOOD, Trend.STABLE, "4-5кг"),
                    HealthMetric(MetricType.APPETITE, "90", "%", HealthStatus.EXCELLENT, Trend.UP, "80-95%"),
                    HealthMetric(MetricType.SLEEP, "12", "ч", HealthStatus.NORMAL, Trend.DOWN, "10-14ч")
                ),
                dailyActivities = listOf(
                    DailyActivity("Утреннее кормление", true, "08:00"),
                    DailyActivity("Прогулка", false, "10:00"),
                    DailyActivity("Игры", true, "12:00"),
                    DailyActivity("Вечернее кормление", false, "18:00")
                )
            )
        )
    }

    private fun createPetFromData(petData: NewPetData): Pet {
        // Конвертируем HealthMetricInput в HealthMetric
        val healthMetrics = if (petData.healthMetrics.isNotEmpty()) {
            petData.healthMetrics.map { input ->
                HealthMetric(
                    type = input.type,
                    value = input.value,
                    unit = input.unit,
                    status = HealthStatus.NORMAL,
                    trend = Trend.STABLE,
                    normalRange = getDefaultNormalRange(input.type, petData.type)
                )
            }
        } else {
            getDefaultMetricsForPetType(petData.type)
        }

        // Конвертируем DailyActivityInput в DailyActivity
        val dailyActivities = if (petData.dailyActivities.isNotEmpty()) {
            petData.dailyActivities.map { input ->
                DailyActivity(
                    activity = input.activity,
                    completed = false,
                    time = input.time
                )
            }
        } else {
            getDefaultActivitiesForPetType(petData.type)
        }

        return Pet(
            id = System.currentTimeMillis().toString(),
            name = petData.name,
            type = petData.type,
            breed = petData.breed,
            birthDate = petData.birthDate,
            weight = petData.weight,
            healthData = PetHealthData(
                healthScore = 100,
                lastVetVisit = "Не было",
                nextVetVisit = "Не назначен",
                healthMetrics = healthMetrics,
                dailyActivities = dailyActivities
            )
        )
    }

    private fun getDefaultNormalRange(type: MetricType, petType: PetType): String {
        return when(type) {
            MetricType.ACTIVITY -> if (petType == PetType.CAT) "70-90%" else "60-80%"
            MetricType.WEIGHT -> if (petType == PetType.CAT) "3-6кг" else "10-30кг"
            MetricType.APPETITE -> if (petType == PetType.CAT) "80-95%" else "85-100%"
            MetricType.SLEEP -> if (petType == PetType.CAT) "10-14ч" else "12-14ч"
            MetricType.HEART_RATE -> if (petType == PetType.CAT) "110-140" else "60-140"
            MetricType.TEMPERATURE -> if (petType == PetType.CAT) "37.5-39.2°C" else "37.5-39.2°C"
        }
    }

    private fun getDefaultMetricsForPetType(type: PetType): List<HealthMetric> {
        return when(type) {
            PetType.CAT -> listOf(
                HealthMetric(MetricType.ACTIVITY, "0", "%", HealthStatus.NORMAL, Trend.STABLE, "70-90%"),
                HealthMetric(MetricType.WEIGHT, "0", "кг", HealthStatus.NORMAL, Trend.STABLE, "3-6кг"),
                HealthMetric(MetricType.APPETITE, "0", "%", HealthStatus.NORMAL, Trend.STABLE, "80-95%")
            )
            PetType.DOG -> listOf(
                HealthMetric(MetricType.ACTIVITY, "0", "%", HealthStatus.NORMAL, Trend.STABLE, "60-80%"),
                HealthMetric(MetricType.WEIGHT, "0", "кг", HealthStatus.NORMAL, Trend.STABLE, "10-30кг"),
                HealthMetric(MetricType.APPETITE, "0", "%", HealthStatus.NORMAL, Trend.STABLE, "85-100%")
            )
            else -> emptyList()
        }
    }

    private fun getDefaultActivitiesForPetType(type: PetType): List<DailyActivity> {
        return when(type) {
            PetType.CAT -> listOf(
                DailyActivity("Утреннее кормление", false, "08:00"),
                DailyActivity("Игры", false, "12:00"),
                DailyActivity("Вечернее кормление", false, "18:00")
            )
            PetType.DOG -> listOf(
                DailyActivity("Утренняя прогулка", false, "07:00"),
                DailyActivity("Кормление", false, "08:00"),
                DailyActivity("Вечерняя прогулка", false, "19:00")
            )
            else -> emptyList()
        }
    }
}