import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
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
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun PetRegistrationScreen(navController: NavController? = null) {
    var petName by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf<Gender?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    0.0f to Color(0xFF165BDA),
                    0.6f to Color(0xFF165BDA),  // Белый начинается раньше
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
            // Верхняя часть (синяя)
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

                BasicTextField(
                    value = petName,
                    onValueChange = { petName = it },
                    textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .padding(horizontal = 16.dp),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.padding(vertical = 16.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (petName.isEmpty()) {
                                Text(
                                    text = "Например, Пушок",
                                    style = TextStyle(fontSize = 18.sp, color = Color.Gray)
                                )
                            }
                            innerTextField()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(30.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { selectedGender = Gender.MALE },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .padding(end = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedGender == Gender.MALE) Color(0xFFDCEE7D) else Color.White,
                            contentColor = if (selectedGender == Gender.MALE) Color.Black else Color.Gray
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
                        onClick = { selectedGender = Gender.FEMALE },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .padding(start = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedGender == Gender.FEMALE) Color(0xFFDCEE7D) else Color.White,
                            contentColor = if (selectedGender == Gender.FEMALE) Color.Black else Color.Gray
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

            // Нижняя часть (белая) - где находится кнопка
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { navController?.navigate("age") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFDCEE7D),
                        contentColor = Color.Black
                    ),
                    enabled = petName.isNotEmpty() && selectedGender != null
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

enum class Gender { MALE, FEMALE }


@Composable
fun PetRegistrationScreenPreview() {
    PetRegistrationScreen()
}