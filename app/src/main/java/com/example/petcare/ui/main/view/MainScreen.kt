package com.example.petcare.ui.main.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.petcare.ui.doctors.model.DoctorAppointment
import com.example.petcare.ui.doctors.viewmodel.DoctorViewModel

@Composable
fun MainScreen(navController: NavHostController) {
    val doctorViewModel: DoctorViewModel = viewModel()
    val appointments by doctorViewModel.appointments.collectAsState()

    val demoAppointments = listOf(
        DoctorAppointment(
            id = "1",
            doctorId = "doctor_1",
            doctorName = "Др. Иван Петров",
            doctorSpecialization = "Ветеринар-терапевт",
            date = "15.12.2023",
            time = "10:00",
            petName = "Барсик",
            reason = "Ежегодный осмотр"
        ),
        DoctorAppointment(
            id = "2",
            doctorId = "doctor_2",
            doctorName = "Др. Мария Сидорова",
            doctorSpecialization = "Ветеринар-дерматолог",
            date = "20.12.2023",
            time = "14:30",
            petName = "Шарик",
            reason = "Кожные проблемы"
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF165BDA),
                        Color(0xFF2A6BEA),
                        Color(0xFF4A85FA),
                        Color(0xFF7AAFFF),
                        Color(0xFFAFD5FF),
                        Color(0xFFFFFFFF)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 70.dp)
        ) {
            // Верхняя часть с заголовком - фиксированная высота
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.Transparent),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "PetCare",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Забота о вашем питомце",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.9f),
                    textAlign = TextAlign.Center
                )
            }

            // Белая карточка с записями - занимает оставшееся пространство
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp)
                    .offset(y = (-24).dp),
                shape = RoundedCornerShape(32.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                ) {
                    Text(
                        text = "Ближайшие записи",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )

                    val displayAppointments = if (appointments.isEmpty()) demoAppointments else appointments

                    if (displayAppointments.isEmpty()) {
                        EmptyAppointmentsState(navController)
                    } else {
                        AppointmentsList(displayAppointments)
                    }
                }
            }
        }

        // Tab Bar внизу экрана
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .align(Alignment.BottomCenter)
                .background(Color.White)
        ) {
            TabBar(navController = navController, currentScreen = "main")
        }
    }
}

@Composable
private fun EmptyAppointmentsState(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 32.dp)
        ) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Нет записей",
                modifier = Modifier.size(80.dp),
                tint = Color.Gray.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "У вас пока нет записей",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Запишитесь к врачу для осмотра питомца",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    navController.navigate("doctors")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF165BDA),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text(
                    text = "Найти врача",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun AppointmentsList(appointments: List<DoctorAppointment>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(appointments) { appointment ->
            AppointmentCard(appointment = appointment)
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun AppointmentCard(appointment: DoctorAppointment) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = appointment.doctorName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = appointment.doctorSpecialization,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF165BDA),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.LightGray.copy(alpha = 0.3f))
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoColumn("Дата", appointment.date, true)
                InfoColumn("Время", appointment.time, true)
                InfoColumn("Питомец", appointment.petName, true)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Причина: ${appointment.reason}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun InfoColumn(title: String, value: String, isBoldValue: Boolean = false) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isBoldValue) FontWeight.Bold else FontWeight.Medium,
            color = Color.Black
        )
    }
}

@Composable
private fun TabBar(
    navController: NavHostController? = null,
    currentScreen: String = "main"
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Color.White)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TabItem(
            icon = Icons.Default.Home,
            title = "Главная",
            isSelected = currentScreen == "main",
            onClick = {}
        )

        TabItem(
            icon = Icons.Default.Person,
            title = "Врачи",
            isSelected = currentScreen == "doctors",
            onClick = {
                navController?.navigate("doctors") {
                    popUpTo("main") { saveState = true }
                    launchSingleTop = true
                }
            }
        )

        TabItem(
            icon = Icons.Default.Favorite,
            title = "Здоровье",
            isSelected = currentScreen == "health",
            onClick = {
                navController?.navigate("health") {
                    popUpTo("main") { saveState = true }
                    launchSingleTop = true
                }
            }
        )

        TabItem(
            icon = Icons.Default.AccountCircle,
            title = "Профиль",
            isSelected = currentScreen == "profile",
            onClick = {
                navController?.navigate("profile") {
                    popUpTo("main") { saveState = true }
                    launchSingleTop = true
                }
            }
        )
    }
}

@Composable
private fun TabItem(
    icon: ImageVector,
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(70.dp)
            .clickable { onClick() }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = if (isSelected) Color(0xFF165BDA) else Color.Gray,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            color = if (isSelected) Color(0xFF165BDA) else Color.Gray,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}