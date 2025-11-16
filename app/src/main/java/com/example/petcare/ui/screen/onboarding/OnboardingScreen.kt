package com.example.petcare.ui.screen.onboarding
import androidx.compose.runtime.remember
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.petcare.data.repository.OnboardingRepository
import com.example.petcare.ui.theme.PetCareTheme
import androidx.compose.runtime.collectAsState
import com.example.petcare.R
import androidx.compose.ui.graphics.Brush

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel,
    navController: NavController? = null
) {
    val uiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(uiState.isCompleted) {
        if (uiState.isCompleted) {
            navController?.navigate("main") {
                popUpTo("onboarding") { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    0.0f to Color(0xFF165BDA), // 0% - синий
                    0.7f to Color(0xFF165BDA), // 80% - синий
                    1.0f to Color(0xFFFFFFFF)  // 100% - белый
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Пустое пространство сверху
            Spacer(modifier = Modifier.weight(1f))

            // Изображение по центру
            Image(
                painter = painterResource(id = R.drawable.bg_onboarding),
                contentDescription = "Логотип PetCare",
                modifier = Modifier
                    .size(300.dp),
                contentScale = ContentScale.Fit
            )

            // Пустое пространство между изображением и текстом
            Spacer(modifier = Modifier.weight(1f))

            // Текст и кнопка внизу
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Заголовок
                Text(
                    text = "Добрый день,\nэто PetCare",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Описание
                Text(
                    text = "Мы — сервис заботы для питомцев и их хозяев.",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 18.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Кнопка "Начать"
                Button(
                    onClick = { viewModel.onEvent(OnboardingEvent.OnStartButtonClick) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFDCEE7D), // Цвет #DCEE7D
                        contentColor = Color(color=0xFF165BDA) // Черный текст
                    ),
                    enabled = !uiState.isLoading
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color.Black,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = "Начать",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Отступ снизу
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}