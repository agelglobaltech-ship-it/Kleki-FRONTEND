package com.example.klexifrontend.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
import com.example.klexifrontend.components.KlexiButton
import com.example.klexifrontend.components.PlatformBackground
import com.example.klexifrontend.models.AgeRange
import com.example.klexifrontend.state.AppScreen
import com.example.klexifrontend.state.AppStateManager
import com.example.klexifrontend.theme.KlexiColors

@Composable
fun RegisterAgeScreen(stateManager: AppStateManager) {
    val ageRanges = AgeRange.entries

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
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { stateManager.currentScreen = AppScreen.LOGIN },
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                ) {
                    Text("←", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = KlexiColors.TextDark)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Card Container matching Image 7
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .shadow(10.dp, RoundedCornerShape(32.dp)),
                shape = RoundedCornerShape(32.dp),
                color = KlexiColors.SkyLight
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Selecciona tu rango de edad",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = KlexiColors.TextDark,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Grid of 7 Age Ranges
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4),
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(ageRanges) { range ->
                            val isSelected = stateManager.regAgeRange == range
                            val emojis = mapOf(
                                AgeRange.PRIMERA_INFANCIA to "👧",
                                AgeRange.NINEZ_INTERMEDIA to "👦",
                                AgeRange.PREADOLESCENCIA to "👧",
                                AgeRange.ADOLESCENCIA to "🧑",
                                AgeRange.JUVENTUD to "👨",
                                AgeRange.ADULTEZ_MEDIA to "👩",
                                AgeRange.ADULTEZ_MADURA to "👵"
                            )

                            Surface(
                                modifier = Modifier
                                    .height(130.dp)
                                    .shadow(if (isSelected) 8.dp else 4.dp, RoundedCornerShape(20.dp))
                                    .border(
                                        width = if (isSelected) 3.dp else 1.5.dp,
                                        color = if (isSelected) KlexiColors.PurplePrimary else KlexiColors.CardBorder,
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .clickable { stateManager.regAgeRange = range },
                                shape = RoundedCornerShape(20.dp),
                                color = if (isSelected) KlexiColors.PurpleLight else KlexiColors.CardBackground
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = emojis[range] ?: "🧑",
                                        fontSize = 32.sp
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = range.rangeText,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = KlexiColors.TextDark,
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        text = range.label,
                                        fontSize = 10.sp,
                                        color = KlexiColors.TextMuted,
                                        textAlign = TextAlign.Center,
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Bottom Buttons Bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            onClick = { stateManager.currentScreen = AppScreen.LOGIN },
                            shape = RoundedCornerShape(24.dp),
                            modifier = Modifier.height(48.dp)
                        ) {
                            Text("← Regresar", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }

                        KlexiButton(
                            text = "Continuar ➔",
                            onClick = {
                                if (stateManager.regAgeRange != null) {
                                    stateManager.currentScreen = AppScreen.REGISTER_FORM
                                }
                            },
                            enabled = stateManager.regAgeRange != null,
                            modifier = Modifier.width(160.dp)
                        )
                    }
                }
            }
        }
    }
}
