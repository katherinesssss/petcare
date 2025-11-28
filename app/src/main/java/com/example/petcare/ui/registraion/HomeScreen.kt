package com.example.petcare.ui.registraion

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.petcare.ui.viewmodel.AuthViewModel
import com.example.petcare.ui.viewmodel.UserState


@Composable
fun HomeScreen(
    onLogout: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    val currentUser by viewModel.currentUser.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (currentUser) {
            is UserState.LoggedIn -> {
                val user = (currentUser as UserState.LoggedIn).user
                Text(
                    text = "Добро пожаловать, ${user.username}!",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = "Email: ${user.email}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
            }
            else -> {
                Text("Пользователь не авторизован")
            }
        }

        Button(
            onClick = {
                viewModel.logout()
                onLogout()
            }
        ) {
            Text("Выйти")
        }
    }
}