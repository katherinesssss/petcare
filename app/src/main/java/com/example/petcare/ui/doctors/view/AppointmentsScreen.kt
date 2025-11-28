// AppointmentsScreen.kt
package com.example.petcare.ui.doctors.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.petcare.ui.doctors.model.AppointmentStatus
import com.example.petcare.ui.doctors.viewmodel.DoctorViewModel

@Composable
fun AppointmentsScreen() {
    val viewModel: DoctorViewModel = viewModel()
    val appointments by viewModel.appointments.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Мои записи",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (appointments.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Нет записей",
                        modifier = Modifier.size(64.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "У вас пока нет записей",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(appointments) { appointment ->
                    AppointmentCard(
                        appointment = appointment,
                        onCancel = { viewModel.cancelAppointment(appointment.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun AppointmentCard(
    appointment: com.example.petcare.ui.doctors.model.DoctorAppointment,
    onCancel: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = appointment.doctorName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = appointment.doctorSpecialization,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }

                // Статус
                Text(
                    text = when (appointment.status) {
                        AppointmentStatus.CONFIRMED -> "Подтверждена"
                        AppointmentStatus.COMPLETED -> "Завершена"
                        AppointmentStatus.CANCELLED -> "Отменена"
                    },
                    style = MaterialTheme.typography.labelSmall,
                    color = when (appointment.status) {
                        AppointmentStatus.CONFIRMED -> Color(0xFF4CAF50)
                        AppointmentStatus.COMPLETED -> Color(0xFF2196F3)
                        AppointmentStatus.CANCELLED -> Color(0xFFF44336)
                    },
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .background(
                            when (appointment.status) {
                                AppointmentStatus.CONFIRMED -> Color(0xFFE8F5E8)
                                AppointmentStatus.COMPLETED -> Color(0xFFE3F2FD)
                                AppointmentStatus.CANCELLED -> Color(0xFFFFEBEE)
                            },
                            RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Детали записи
            Column {
                InfoRow("Дата и время", "${appointment.date} в ${appointment.time}")
                InfoRow("Питомец", appointment.petName)
                InfoRow("Причина", appointment.reason)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Кнопка отмены (только для подтвержденных записей)
            if (appointment.status == AppointmentStatus.CONFIRMED) {
                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFFF44336)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Отменить",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Отменить запись")
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}