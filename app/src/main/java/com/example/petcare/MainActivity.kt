package com.example.petcare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.petcare.data.repository.OnboardingRepository
import com.example.petcare.ui.screen.onboarding.OnboardingScreen
import com.example.petcare.ui.screen.onboarding.OnboardingViewModel
import com.example.petcare.ui.screen.onboarding.OnboardingViewModelFactory
import com.example.petcare.ui.screen.registration.PetRegistrationScreen
import com.example.petcare.ui.theme.PetCareTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetCareTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    // Правильное создание ViewModel через Factory
                    val onboardingViewModel: OnboardingViewModel = viewModel(
                        factory = OnboardingViewModelFactory(
                            repository = OnboardingRepository()
                        )
                    )

                    NavHost(
                        navController = navController,
                        startDestination = "onboarding"
                    ) {
                        composable("onboarding") {
                            OnboardingScreen(
                                viewModel = onboardingViewModel,
                                navController = navController
                            )
                        }
                        composable("registration") {
                            PetRegistrationScreen(navController = navController)
                        }
                        composable("main") {
                            Text("Главный экран PetCare")
                        }
                    }
                }
            }
        }
    }
}