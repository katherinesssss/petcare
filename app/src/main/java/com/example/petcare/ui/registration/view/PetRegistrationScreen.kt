package com.example.petcare.ui.registration.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.petcare.ui.registration.model.Gender
import com.example.petcare.ui.registration.model.RegistrationEvent
import com.example.petcare.ui.registration.viewmodel.RegistrationViewModel

@Composable
fun PetRegistrationScreen(
    navController: NavController? = null,
    viewModel: RegistrationViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

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
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(60.dp))

                Text(
                    text = "Как зовут вашего питомца?",
                    style = TextStyle(
                        fontSize = 24.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(30.dp))

                // Исправленный BasicTextField
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                ) {
                    BasicTextField(
                        value = uiState.petName,
                        onValueChange = { viewModel.onEvent(RegistrationEvent.OnPetNameChanged(it)) },
                        textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                            .align(Alignment.CenterStart),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (uiState.petName.isEmpty()) {
                                    Text(
                                        text = "Например, Пушок",
                                        style = TextStyle(fontSize = 18.sp, color = Color.Gray)
                                    )
                                }
                                innerTextField() // Теперь innerTextField доступен
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { viewModel.onEvent(RegistrationEvent.OnGenderSelected(Gender.MALE)) },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .padding(end = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (uiState.selectedGender == Gender.MALE) Color(0xFFDCEE7D) else Color.White,
                            contentColor = if (uiState.selectedGender == Gender.MALE) Color.Black else Color.Gray
                        )
                    ) {
                        Text(
                            text = "Мальчик",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF165BDA)
                        )
                    }

                    Button(
                        onClick = { viewModel.onEvent(RegistrationEvent.OnGenderSelected(Gender.FEMALE)) },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .padding(start = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (uiState.selectedGender == Gender.FEMALE) Color(0xFFDCEE7D) else Color.White,
                            contentColor = if (uiState.selectedGender == Gender.FEMALE) Color.Black else Color.Gray
                        )
                    ) {
                        Text(
                            text = "Девочка",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF165BDA)
                        )
                    }
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        viewModel.onEvent(RegistrationEvent.OnContinueRegistration)
                        navController?.navigate("age")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFDCEE7D),
                        contentColor = Color.Black
                    ),
                    enabled = viewModel.isRegistrationValid() && !uiState.isLoading
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color(0xFF165BDA),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = "Продолжить",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF165BDA)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}