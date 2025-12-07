package com.example.petcare.ui.auth.view

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
fun RegistrationScreen(
    viewModel: AuthViewModel,
    onNavigateToLogin: () -> Unit,
    onRegistrationSuccess: () -> Unit
) {
    val registerState by viewModel.registerState.collectAsState()

    LaunchedEffect(registerState) {
        if (registerState is AuthState.Success) {
            onRegistrationSuccess()
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
                contentDescription = "Регистрация",
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Заголовок
            Text(
                text = "Создайте аккаунт",
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
                text = "Зарегистрируйтесь для начала работы",
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
                    value = viewModel.username,
                    onValueChange = { viewModel.updateUsername(it) },
                    label = {
                        Text(
                            "Имя пользователя",
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
                    value = viewModel.email,
                    onValueChange = { viewModel.updateEmail(it) },
                    label = {
                        Text(
                            "Email",
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
                    value = viewModel.password,
                    onValueChange = { viewModel.updatePassword(it) },
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

                OutlinedTextField(
                    value = viewModel.confirmPassword,
                    onValueChange = { viewModel.updateConfirmPassword(it) },
                    label = {
                        Text(
                            "Подтвердите пароль",
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
            when (registerState) {
                is AuthState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color(0xFFDCEE7D)
                    )
                }
                is AuthState.Error -> {
                    Text(
                        text = (registerState as AuthState.Error).message,
                        color = Color(0xFFFF6B6B),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                else -> {}
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Кнопка регистрации
            Button(
                onClick = { viewModel.register() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFDCEE7D),
                    contentColor = Color(0xFF165BDA)
                ),
                enabled = registerState !is AuthState.Loading
            ) {
                if (registerState is AuthState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color(0xFF165BDA),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Зарегистрироваться",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Ссылка на вход
            TextButton(
                onClick = onNavigateToLogin,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Уже есть аккаунт? Войти",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}