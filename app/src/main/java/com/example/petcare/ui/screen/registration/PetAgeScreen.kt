package com.example.petcare.ui.screen.registration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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

@Composable
fun PetAgeScreen(
    navController: NavController? = null
) {
    var selectedYears by remember { mutableStateOf<Int?>(null) }
    var selectedMonths by remember { mutableStateOf<Int?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    0.0f to Color(0xFF165BDA),
                    0.6f to Color(0xFF165BDA), // Увеличил белую область
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
            // Верхняя часть с контентом
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                Text(
                    text = "Сколько лет вашей кошке?",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Контейнер для двух колес прокрутки
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Колесо лет
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
                            selectedItem = selectedYears,
                            onItemSelected = { selectedYears = it },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Колесо месяцев
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
                            items = (0..11).toList(),
                            selectedItem = selectedMonths,
                            onItemSelected = { selectedMonths = it },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Нижняя часть с кнопкой (на белом фоне)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        navController?.navigate("next_screen")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFDCEE7D),
                        contentColor = Color.Black
                    ),
                    enabled = selectedYears != null && selectedMonths != null
                ) {
                    Text(
                        text = "Продолжить",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF165BDA)
                    )
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
        // Линия выбора (как в iOS)
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
            // Добавляем отступы сверху и снизу для полной прокрутки
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

        // Градиенты сверху и снизу для эффекта размытия
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

@Composable
fun PetAgeScreenPreview() {
    PetAgeScreen()
}