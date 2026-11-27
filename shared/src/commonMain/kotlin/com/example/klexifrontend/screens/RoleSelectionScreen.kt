package com.example.klexifrontend.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.klexifrontend.components.PlatformBackground
import com.example.klexifrontend.models.UserRole
import com.example.klexifrontend.state.AppScreen
import com.example.klexifrontend.state.AppStateManager
import com.example.klexifrontend.theme.KlexiColors

@Composable
fun RoleSelectionScreen(stateManager: AppStateManager) {
    PlatformBackground(showBottomGround = true) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { stateManager.currentScreen = AppScreen.WELCOME },
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                ) {
                    Text("←", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = KlexiColors.TextDark)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Container Card matching Image 6
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .shadow(8.dp, RoundedCornerShape(32.dp)),
                shape = RoundedCornerShape(32.dp),
                color = KlexiColors.SkyLight
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "¿Quién usará Klexi?",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = KlexiColors.TextDark,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    // 3 Role Cards in Responsive Layout
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        RoleCard(
                            title = "Usuario",
                            subtitle = "(Alumno)",
                            emoji = "👧",
                            modifier = Modifier.weight(1f),
                            onClick = {
                                stateManager.selectedRole = UserRole.ALUMNO
                                stateManager.currentScreen = AppScreen.LOGIN
                            }
                        )

                        RoleCard(
                            title = "Tutor",
                            subtitle = "(Padres)",
                            emoji = "👨‍👩‍👧",
                            modifier = Modifier.weight(1f),
                            onClick = {
                                stateManager.selectedRole = UserRole.TUTOR
                                stateManager.currentScreen = AppScreen.LOGIN
                            }
                        )

                        RoleCard(
                            title = "Otros",
                            subtitle = "(Admin/Terapeuta)",
                            emoji = "⚙️",
                            modifier = Modifier.weight(1f),
                            onClick = {
                                stateManager.selectedRole = UserRole.ADMIN
                                stateManager.currentScreen = AppScreen.LOGIN
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RoleCard(
    title: String,
    subtitle: String,
    emoji: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .height(180.dp)
            .shadow(6.dp, RoundedCornerShape(24.dp))
            .border(2.dp, KlexiColors.CardBorder, RoundedCornerShape(24.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        color = KlexiColors.CardBackground
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(KlexiColors.PurpleLight),
                contentAlignment = Alignment.Center
            ) {
                Text(emoji, fontSize = 36.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = KlexiColors.TextDark,
                textAlign = TextAlign.Center
            )
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = KlexiColors.TextMuted,
                textAlign = TextAlign.Center
            )
        }
    }
}
