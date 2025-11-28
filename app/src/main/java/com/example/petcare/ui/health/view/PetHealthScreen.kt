package com.example.petcare.ui.health.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.petcare.ui.health.model.*
import com.example.petcare.ui.health.viewmodel.PetHealthViewModel

@Composable
fun PetHealthScreen(
    navController: NavController? = null
) {
    val viewModel: PetHealthViewModel = viewModel()
    val pets by viewModel.pets.collectAsState()
    val selectedPet by viewModel.selectedPet.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val showAddPetDialog by viewModel.showAddPetDialog.collectAsState()

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
                .padding(bottom = 70.dp)
        ) {
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            } else {
                PetHealthContent(
                    pets = pets,
                    selectedPet = selectedPet,
                    onPetSelected = viewModel::selectPet,
                    onAddPetClick = viewModel::showAddPetDialog,
                    viewModel = viewModel
                )
            }
        }

        // Tab Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .align(Alignment.BottomCenter)
                .background(Color.White)
        ) {
            TabBar(navController = navController, currentScreen = "health")
        }

        if (showAddPetDialog) {
            AddPetDialog(
                onDismiss = viewModel::hideAddPetDialog,
                onAddPet = { newPetData ->
                    viewModel.addNewPet(newPetData)
                }
            )
        }
    }
}

@Composable
private fun PetHealthContent(
    pets: List<Pet>,
    selectedPet: Pet?,
    onPetSelected: (Pet) -> Unit,
    onAddPetClick: () -> Unit,
    viewModel: PetHealthViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp)
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Здоровье питомца",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Панель выбора питомца
        PetsSelectionRow(
            pets = pets,
            selectedPet = selectedPet,
            onPetSelected = onPetSelected,
            onAddPetClick = onAddPetClick
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Контент выбранного питомца
        if (selectedPet != null) {
            SelectedPetContent(
                pet = selectedPet,
                viewModel = viewModel
            )
        } else {
            NoPetsContent(onAddPetClick = onAddPetClick)
        }
    }
}

@Composable
private fun PetsSelectionRow(
    pets: List<Pet>,
    selectedPet: Pet?,
    onPetSelected: (Pet) -> Unit,
    onAddPetClick: () -> Unit
) {
    Column {
        Text(
            text = "Мои питомцы",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            pets.forEach { pet ->
                PetSelectionChip(
                    pet = pet,
                    isSelected = selectedPet?.id == pet.id,
                    onClick = { onPetSelected(pet) }
                )
            }

            AddPetChip(onClick = onAddPetClick)
        }
    }
}

@Composable
private fun PetSelectionChip(
    pet: Pet,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (isSelected) Color(0xFFDCEE7D)
                else Color(0x80FFFFFF)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = pet.name,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = if (isSelected) Color(0xFF165BDA) else Color.White
        )
    }
}

@Composable
private fun AddPetChip(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0x80FFFFFF))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "+",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
private fun SelectedPetContent(
    pet: Pet,
    viewModel: PetHealthViewModel
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            // Карточка питомца
            PetInfoCard(pet)

            Spacer(modifier = Modifier.height(20.dp))

            // Оценка здоровья
            HealthScoreCard(pet.healthData.healthScore)

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Показатели здоровья",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        item {
            HealthMetricsGrid(
                metrics = pet.healthData.healthMetrics,
                onMetricUpdate = { metricType, value ->
                    viewModel.updatePetHealthMetric(pet.id, metricType, value)
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Ежедневные активности",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        items(pet.healthData.dailyActivities) { activity ->
            DailyActivityItem(
                activity = activity,
                onToggleCompletion = {
                    viewModel.toggleActivityCompletion(pet.id, activity.activity)
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun PetInfoCard(pet: Pet) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = pet.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                text = "${pet.type.displayName()}, ${pet.breed}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoItem("Возраст", pet.age)
                InfoItem("Вес", "${pet.weight} кг")
                InfoItem("Последний визит", pet.healthData.lastVetVisit)
            }
        }
    }
}

@Composable
private fun HealthScoreCard(score: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Оценка здоровья",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$score%",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    color = getHealthScoreColor(score)
                )

                Spacer(modifier = Modifier.width(16.dp))

                LinearProgressIndicator(
                    progress = score / 100f,
                    modifier = Modifier
                        .weight(1f)
                        .height(12.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    color = getHealthScoreColor(score),
                    trackColor = Color(0xFFE0E0E0)
                )
            }
        }
    }
}

@Composable
private fun HealthMetricsGrid(
    metrics: List<HealthMetric>,
    onMetricUpdate: (MetricType, String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 400.dp)
    ) {
        items(metrics) { metric ->
            HealthMetricCard(
                metric = metric,
                onMetricUpdate = onMetricUpdate
            )
        }
    }
}

@Composable
private fun HealthMetricCard(
    metric: HealthMetric,
    onMetricUpdate: (MetricType, String) -> Unit
) {
    var showEditDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showEditDialog = true },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = metric.type.displayName(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )

            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(
                    text = metric.value,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = getStatusColor(metric.status)
                )

                Text(
                    text = metric.unit,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 4.dp, bottom = 2.dp)
                )
            }

            Text(
                text = metric.normalRange,
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }

    if (showEditDialog) {
        EditMetricDialog(
            metric = metric,
            onDismiss = { showEditDialog = false },
            onSave = { newValue: String ->
                onMetricUpdate(metric.type, newValue)
                showEditDialog = false
            }
        )
    }
}

@Composable
private fun DailyActivityItem(
    activity: DailyActivity,
    onToggleCompletion: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggleCompletion() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = activity.activity,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

                activity.time?.let { time ->
                    Text(
                        text = time,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }

            Checkbox(
                checked = activity.completed,
                onCheckedChange = { onToggleCompletion() },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFF165BDA),
                    checkmarkColor = Color.White
                )
            )
        }
    }
}

@Composable
private fun NoPetsContent(onAddPetClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Face,
            contentDescription = "No pets",
            modifier = Modifier.size(64.dp),
            tint = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Добавьте первого питомца",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Следите за здоровьем вашего питомца",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onAddPetClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFDCEE7D),
                contentColor = Color(0xFF165BDA)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = "Добавить питомца",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun InfoItem(title: String, value: String) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
    }
}

// Таб бар
@Composable
private fun TabBar(
    navController: NavController? = null,
    currentScreen: String = "health"
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Color.White)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TabItem(
            icon = Icons.Default.Home,
            title = "Главная",
            isSelected = currentScreen == "main",
            onClick = {
                navController?.navigate("main") {
                    popUpTo("health") { saveState = true }
                    launchSingleTop = true
                }
            }
        )

        TabItem(
            icon = Icons.Default.Person,
            title = "Врачи",
            isSelected = currentScreen == "doctors",
            onClick = {
                navController?.navigate("doctors") {
                    popUpTo("health") { saveState = true }
                    launchSingleTop = true
                }
            }
        )

        TabItem(
            icon = Icons.Default.Create,
            title = "Здоровье",
            isSelected = currentScreen == "health",
            onClick = {}
        )

        TabItem(
            icon = Icons.Default.AccountCircle,
            title = "Профиль",
            isSelected = currentScreen == "profile",
            onClick = {
                navController?.navigate("profile") {
                    popUpTo("health") { saveState = true }
                    launchSingleTop = true
                }
            }
        )
    }
}

@Composable
private fun TabItem(
    icon: ImageVector,
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(70.dp)
            .clickable { onClick() }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = if (isSelected) Color(0xFF165BDA) else Color.Gray,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            color = if (isSelected) Color(0xFF165BDA) else Color.Gray,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}