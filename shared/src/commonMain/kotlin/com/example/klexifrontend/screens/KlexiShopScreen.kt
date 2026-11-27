package com.example.klexifrontend.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.klexifrontend.components.KlexiButton
import com.example.klexifrontend.components.KlexiChameleon
import com.example.klexifrontend.components.PlatformBackground
import com.example.klexifrontend.models.AccessoryType
import com.example.klexifrontend.models.KlexiShopItem
import com.example.klexifrontend.state.AppScreen
import com.example.klexifrontend.state.AppStateManager
import com.example.klexifrontend.theme.KlexiColors

@Composable
fun KlexiShopScreen(stateManager: AppStateManager) {
    var selectedShopItem by remember { mutableStateOf(stateManager.shopItems.first()) }

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
                IconButton(
                    onClick = { stateManager.currentScreen = AppScreen.STUDENT_HOME },
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                ) {
                    Text("←", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = KlexiColors.TextDark)
                }

                Text(
                    text = "Tienda de Klexi",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = KlexiColors.TextDark
                )

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White,
                    shadowElevation = 2.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("⭐", fontSize = 18.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${stateManager.currentUser.points}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = KlexiColors.PurplePrimary
                        )
                    }
                }
            }

            Text(
                text = "¡Desbloquea nuevos estilos para Klexi!",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = KlexiColors.PurplePrimary
            )

            // Center Stage (Klexi Avatar Preview)
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(240.dp)
                    .shadow(8.dp, RoundedCornerShape(32.dp)),
                shape = RoundedCornerShape(32.dp),
                color = KlexiColors.CardBackground
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    KlexiChameleon(
                        size = 200.dp,
                        equippedAccessory = selectedShopItem.type,
                        isCheering = true
                    )
                }
            }

            // Items Row (Image 3 Middle)
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                items(stateManager.shopItems) { item ->
                    val isSelected = selectedShopItem.id == item.id
                    val itemEmoji = when (item.type) {
                        AccessoryType.HAT -> "🧙‍♂️"
                        AccessoryType.GLASSES -> "🕶️"
                        AccessoryType.BOW -> "🎀"
                        AccessoryType.CROWN -> "👑"
                        AccessoryType.COLOR -> "🎨"
                    }

                    Surface(
                        modifier = Modifier
                            .size(100.dp)
                            .shadow(if (isSelected) 8.dp else 4.dp, RoundedCornerShape(24.dp))
                            .border(
                                width = if (isSelected) 3.dp else 1.5.dp,
                                color = if (isSelected) KlexiColors.PurplePrimary else KlexiColors.CardBorder,
                                shape = RoundedCornerShape(24.dp)
                            )
                            .clickable { selectedShopItem = item },
                        shape = RoundedCornerShape(24.dp),
                        color = if (isSelected) KlexiColors.PurpleLight else Color.White
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(itemEmoji, fontSize = 38.sp)
                                if (!item.isUnlocked) {
                                    Text("🔒", fontSize = 14.sp)
                                }
                            }
                        }
                    }
                }
            }

            // Action Button
            KlexiButton(
                text = when {
                    selectedShopItem.isEquipped -> "Equipado ✓"
                    selectedShopItem.isUnlocked -> "Equipar"
                    else -> "Comprar ⭐ ${selectedShopItem.price}"
                },
                onClick = { stateManager.buyOrEquipItem(selectedShopItem) },
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .padding(bottom = 60.dp),
                containerColor = if (selectedShopItem.isEquipped) Color(0xFF22C55E) else KlexiColors.PurplePrimary
            )
        }

        // Reward Popup Dialog (Image 8)
        if (stateManager.showRewardPopup) {
            Dialog(onDismissRequest = { stateManager.showRewardPopup = false }) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .shadow(12.dp, RoundedCornerShape(32.dp)),
                    shape = RoundedCornerShape(32.dp),
                    color = Color.White
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("⭐", fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "¡Felicidades!",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = KlexiColors.TextDark
                        )
                        Text(
                            "Desbloqueaste un nuevo accesorio para Klexi",
                            fontSize = 16.sp,
                            color = KlexiColors.TextMuted,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        KlexiChameleon(
                            size = 150.dp,
                            equippedAccessory = stateManager.justUnlockedItem?.type,
                            isCheering = true
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        KlexiButton(
                            text = "Continuar",
                            onClick = { stateManager.showRewardPopup = false },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}
