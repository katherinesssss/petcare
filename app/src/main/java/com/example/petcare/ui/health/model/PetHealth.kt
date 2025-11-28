package com.example.petcare.ui.health.model

import androidx.compose.ui.graphics.Color

data class Pet(
    val id: String,
    val name: String,
    val type: PetType,
    val breed: String,
    val birthDate: String,
    val weight: Double,
    val photoUrl: String? = null,
    val healthData: PetHealthData
) {
    val age: String
        get() = calculateAge(birthDate)

    private fun calculateAge(birthDate: String): String {
        // Упрощенный расчет возраста
        return "2 года"
    }
}

data class PetHealthData(
    val healthScore: Int,
    val lastVetVisit: String,
    val nextVetVisit: String,
    val healthMetrics: List<HealthMetric>,
    val dailyActivities: List<DailyActivity>,
    val vaccinations: List<Vaccination> = emptyList()
)

data class HealthMetric(
    val type: MetricType,
    val value: String,
    val unit: String,
    val status: HealthStatus,
    val trend: Trend,
    val normalRange: String
)

data class DailyActivity(
    val activity: String,
    val completed: Boolean,
    val time: String? = null
)

data class Vaccination(
    val name: String,
    val date: String,
    val nextDate: String,
    val isCompleted: Boolean
)

data class NewPetData(
    val name: String,
    val type: PetType,
    val breed: String,
    val birthDate: String,
    val weight: Double,
    val healthMetrics: List<HealthMetricInput> = emptyList(),
    val dailyActivities: List<DailyActivityInput> = emptyList()
)

data class HealthMetricInput(
    val type: MetricType,
    val value: String,
    val unit: String
)

data class DailyActivityInput(
    val activity: String,
    val time: String? = null
)

enum class PetType {
    CAT, DOG, BIRD, RODENT, REPTILE, OTHER
}

enum class MetricType {
    ACTIVITY, WEIGHT, APPETITE, SLEEP, HEART_RATE, TEMPERATURE
}

enum class HealthStatus {
    EXCELLENT, GOOD, NORMAL, NEEDS_ATTENTION
}

enum class Trend {
    UP, DOWN, STABLE
}

// Extension functions
fun PetType.displayName(): String {
    return when(this) {
        PetType.CAT -> "Кот"
        PetType.DOG -> "Собака"
        PetType.BIRD -> "Птица"
        PetType.RODENT -> "Грызун"
        PetType.REPTILE -> "Рептилия"
        PetType.OTHER -> "Другой"
    }
}

fun MetricType.displayName(): String {
    return when(this) {
        MetricType.ACTIVITY -> "Активность"
        MetricType.WEIGHT -> "Вес"
        MetricType.APPETITE -> "Аппетит"
        MetricType.SLEEP -> "Сон"
        MetricType.HEART_RATE -> "Пульс"
        MetricType.TEMPERATURE -> "Температура"
    }
}

fun getStatusColor(status: HealthStatus): Color {
    return when(status) {
        HealthStatus.EXCELLENT -> Color(0xFF4CAF50)
        HealthStatus.GOOD -> Color(0xFF8BC34A)
        HealthStatus.NORMAL -> Color(0xFFFFC107)
        HealthStatus.NEEDS_ATTENTION -> Color(0xFFF44336)
    }
}

fun getHealthScoreColor(score: Int): Color {
    return when {
        score >= 80 -> Color(0xFF4CAF50)
        score >= 60 -> Color(0xFF8BC34A)
        score >= 40 -> Color(0xFFFFC107)
        score >= 20 -> Color(0xFFFF9800)
        else -> Color(0xFFF44336)
    }
}