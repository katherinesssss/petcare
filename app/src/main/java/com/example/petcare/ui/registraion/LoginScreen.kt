package com.example.petcare.ui.registraion

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petcare.R
import com.example.petcare.ui.viewmodel.AuthViewModel
import com.example.petcare.ui.viewmodel.AuthState

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onNavigateToRegistration: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    val loginState by viewModel.loginState.collectAsState()

    LaunchedEffect(loginState) {
        if (loginState is AuthState.Success) {
            onLoginSuccess()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    0.0f to Color(0xFF165BDA),
                    0.7f to Color(0xFF165BDA),
                    1.0f to Color(0xFFFFFFFF)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(0.5f))

            // Логотип или иконка
            Image(
                painter = painterResource(id = R.drawable.cat),
                contentDescription = "Вход в систему",
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Заголовок
            Text(
                text = "Вход в аккаунт",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Войдите в свой аккаунт PetCare",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 16.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Поля формы
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = viewModel.loginCredential,
                    onValueChange = { viewModel.updateLoginCredential(it) },
                    label = {
                        Text(
                            "Email или имя пользователя",
                            color = Color.White
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        unfocusedLabelColor = Color.White,
                        focusedLabelColor = Color(0xFFDCEE7D),
                        unfocusedTextColor = Color.White,
                        focusedTextColor = Color.White,
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        cursorColor = Color.White,
                        unfocusedIndicatorColor = Color.White.copy(alpha = 0.7f),
                        focusedIndicatorColor = Color(0xFFDCEE7D)
                    )
                )

                OutlinedTextField(
                    value = viewModel.loginPassword,
                    onValueChange = { viewModel.updateLoginPassword(it) },
                    label = {
                        Text(
                            "Пароль",
                            color = Color.White
                        )
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        unfocusedLabelColor = Color.White,
                        focusedLabelColor = Color(0xFFDCEE7D),
                        unfocusedTextColor = Color.White,
                        focusedTextColor = Color.White,
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        cursorColor = Color.White,
                        unfocusedIndicatorColor = Color.White.copy(alpha = 0.7f),
                        focusedIndicatorColor = Color(0xFFDCEE7D)
                    )
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Сообщения об ошибках/успехе
            when (loginState) {
                is AuthState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color(0xFFDCEE7D)
                    )
                }
                is AuthState.Error -> {
                    Text(
                        text = (loginState as AuthState.Error).message,
                        color = Color(0xFFFF6B6B),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                else -> {}
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Кнопка входа
            Button(
                onClick = { viewModel.login() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFDCEE7D),
                    contentColor = Color(0xFF165BDA)
                ),
                enabled = loginState !is AuthState.Loading
            ) {
                if (loginState is AuthState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color(0xFF165BDA),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Войти",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Ссылка на регистрацию
            TextButton(
                onClick = onNavigateToRegistration,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Нет аккаунта? Зарегистрироваться",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}