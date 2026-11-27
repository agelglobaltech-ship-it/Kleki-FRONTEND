package com.example.klexifrontend.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.klexifrontend.components.KlexiButton
import com.example.klexifrontend.components.KlexiChameleon
import com.example.klexifrontend.components.PlatformBackground
import com.example.klexifrontend.state.AppScreen
import com.example.klexifrontend.state.AppStateManager
import com.example.klexifrontend.theme.KlexiColors

@Composable
fun WelcomeScreen(stateManager: AppStateManager) {
    PlatformBackground(showBottomGround = true) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Main Logo & Chameleon Hero
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Glowing Star Top
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(KlexiColors.StarYellow),
                    contentAlignment = Alignment.Center
                ) {
                    Text("⭐", fontSize = 28.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Klexi",
                    fontSize = 54.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = KlexiColors.TextDark
                )

                Text(
                    text = "Aprende • Juega • Avanza",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = KlexiColors.PurplePrimary
                )

                Spacer(modifier = Modifier.height(30.dp))

                // Mascot Klexi
                KlexiChameleon(
                    size = 220.dp,
                    equippedAccessory = stateManager.equippedAccessory,
                    isCheering = true
                )
            }

            // Bottom Action Card
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(bottom = 60.dp),
                shape = RoundedCornerShape(28.dp),
                color = Color.White.copy(alpha = 0.95f),
                shadowElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "¡Hola!",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = KlexiColors.TextDark
                    )
                    Text(
                        text = "Mejorar la lectura y escritura jugando",
                        fontSize = 16.sp,
                        color = KlexiColors.TextMuted,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    KlexiButton(
                        text = "Comenzar  ➔",
                        onClick = { stateManager.currentScreen = AppScreen.ROLE_SELECTION },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
