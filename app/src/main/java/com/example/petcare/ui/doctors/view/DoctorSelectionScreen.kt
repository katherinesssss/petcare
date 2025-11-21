package com.example.petcare.ui.doctors.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.petcare.ui.doctors.model.Doctor
import com.example.petcare.ui.doctors.model.DoctorUiState
import com.example.petcare.ui.doctors.viewmodel.DoctorViewModel

@Composable
fun DoctorSelectionScreen(
    navController: NavController? = null
) {
    val viewModel: DoctorViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    DoctorSelectionContent(
        uiState = uiState,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onDoctorSelected = { doctor ->
            navController?.navigate("doctor_details/${doctor.id}")
        },
        navController = navController
    )
}

@Composable
private fun DoctorSelectionContent(
    uiState: DoctorUiState,
    onSearchQueryChanged: (String) -> Unit,
    onDoctorSelected: (Doctor) -> Unit,
    navController: NavController?
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    0.0f to Color(0xFF165BDA),
                    0.6f to Color(0xFF165BDA),
                    1.0f to Color(0xFFFFFFFF)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp)
                .padding(bottom = 70.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Выберите врача",
                style = TextStyle(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            BasicTextField(
                value = uiState.searchQuery,
                onValueChange = onSearchQueryChanged,
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Black
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(Color.White)
                    .padding(horizontal = 20.dp),
                decorationBox = { innerTextField ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Поиск",
                            tint = Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        if (uiState.searchQuery.isEmpty()) {
                            Text(
                                text = "Поиск врачей...",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    color = Color.Gray
                                )
                            )
                        }
                        innerTextField()
                    }
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (uiState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    if (uiState.filteredDoctors.isEmpty()) {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(40.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Врачи не найдены",
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Попробуйте изменить запрос",
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        color = Color.White
                                    )
                                )
                            }
                        }
                    } else {
                        items(uiState.filteredDoctors) { doctor ->
                            DoctorItem(
                                doctor = doctor,
                                onDoctorSelected = onDoctorSelected
                            )
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .align(Alignment.BottomCenter)
                .background(Color.White)
        ) {
            TabBar(navController = navController, currentScreen = "doctors")
        }
    }
}

@Composable
private fun DoctorItem(
    doctor: Doctor,
    onDoctorSelected: (Doctor) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onDoctorSelected(doctor) },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFDCEE7D)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = doctor.name.split(" ").map { it.first() }.joinToString(""),
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF165BDA)
                    )
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = doctor.name,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = doctor.specialization,
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = doctor.experience,
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Color(0xFF165BDA)
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "⭐ ${doctor.rating} • ${doctor.reviews} отзывов",
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                )
            }

            Button(
                onClick = { onDoctorSelected(doctor) },
                modifier = Modifier
                    .height(36.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFDCEE7D),
                    contentColor = Color.Black
                )
            ) {
                Text(
                    text = "Запись",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun TabBar(
    navController: NavController? = null,
    currentScreen: String = "doctors"
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
            onClick = {
                navController?.navigate("main") {
                    popUpTo("doctors") { saveState = true }
                    launchSingleTop = true
                }
            }
        )

        TabItem(
            icon = Icons.Default.Person,
            title = "Врачи",
            isSelected = currentScreen == "doctors",
            onClick = {}
        )

        TabItem(
            icon = Icons.Default.Create,
            title = "Здоровье",
            isSelected = currentScreen == "health",
            onClick = {
                navController?.navigate("health") {
                    popUpTo("doctors") { saveState = true }
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
                    popUpTo("doctors") { saveState = true }
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
            style = TextStyle(
                fontSize = 10.sp,
                color = if (isSelected) Color(0xFF165BDA) else Color.Gray,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        )
    }
}