package com.example.klexifrontend.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.klexifrontend.components.KlexiChameleon
import com.example.klexifrontend.components.PlatformBackground
import com.example.klexifrontend.components.StarPointsBadge
import com.example.klexifrontend.state.AppScreen
import com.example.klexifrontend.state.AppStateManager
import com.example.klexifrontend.theme.KlexiColors

@Composable
fun GamePlayScreen(stateManager: AppStateManager) {
    var selectedOption by remember { mutableStateOf<String?>(null) }
    var isCorrect by remember { mutableStateOf<Boolean?>(null) }

    val options = listOf("Manzana", "Hongo", "Mango")
    val correctAnswer = "Hongo"

    PlatformBackground(showBottomGround = true) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StarPointsBadge(level = stateManager.currentUser.level, points = stateManager.currentUser.points)

                IconButton(
                    onClick = { stateManager.currentScreen = AppScreen.STUDENT_HOME },
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(KlexiColors.PurplePrimary)
                ) {
                    Text("⏸", fontSize = 20.sp, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Instruction Speech Bar
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .shadow(4.dp, RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                color = Color.White
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("🔊", fontSize = 22.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        "Selecciona la palabra correcta",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = KlexiColors.TextDark
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Main Question Card with Image (Image 3 Left)
            Surface(
                modifier = Modifier
                    .size(200.dp)
                    .shadow(8.dp, RoundedCornerShape(28.dp))
                    .border(3.dp, KlexiColors.CardBorder, RoundedCornerShape(28.dp)),
                shape = RoundedCornerShape(28.dp),
                color = Color.White
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    // Mushroom / Hongo graphic representation
                    Text("🍄", fontSize = 110.sp)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Options List
            Column(
                modifier = Modifier.fillMaxWidth(0.85f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                options.forEach { option ->
                    val isThisSelected = selectedOption == option
                    val optionBg = when {
                        isThisSelected && isCorrect == true -> Color(0xFF22C55E)
                        isThisSelected && isCorrect == false -> Color(0xFFEF4444)
                        isThisSelected -> KlexiColors.PurplePrimary
                        else -> KlexiColors.CardBackground
                    }
                    val optionTextColor = if (isThisSelected) Color.White else KlexiColors.TextDark

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .shadow(4.dp, RoundedCornerShape(28.dp))
                            .border(2.dp, KlexiColors.CardBorder, RoundedCornerShape(28.dp))
                            .clickable {
                                selectedOption = option
                                val correct = (option == correctAnswer)
                                isCorrect = correct
                                if (correct) {
                                    stateManager.currentUser = stateManager.currentUser.copy(
                                        points = stateManager.currentUser.points + 10
                                    )
                                }
                            },
                        shape = RoundedCornerShape(28.dp),
                        color = optionBg
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = option,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = optionTextColor
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Cheering Mascot at bottom left
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 50.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Start
            ) {
                KlexiChameleon(
                    size = 130.dp,
                    equippedAccessory = stateManager.equippedAccessory,
                    isCheering = isCorrect == true
                )

                isCorrect?.let { correct ->
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = if (correct) Color(0xFFDCFCE7) else Color(0xFFFEE2E2),
                        shadowElevation = 4.dp
                    ) {
                        Text(
                            text = if (correct) "¡Excelente trabajo! 🎉 +10 pts" else "¡Inténtalo de nuevo! 💪",
                            modifier = Modifier.padding(12.dp),
                            fontWeight = FontWeight.Bold,
                            color = if (correct) Color(0xFF15803D) else Color(0xFFB91C1C)
                        )
                    }
                }
            }
        }
    }
}
