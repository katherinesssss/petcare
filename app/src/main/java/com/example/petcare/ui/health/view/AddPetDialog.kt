package com.example.petcare.ui.health.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.petcare.ui.health.model.*

@Composable
fun AddPetDialog(
    onDismiss: () -> Unit,
    onAddPet: (NewPetData) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var selectedPetType by remember { mutableStateOf(PetType.CAT) }
    var breed by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.extraLarge,
            color = MaterialTheme.colorScheme.surface
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
                        text = "Добавить питомца",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Закрыть")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Основной контент с прокруткой
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .weight(1f, false),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Основная информация
                    Text(
                        text = "Основная информация",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Имя питомца") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = "Тип питомца",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )

                    // Типы питомцев
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        PetType.entries.forEach { type ->
                            val isSelected = selectedPetType == type
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { selectedPetType = type },
                                shape = MaterialTheme.shapes.small,
                                color = if (isSelected) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.surfaceVariant,
                                contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary
                                else MaterialTheme.colorScheme.onSurfaceVariant
                            ) {
                                Text(
                                    text = type.displayName(),
                                    modifier = Modifier.padding(12.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = breed,
                        onValueChange = { breed = it },
                        label = { Text("Порода") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = birthDate,
                        onValueChange = { birthDate = it },
                        label = { Text("Дата рождения (дд.мм.гггг)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = weight,
                        onValueChange = { weight = it },
                        label = { Text("Вес") },
                        suffix = { Text("кг") },
                        modifier = Modifier.fillMaxWidth()
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
                            if (name.isNotBlank() && breed.isNotBlank() && birthDate.isNotBlank() && weight.isNotBlank()) {
                                onAddPet(
                                    NewPetData(
                                        name = name,
                                        type = selectedPetType,
                                        breed = breed,
                                        birthDate = birthDate,
                                        weight = weight.toDoubleOrNull() ?: 0.0,
                                        healthMetrics = emptyList(),
                                        dailyActivities = emptyList()
                                    )
                                )
                            }
                        },
                        enabled = name.isNotBlank() && breed.isNotBlank() && birthDate.isNotBlank() && weight.isNotBlank(),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Добавить питомца")
                    }
                }
            }
        }
    }
}