// CreateAppointmentDialog.kt
package com.example.petcare.ui.doctors.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.petcare.ui.doctors.model.DoctorAppointment
import com.example.petcare.ui.doctors.viewmodel.DoctorViewModel
import java.util.*

@Composable
fun CreateAppointmentDialog(
    viewModel: DoctorViewModel,
    onDismiss: () -> Unit
) {
    val selectedDoctor = viewModel.selectedDoctorForAppointment
    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }
    var petName by remember { mutableStateOf("") }
    var reason by remember { mutableStateOf("") }

    if (selectedDoctor == null) return

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.extraLarge
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                // Заголовок
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Запись на прием",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Закрыть")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Информация о враче
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = selectedDoctor.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = selectedDoctor.specialization,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Форма записи
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .weight(1f, false),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = selectedDate,
                        onValueChange = { selectedDate = it },
                        label = { Text("Дата (дд.мм.гггг)") },
                        placeholder = { Text("15.12.2023") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = selectedTime,
                        onValueChange = { selectedTime = it },
                        label = { Text("Время") },
                        placeholder = { Text("10:00") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = petName,
                        onValueChange = { petName = it },
                        label = { Text("Имя питомца") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = reason,
                        onValueChange = { reason = it },
                        label = { Text("Причина визита") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Кнопки
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Отмена")
                    }

                    Button(
                        onClick = {
                            if (selectedDate.isNotBlank() && selectedTime.isNotBlank() &&
                                petName.isNotBlank() && reason.isNotBlank()) {
                                val appointment = DoctorAppointment(
                                    id = UUID.randomUUID().toString(),
                                    doctorId = selectedDoctor.id,
                                    doctorName = selectedDoctor.name,
                                    doctorSpecialization = selectedDoctor.specialization,
                                    date = selectedDate,
                                    time = selectedTime,
                                    petName = petName,
                                    reason = reason
                                )
                                viewModel.addAppointment(appointment)
                            }
                        },
                        enabled = selectedDate.isNotBlank() && selectedTime.isNotBlank() &&
                                petName.isNotBlank() && reason.isNotBlank(),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Записаться")
                    }
                }
            }
        }
    }
}