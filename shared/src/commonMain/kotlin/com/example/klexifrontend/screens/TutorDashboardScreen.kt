package com.example.klexifrontend.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.klexifrontend.components.*
import com.example.klexifrontend.state.AppScreen
import com.example.klexifrontend.state.AppStateManager
import com.example.klexifrontend.theme.KlexiColors

@Composable
fun TutorDashboardScreen(stateManager: AppStateManager) {
    var selectedTab by remember { mutableStateOf(0) } // 0: Resumen, 1: Observaciones, 2: Recomendaciones

    PlatformBackground(showBottomGround = true) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = { stateManager.currentScreen = AppScreen.ROLE_SELECTION },
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                ) {
                    Text("←", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = KlexiColors.TextDark)
                }

                Text(
                    text = "Perfil de Tutor",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = KlexiColors.TextDark
                )

                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                ) {
                    Text("⚙️", fontSize = 20.sp)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Main Card (Image 3 Right & Image 9 Left)
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .weight(1f)
                    .shadow(10.dp, RoundedCornerShape(32.dp)),
                shape = RoundedCornerShape(32.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Tutor Avatar & Name
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(KlexiColors.PurpleLight),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("👩‍🏫", fontSize = 36.sp)
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Text(
                                text = stateManager.currentUser.tutorFullName ?: "María García",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = KlexiColors.TextDark
                            )
                            Text(
                                text = "Tutor de ${stateManager.currentUser.fullName}",
                                fontSize = 14.sp,
                                color = KlexiColors.TextMuted
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // 3 Tabs (Resumen, Observaciones, Recomendaciones)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(KlexiColors.PurpleLight, RoundedCornerShape(20.dp))
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TabButton("Resumen", selectedTab == 0, modifier = Modifier.weight(1f)) { selectedTab = 0 }
                        TabButton("Observaciones", selectedTab == 1, modifier = Modifier.weight(1f)) { selectedTab = 1 }
                        TabButton("Recomendaciones", selectedTab == 2, modifier = Modifier.weight(1f)) { selectedTab = 2 }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Tab Content
                    when (selectedTab) {
                        0 -> {
                            // Resumen Content
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                // Chart Box
                                Surface(
                                    modifier = Modifier
                                        .weight(1.2f)
                                        .border(1.5.dp, KlexiColors.CardBorder, RoundedCornerShape(20.dp)),
                                    shape = RoundedCornerShape(20.dp),
                                    color = KlexiColors.CardBackground
                                ) {
                                    Column(modifier = Modifier.padding(14.dp)) {
                                        Text("Avance general", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = KlexiColors.TextDark)
                                        Spacer(modifier = Modifier.height(10.dp))
                                        SimpleBarChart(data = listOf(20f, 35f, 45f, 60f, 78f, 90f))
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text("Últimos 6 meses", fontSize = 11.sp, color = KlexiColors.TextMuted)
                                    }
                                }

                                // Áreas a reforzar Box
                                Surface(
                                    modifier = Modifier
                                        .weight(1f)
                                        .border(1.5.dp, KlexiColors.CardBorder, RoundedCornerShape(20.dp)),
                                    shape = RoundedCornerShape(20.dp),
                                    color = KlexiColors.CardBackground
                                ) {
                                    Column(modifier = Modifier.padding(14.dp)) {
                                        Text("Áreas a reforzar", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = KlexiColors.TextDark)
                                        Spacer(modifier = Modifier.height(10.dp))
                                        LegendItem(color = Color(0xFF86EFAC), label = "Lectura")
                                        LegendItem(color = Color(0xFFA855F7), label = "Escritura")
                                        LegendItem(color = Color(0xFFFB923C), label = "Comprensión")
                                    }
                                }
                            }
                        }

                        1 -> {
                            // Observaciones Content
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Text("Observaciones del Terapeuta", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = KlexiColors.TextDark)
                                Spacer(modifier = Modifier.height(12.dp))
                                stateManager.therapistNotes.forEach { note ->
                                    Surface(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 6.dp),
                                        shape = RoundedCornerShape(16.dp),
                                        color = KlexiColors.CardBackground
                                    ) {
                                        Row(modifier = Modifier.padding(12.dp)) {
                                            Text("✓ ", color = Color(0xFF22C55E), fontWeight = FontWeight.Bold)
                                            Column {
                                                Text(note.text, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = KlexiColors.TextDark)
                                                Text("${note.area} • ${note.date}", fontSize = 11.sp, color = KlexiColors.TextMuted)
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        2 -> {
                            // Recomendaciones Content
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(20.dp),
                                color = KlexiColors.CardBackground
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text("💡", fontSize = 28.sp)
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text("Sugerencia de Práctica:", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = KlexiColors.TextDark)
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            "Practicar ejercicios de lectura con palabras cortas y reforzar la comprensión de textos diariamente.",
                                            fontSize = 14.sp,
                                            color = KlexiColors.TextMuted
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TabButton(title: String, active: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        color = if (active) Color.White else Color.Transparent,
        shadowElevation = if (active) 2.dp else 0.dp
    ) {
        Box(
            modifier = Modifier.padding(vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = if (active) KlexiColors.PurplePrimary else KlexiColors.TextMuted
            )
        }
    }
}
