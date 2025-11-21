package com.example.petcare.ui.profile.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.petcare.ui.profile.model.ProfileEvent
import com.example.petcare.ui.profile.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    navController: NavController? = null,
    viewModel: ProfileViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Навигация при выходе из аккаунта
    LaunchedEffect(uiState.isLoggedOut) {
        if (uiState.isLoggedOut) {
            navController?.navigate("onboarding") {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    0.0f to Color(0xFF165BDA),
                    0.4f to Color(0xFF165BDA),
                    1.0f to Color(0xFFFFFFFF)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // Заголовок
            Text(
                text = "Профиль",
                style = TextStyle(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(40.dp))

            if (uiState.isLoading) {
                // Индикатор загрузки
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            } else {
                // Круг с аватаром профиля
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFDCEE7D)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = uiState.userData.avatarInitials,
                        style = TextStyle(
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF165BDA)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Карточка с информацией о пользователе
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    // ФИО
                    ProfileInfoItem(
                        title = "ФИО",
                        value = uiState.userData.fullName
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Электронная почта
                    ProfileInfoItem(
                        title = "Электронная почта",
                        value = uiState.userData.email
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Телефон
                    ProfileInfoItem(
                        title = "Телефон",
                        value = uiState.userData.phone
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Дата регистрации
                    ProfileInfoItem(
                        title = "В PetCare с",
                        value = uiState.userData.registrationDate
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Сообщение об ошибке
            uiState.error?.let { error ->
                Text(
                    text = error,
                    color = Color(0xFFFF6B6B),
                    modifier = Modifier.padding(16.dp)
                )
            }

            // Кнопка выхода
            Button(
                onClick = { viewModel.onEvent(ProfileEvent.OnLogoutClick) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF6B6B),
                    contentColor = Color.White
                ),
                shape = MaterialTheme.shapes.medium,
                enabled = !uiState.isLoading
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = "Выйти",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Выйти из аккаунта",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun ProfileInfoItem(
    title: String,
    value: String
) {
    Column {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 12.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Normal
            )
        )
    }
}

@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}