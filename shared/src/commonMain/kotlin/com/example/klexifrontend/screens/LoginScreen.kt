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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.klexifrontend.components.KlexiButton
import com.example.klexifrontend.components.KlexiChameleon
import com.example.klexifrontend.components.PlatformBackground
import com.example.klexifrontend.models.UserRole
import com.example.klexifrontend.network.KlexiAuthService
import com.example.klexifrontend.state.AppScreen
import com.example.klexifrontend.state.AppStateManager
import com.example.klexifrontend.theme.KlexiColors
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(stateManager: AppStateManager) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    PlatformBackground(showBottomGround = true) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Top Bar with Back Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
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
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Main Login Card
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .shadow(10.dp, RoundedCornerShape(32.dp)),
                shape = RoundedCornerShape(32.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Chameleon Avatar Circle Top
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .clip(CircleShape)
                            .background(KlexiColors.SkyLight)
                            .border(3.dp, KlexiColors.PurplePrimary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        KlexiChameleon(size = 80.dp, equippedAccessory = stateManager.equippedAccessory)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Iniciar Sesión",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = KlexiColors.TextDark
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Username Input Field
                    OutlinedTextField(
                        value = username,
                        onValueChange = {
                            username = it
                            errorMessage = null
                        },
                        label = { Text("Usuario o Correo") },
                        leadingIcon = { Text("👤") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = KlexiColors.PurplePrimary,
                            unfocusedBorderColor = KlexiColors.CardBorder
                        ),
                        singleLine = true,
                        enabled = !isLoading
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Password Input Field
                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            errorMessage = null
                        },
                        label = { Text("Contraseña") },
                        leadingIcon = { Text("🔒") },
                        trailingIcon = {
                            IconButton(onClick = { showPassword = !showPassword }) {
                                Text(if (showPassword) "👁️" else "🙈")
                            }
                        },
                        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = KlexiColors.PurplePrimary,
                            unfocusedBorderColor = KlexiColors.CardBorder
                        ),
                        singleLine = true,
                        enabled = !isLoading
                    )

                    if (errorMessage != null) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = errorMessage!!,
                            color = Color(0xFFD32F2F),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Entrar Button
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = KlexiColors.PurplePrimary,
                            modifier = Modifier.size(44.dp)
                        )
                    } else {
                        KlexiButton(
                            text = "Entrar",
                            onClick = {
                                if (username.isBlank() || password.isBlank()) {
                                    errorMessage = "Ingresa tu usuario y contraseña"
                                    return@KlexiButton
                                }

                                coroutineScope.launch {
                                    isLoading = true
                                    errorMessage = null

                                    val response = KlexiAuthService.login(
                                        usuario = username.trim(),
                                        contrasena = password
                                    )

                                    isLoading = false

                                    if (response.exito) {
                                        val resolvedRole = when (response.rolId) {
                                            1 -> UserRole.ALUMNO
                                            2 -> UserRole.TUTOR
                                            3 -> UserRole.TERAPEUTA
                                            4 -> UserRole.ADMIN
                                            else -> stateManager.selectedRole ?: UserRole.ALUMNO
                                        }
                                        stateManager.selectedRole = resolvedRole

                                        stateManager.currentScreen = when (resolvedRole) {
                                            UserRole.ALUMNO -> AppScreen.STUDENT_HOME
                                            UserRole.TUTOR -> AppScreen.TUTOR_PANEL
                                            UserRole.ADMIN -> AppScreen.ADMIN_PANEL
                                            UserRole.TERAPEUTA -> AppScreen.THERAPIST_PANEL
                                        }
                                    } else {
                                        errorMessage = response.mensaje
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "¿Olvidaste tu contraseña?",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = KlexiColors.PurplePrimary,
                        modifier = Modifier
                            .clickable { }
                            .padding(4.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    HorizontalDivider(color = KlexiColors.CardBorder)

                    Spacer(modifier = Modifier.height(16.dp))

                    // Registrarse Button
                    OutlinedButton(
                        onClick = { stateManager.currentScreen = AppScreen.REGISTER_AGE },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = KlexiColors.PurplePrimary),
                        border = ButtonDefaults.outlinedButtonBorder.copy(width = 2.dp),
                        enabled = !isLoading
                    ) {
                        Text("Registrarse", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}