// DoctorDetailScreen.kt (обновленная версия)
package com.example.petcare.ui.doctors.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.petcare.ui.doctors.viewmodel.DoctorViewModel

@Composable
fun DoctorDetailScreen(
    navController: NavController,
    doctorId: String?,
    viewModel: DoctorViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val showAppointmentDialog by viewModel.showAppointmentDialog.collectAsState()

    // Находим врача по ID
    val doctor = uiState.doctors.find { it.id == doctorId } ?: run {
        // Если врач не найден, показываем ошибку
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        0.0f to Color(0xFF165BDA),
                        1.0f to Color(0xFFFFFFFF)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Врач не найден",
                color = Color.White,
                fontSize = 18.sp
            )
        }
        return
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    0.0f to Color(0xFF165BDA),
                    0.3f to Color(0xFF165BDA),
                    1.0f to Color(0xFFFFFFFF)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Верхняя часть с основной информацией
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            0.0f to Color(0xFF165BDA),
                            1.0f to Color(0xFF165BDA)
                        )
                    )
            ) {
                // Кнопка назад
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .padding(16.dp)
                        .size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Назад",
                        tint = Color.White
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Аватар врача
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFDCEE7D)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = doctor.name.split(" ").map { it.first() }.joinToString(""),
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF165BDA),
                                fontSize = 32.sp
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = doctor.name,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        ),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = doctor.specialization,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.White.copy(alpha = 0.9f),
                            textAlign = TextAlign.Center
                        ),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Рейтинг и опыт
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Рейтинг",
                                tint = Color(0xFFDCEE7D),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${doctor.rating} (${doctor.reviews} отзывов)",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Color.White
                                )
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Опыт",
                                tint = Color(0xFFDCEE7D),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${doctor.experienceYears} лет",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Color.White
                                )
                            )
                        }
                    }
                }
            }

            // Детальная информация
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .offset(y = (-24).dp),
                shape = MaterialTheme.shapes.extraLarge,
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    // Образование
                    InfoSection(
                        icon = Icons.Default.Home,
                        title = "Образование",
                        content = doctor.education
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // О враче
                    InfoSection(
                        icon = Icons.Default.Person,
                        title = "О враче",
                        content = doctor.description
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Языки
                    InfoSection(
                        icon = Icons.Default.Info,
                        title = "Языки",
                        content = doctor.languages.joinToString(", ")
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Услуги
                    InfoSection(
                        icon = Icons.Default.Face,
                        title = "Услуги",
                        content = doctor.services.joinToString(" • ")
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Местоположение
                    InfoSection(
                        icon = Icons.Default.LocationOn,
                        title = "Местоположение",
                        content = doctor.location
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Доступное время
                    InfoSection(
                        icon = Icons.Default.DateRange,
                        title = "Доступное время",
                        content = doctor.availableSlots.joinToString(", ")
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Цена и кнопка записи
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Стоимость консультации",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color.Gray
                                )
                            )
                            Text(
                                text = doctor.price,
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF165BDA)
                                )
                            )
                        }

                        Button(
                            onClick = {
                                viewModel.showAppointmentDialog(doctor)
                            },
                            modifier = Modifier.height(50.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF165BDA),
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = "Записаться",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }

        // Диалог записи
        if (showAppointmentDialog) {
            CreateAppointmentDialog(
                viewModel = viewModel,
                onDismiss = viewModel::hideAppointmentDialog
            )
        }
    }
}

@Composable
fun InfoSection(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    content: String
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color(0xFF165BDA),
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF165BDA)
                )
            )
        }
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.Black,
                lineHeight = 20.sp
            )
        )
    }
}