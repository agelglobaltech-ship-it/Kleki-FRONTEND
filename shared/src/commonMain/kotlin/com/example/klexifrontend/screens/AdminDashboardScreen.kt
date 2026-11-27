package com.example.klexifrontend.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
fun AdminDashboardScreen(stateManager: AppStateManager) {
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
                    text = "Panel Administrativo",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = KlexiColors.TextDark
                )

                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(KlexiColors.PurpleLight),
                    contentAlignment = Alignment.Center
                ) {
                    Text("👨‍💼", fontSize = 24.sp)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Main Admin Container (Image 9 Center)
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
                    // 4 KPI Cards Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        KpiStatCard("Usuarios", "1,248", "👥", Color(0xFFE0F2FE), modifier = Modifier.weight(1f))
                        KpiStatCard("Avanzaron", "892", "📈", Color(0xFFDCFCE7), modifier = Modifier.weight(1f))
                        KpiStatCard("Bajas", "102", "❌", Color(0xFFFEE2E2), modifier = Modifier.weight(1f))
                        KpiStatCard("Sin uso", "254", "⏸️", Color(0xFFF3E8FF), modifier = Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Monthly Reports Section
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        color = KlexiColors.CardBackground
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Reportes Mensuales", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = KlexiColors.TextDark)
                                Button(
                                    onClick = { },
                                    shape = RoundedCornerShape(16.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = KlexiColors.PurplePrimary)
                                ) {
                                    Text("Ver detalle >", fontSize = 12.sp)
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            SimpleBarChart(
                                data = listOf(30f, 45f, 60f, 50f, 75f, 90f, 110f, 130f),
                                barColor = KlexiColors.PurplePrimary,
                                modifier = Modifier.height(80.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // User Control Table
                    Text(
                        text = "Gestión y Monitoreo de Usuarios",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = KlexiColors.TextDark,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    stateManager.adminUsers.forEach { user ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            shape = RoundedCornerShape(16.dp),
                            color = KlexiColors.CardBackground
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("👤", fontSize = 20.sp)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(user.name, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = KlexiColors.TextDark)
                                        Text("${user.ageRange} • Tutor: ${user.tutorName}", fontSize = 12.sp, color = KlexiColors.TextMuted)
                                    }
                                }

                                Button(
                                    onClick = { stateManager.toggleUserLock(user.id) },
                                    shape = RoundedCornerShape(14.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (user.isLocked) Color(0xFF22C55E) else Color(0xFFEF4444)
                                    )
                                ) {
                                    Text(if (user.isLocked) "Desbloquear" else "Bloquear", fontSize = 12.sp, fontWeight = FontWeight.Bold)
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
private fun KpiStatCard(
    title: String,
    value: String,
    emoji: String,
    bgColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .height(100.dp)
            .shadow(4.dp, RoundedCornerShape(20.dp))
            .border(1.5.dp, KlexiColors.CardBorder, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        color = bgColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(emoji, fontSize = 22.sp)
            Text(title, fontSize = 11.sp, fontWeight = FontWeight.Medium, color = KlexiColors.TextDark)
            Text(value, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = KlexiColors.TextDark)
        }
    }
}
