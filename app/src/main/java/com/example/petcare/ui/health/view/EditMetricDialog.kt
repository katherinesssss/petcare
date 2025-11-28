package com.example.petcare.ui.health.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.petcare.ui.health.model.HealthMetric
import com.example.petcare.ui.health.model.MetricType
import com.example.petcare.ui.health.model.displayName

@Composable
fun EditMetricDialog(
    metric: HealthMetric,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var newValue by remember { mutableStateOf(metric.value) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = metric.type.displayName(),
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = newValue,
                    onValueChange = { newValue = it },
                    label = { Text("Значение") },
                    suffix = { Text(metric.unit) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = when(metric.type) {
                            MetricType.WEIGHT, MetricType.TEMPERATURE -> KeyboardType.Decimal
                            else -> KeyboardType.Number
                        }
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Норма: ${metric.normalRange}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(newValue) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF165BDA)
                )
            ) {
                Text("Сохранить")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена", color = Color(0xFF165BDA))
            }
        }
    )
}