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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.klexifrontend.components.*
import com.example.klexifrontend.state.AppScreen
import com.example.klexifrontend.state.AppStateManager
import com.example.klexifrontend.theme.KlexiColors

@Composable
fun StudentDashboardScreen(stateManager: AppStateManager) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isWide = maxWidth > 700.dp
        if (isWide) {
            DesktopStudentDashboard(stateManager)
        } else {
            MobileStudentDashboard(stateManager)
        }
    }
}

// ----------------------------------------------------
// MOBILE STUDENT DASHBOARD (Image 1)
// ----------------------------------------------------
@Composable
private fun MobileStudentDashboard(stateManager: AppStateManager) {
    PlatformBackground(showBottomGround = true) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top Header Logo
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Klexi", fontSize = 42.sp, fontWeight = FontWeight.ExtraBold, color = KlexiColors.TextDark)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("⭐", fontSize = 28.sp)
                }
                Text("Aprende • Juega • Avanza", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = KlexiColors.PurplePrimary)

                Spacer(modifier = Modifier.height(8.dp))

                // Mascot Klexi Center Hero
                KlexiChameleon(
                    size = 170.dp,
                    equippedAccessory = stateManager.equippedAccessory
                )

                // Greeting Speech Bubble
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .shadow(4.dp, RoundedCornerShape(24.dp)),
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("¡Hola, ${stateManager.currentUser.fullName.split(" ").first()}!", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = KlexiColors.TextDark)
                            Text("¿Lista para una nueva aventura?", fontSize = 13.sp, color = KlexiColors.TextMuted)
                        }
                        IconButton(
                            onClick = { stateManager.currentScreen = AppScreen.GAME_PLAY },
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(KlexiColors.PurplePrimary)
                        ) {
                            Text("▶", fontSize = 20.sp, color = Color.White)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 4 Main Tiles (Juegos, Actividades, Logros, Tienda)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ActionTile(
                        title = "Juegos",
                        emoji = "🎮",
                        modifier = Modifier.weight(1f),
                        onClick = { stateManager.currentScreen = AppScreen.GAME_PLAY }
                    )
                    ActionTile(
                        title = "Actividades",
                        emoji = "📖",
                        modifier = Modifier.weight(1f),
                        onClick = { stateManager.currentScreen = AppScreen.GAME_PLAY }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ActionTile(
                        title = "Logros",
                        emoji = "🏆",
                        modifier = Modifier.weight(1f),
                        onClick = { stateManager.currentScreen = AppScreen.KLEXI_SHOP }
                    )
                    ActionTile(
                        title = "Tienda",
                        emoji = "🏪",
                        modifier = Modifier.weight(1f),
                        onClick = { stateManager.currentScreen = AppScreen.KLEXI_SHOP }
                    )
                }
            }

            // Bottom Profile Stats Bar
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .shadow(6.dp, RoundedCornerShape(24.dp)),
                shape = RoundedCornerShape(24.dp),
                color = KlexiColors.CardBackground
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .clip(CircleShape)
                                .background(KlexiColors.PurpleLight),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("👧", fontSize = 28.sp)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            StarPointsBadge(
                                level = stateManager.currentUser.level,
                                points = stateManager.currentUser.points
                            )
                        }
                    }
                    IconButton(
                        onClick = { stateManager.currentScreen = AppScreen.ROLE_SELECTION },
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(KlexiColors.PurpleLight)
                    ) {
                        Text("⚙️", fontSize = 20.sp)
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------
// DESKTOP STUDENT DASHBOARD (Image 2)
// ----------------------------------------------------
@Composable
private fun DesktopStudentDashboard(stateManager: AppStateManager) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(KlexiColors.SkyLight)
    ) {
        // Left Sidebar (Navigation)
        Surface(
            modifier = Modifier
                .width(240.dp)
                .fillMaxHeight()
                .shadow(4.dp),
            color = KlexiColors.PurpleLight
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // User Profile Header
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(3.dp, KlexiColors.PurplePrimary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("👧", fontSize = 48.sp)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(stateManager.currentUser.fullName, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = KlexiColors.TextDark)
                StarPointsBadge(level = stateManager.currentUser.level, points = stateManager.currentUser.points)

                Spacer(modifier = Modifier.height(28.dp))

                // Nav Items
                SidebarNavItem("🏠", "Mi Panel", active = true) {}
                SidebarNavItem("🎯", "Mis Metas", active = false) {}
                SidebarNavItem("📊", "Mi Avance", active = false) { stateManager.currentScreen = AppScreen.TUTOR_PANEL }
                SidebarNavItem("🏆", "Logros", active = false) { stateManager.currentScreen = AppScreen.KLEXI_SHOP }
                SidebarNavItem("🏪", "Tienda", active = false) { stateManager.currentScreen = AppScreen.KLEXI_SHOP }
                SidebarNavItem("⚙️", "Configuración", active = false) { stateManager.currentScreen = AppScreen.ROLE_SELECTION }
            }
        }

        // Main Desktop Body
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {
            // Top Nav Header Bar
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                color = Color.White,
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        KlexiChameleon(size = 50.dp, equippedAccessory = stateManager.equippedAccessory)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Klexi", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = KlexiColors.PurplePrimary)
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        HeaderTab("Inicio", active = true) {}
                        HeaderTab("Juegos", active = false) { stateManager.currentScreen = AppScreen.GAME_PLAY }
                        HeaderTab("Actividades", active = false) { stateManager.currentScreen = AppScreen.GAME_PLAY }
                        HeaderTab("Logros", active = false) { stateManager.currentScreen = AppScreen.KLEXI_SHOP }
                        HeaderTab("Tienda", active = false) { stateManager.currentScreen = AppScreen.KLEXI_SHOP }
                    }

                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(KlexiColors.PurpleLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("👧", fontSize = 26.sp)
                    }
                }
            }

            // Platform Hero Background with Winking Klexi
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                PlatformBackground(showBottomGround = true, groundHeight = 40.dp) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 30.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        KlexiChameleon(
                            size = 160.dp,
                            equippedAccessory = stateManager.equippedAccessory,
                            isCheering = true
                        )

                        Spacer(modifier = Modifier.width(20.dp))

                        // Speech Bubble
                        Surface(
                            shape = RoundedCornerShape(24.dp),
                            color = Color.White,
                            shadowElevation = 4.dp
                        ) {
                            Column(modifier = Modifier.padding(18.dp)) {
                                Text(
                                    "¡Hola, ${stateManager.currentUser.fullName.split(" ").first()}!",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = KlexiColors.TextDark
                                )
                                Text(
                                    "Sigue aprendiendo con Klexi ❤️",
                                    fontSize = 16.sp,
                                    color = KlexiColors.PurplePrimary
                                )
                            }
                        }
                    }
                }
            }

            // 3 Multi-card widgets (Tu progreso, Tus metas, Tus logros)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Card 1: Tu progreso
                KlexiCard(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("📊", fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Tu progreso", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = KlexiColors.TextDark)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        DonutProgressChart(percentage = 75, size = 110.dp)

                        Column {
                            LegendItem(color = Color(0xFF86EFAC), label = "Lectura")
                            LegendItem(color = Color(0xFFA855F7), label = "Escritura")
                            LegendItem(color = Color(0xFFFB923C), label = "Comprensión")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    KlexiButton(
                        text = "Ver detalle  ➔",
                        onClick = { stateManager.currentScreen = AppScreen.TUTOR_PANEL },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // Card 2: Tus metas
                KlexiCard(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🎯", fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Tus metas", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = KlexiColors.TextDark)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        color = KlexiColors.PurpleLight
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Leer un cuento sin errores", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = KlexiColors.TextDark)
                            Spacer(modifier = Modifier.height(12.dp))
                            LinearProgressIndicator(
                                progress = { 0.6f },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(12.dp)
                                    .clip(RoundedCornerShape(6.dp)),
                                color = Color(0xFF22C55E),
                                trackColor = Color.White
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("3 / 5 ⭐", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = KlexiColors.PurplePrimary)
                        }
                    }
                }

                // Card 3: Tus logros
                KlexiCard(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🏆", fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Tus logros", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = KlexiColors.TextDark)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("¡Excelente!", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = KlexiColors.TextDark)
                        Text(
                            "Has desbloqueado un nuevo artículo para Klexi",
                            fontSize = 13.sp,
                            color = KlexiColors.TextMuted,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        KlexiButton(
                            text = "Ver tienda  ➔",
                            onClick = { stateManager.currentScreen = AppScreen.KLEXI_SHOP },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

// Helper Components
@Composable
private fun ActionTile(title: String, emoji: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Surface(
        modifier = modifier
            .height(110.dp)
            .shadow(4.dp, RoundedCornerShape(24.dp))
            .border(2.dp, KlexiColors.CardBorder, RoundedCornerShape(24.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(KlexiColors.PurpleLight),
                contentAlignment = Alignment.Center
            ) {
                Text(emoji, fontSize = 26.sp)
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = KlexiColors.TextDark)
        }
    }
}

@Composable
private fun SidebarNavItem(emoji: String, label: String, active: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = if (active) KlexiColors.PurplePrimary else Color.Transparent
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(emoji, fontSize = 20.sp)
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                label,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = if (active) Color.White else KlexiColors.TextDark
            )
        }
    }
}

@Composable
private fun HeaderTab(label: String, active: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() },
        color = if (active) KlexiColors.PurplePrimary else Color.Transparent
    ) {
        Text(
            label,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = if (active) Color.White else KlexiColors.TextDark,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}
