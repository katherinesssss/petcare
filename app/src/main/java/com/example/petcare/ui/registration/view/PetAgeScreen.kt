package com.example.petcare.ui.registration.view

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
import androidx.compose.foundation.clickable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.petcare.ui.registration.model.RegistrationEvent
import com.example.petcare.ui.registration.viewmodel.RegistrationViewModel

@Composable
fun PetAgeScreen(
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
                Spacer(modifier = Modifier.height(40.dp))

                Text(
                    text = "Сколько лет вашему питомцу?",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(40.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Лет",
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Medium
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        WheelPicker(
                            items = (0..20).toList(),
                            selectedItem = uiState.selectedYears,
                            onItemSelected = { viewModel.onEvent(RegistrationEvent.OnYearsSelected(it)) },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Месяцев",
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Medium
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        WheelPicker(
                            items = (0..12).toList(),
                            selectedItem = uiState.selectedMonths,
                            onItemSelected = { viewModel.onEvent(RegistrationEvent.OnMonthsSelected(it)) },
                            modifier = Modifier.weight(1f)
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
                        viewModel.onEvent(RegistrationEvent.OnContinueToDoctors)
                        navController?.navigate("doctors")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFDCEE7D),
                        contentColor = Color.Black
                    ),
                    enabled = viewModel.isAgeSelectionValid() && !uiState.isLoading
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

@Composable
fun WheelPicker(
    items: List<Int>,
    selectedItem: Int?,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = selectedItem ?: 0
    )

    Box(
        modifier = modifier
            .width(80.dp)
            .height(180.dp) // Фиксированная высота для прокрутки
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White.copy(alpha = 0.2f)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(Color.White)
                .align(Alignment.Center)
        )

        LazyColumn(
            state = listState,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp)
        ) {
            item { Spacer(modifier = Modifier.height(60.dp)) }

            items(items) { item ->
                Text(
                    text = item.toString(),
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = if (item == selectedItem) Color.White else Color.White.copy(alpha = 0.6f),
                        fontWeight = if (item == selectedItem) FontWeight.Bold else FontWeight.Normal
                    ),
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .clickable { onItemSelected(item) }
                )
            }

            item { Spacer(modifier = Modifier.height(60.dp)) }
        }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF165BDA),
                            Color.Transparent
                        )
                    )
                )
                .align(Alignment.TopCenter)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color(0xFF165BDA)
                        )
                    )
                )
                .align(Alignment.BottomCenter)
        )
    }
}