package com.example.petcare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.petcare.data.repository.OnboardingRepository
import com.example.petcare.ui.doctors.view.DoctorSelectionScreen
import com.example.petcare.ui.health.view.HealthScreen
import com.example.petcare.ui.onboarding.view.OnboardingScreen
import com.example.petcare.ui.onboarding.viewmodel.OnboardingViewModel
import com.example.petcare.ui.onboarding.viewmodel.OnboardingViewModelFactory
import com.example.petcare.ui.profile.view.ProfileScreen
import com.example.petcare.ui.registration.view.PetAgeScreen
import com.example.petcare.ui.registration.view.PetRegistrationScreen
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
                    MainApp()
                }
            }
        }
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = "onboarding"
    ) {
        composable("onboarding") {
            val repository = remember { OnboardingRepository(context) }
            val onboardingViewModel: OnboardingViewModel = viewModel(
                factory = OnboardingViewModelFactory(
                    repository = repository
                )
            )
            OnboardingScreen(
                viewModel = onboardingViewModel,
                navController = navController
            )
        }
        composable("registration") {
            PetRegistrationScreen(navController = navController)
        }
        composable("age") {
            PetAgeScreen(navController = navController)
        }
        composable("main") {
            Text("Главный экран PetCare")
        }
        composable("doctors") {
            DoctorSelectionScreen(navController = navController)
        }
        composable("profile") {
            ProfileScreen(navController = navController)
        }
        composable("health") {
            HealthScreen(navController = navController)
        }
    }
}