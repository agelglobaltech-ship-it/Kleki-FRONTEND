package com.example.klexifrontend.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.klexifrontend.components.KlexiButton
import com.example.klexifrontend.components.PlatformBackground
import com.example.klexifrontend.models.UserRole
import com.example.klexifrontend.network.KlexiAuthService
import com.example.klexifrontend.network.RegistroAdultoRequest
import com.example.klexifrontend.network.RegistroMenorRequest
import com.example.klexifrontend.state.AppScreen
import com.example.klexifrontend.state.AppStateManager
import com.example.klexifrontend.theme.KlexiColors
import kotlinx.coroutines.launch

@Composable
fun RegisterFormScreen(stateManager: AppStateManager) {
    var showPassword by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val isMinor = stateManager.regAgeRange?.requiresTutor == true

    PlatformBackground(showBottomGround = true) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { stateManager.currentScreen = AppScreen.REGISTER_AGE },
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    enabled = !isLoading
                ) {
                    Text("←", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = KlexiColors.TextDark)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

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
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Crear Cuenta de Usuario",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = KlexiColors.TextDark,
                        textAlign = TextAlign.Center
                    )

                    stateManager.regAgeRange?.let { range ->
                        Text(
                            text = "Rango seleccionado: ${range.label} (${range.rangeText})",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = KlexiColors.PurplePrimary,
                            modifier = Modifier.padding(vertical = 6.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Error Banner if any
                    stateManager.regError?.let { err ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp),
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFFFEBEE)
                        ) {
                            Text(
                                text = err,
                                color = Color(0xFFD32F2F),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(12.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    // Student Details
                    Text(
                        text = "Datos del Usuario (Alumno)",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = KlexiColors.TextDark,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = stateManager.regFullName,
                        onValueChange = {
                            stateManager.regFullName = it
                            stateManager.regError = null
                        },
                        label = { Text("Nombre Completo del Usuario") },
                        leadingIcon = { Text("👤") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true,
                        enabled = !isLoading
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = stateManager.regEmail,
                        onValueChange = {
                            stateManager.regEmail = it
                            stateManager.regError = null
                        },
                        label = { Text(if (isMinor) "Usuario o Correo del Alumno" else "Correo Electrónico") },
                        leadingIcon = { Text("📧") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true,
                        enabled = !isLoading
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = stateManager.regPassword,
                        onValueChange = {
                            stateManager.regPassword = it
                            stateManager.regError = null
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
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true,
                        enabled = !isLoading
                    )

                    // Password Rules Checklist
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .background(KlexiColors.PurpleLight, RoundedCornerShape(12.dp))
                            .padding(12.dp)
                    ) {
                        Text(
                            text = "La contraseña debe incluir:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = KlexiColors.TextDark
                        )
                        PasswordRuleItem("Mínimo 8 caracteres", stateManager.regPassword.length >= 8)
                        PasswordRuleItem("Al menos 1 letra mayúscula", stateManager.regPassword.any { it.isUpperCase() })
                        PasswordRuleItem("Al menos 1 dígito numérico", stateManager.regPassword.any { it.isDigit() })
                        PasswordRuleItem("1 carácter especial (*#$%@)", stateManager.regPassword.any { it in setOf('*', '#', '$', '%', '@') })
                    }

                    // If Minor, Tutor Registration Section
                    if (isMinor) {
                        Spacer(modifier = Modifier.height(14.dp))

                        HorizontalDivider(color = KlexiColors.CardBorder)

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "Registro de Tutor (Requerido para < 18 años)",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = KlexiColors.PurplePrimary,
                            modifier = Modifier.align(Alignment.Start)
                        )

                        Text(
                            text = "Se creará automáticamente la cuenta de tutor para dar seguimiento al avance de su hijo.",
                            fontSize = 12.sp,
                            color = KlexiColors.TextMuted,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(vertical = 4.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = stateManager.regTutorFullName,
                            onValueChange = {
                                stateManager.regTutorFullName = it
                                stateManager.regError = null
                            },
                            label = { Text("Nombre Completo del Tutor") },
                            leadingIcon = { Text("👨‍👩‍👧") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            singleLine = true,
                            enabled = !isLoading
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = stateManager.regTutorEmail,
                            onValueChange = {
                                stateManager.regTutorEmail = it
                                stateManager.regError = null
                            },
                            label = { Text("Correo Electrónico del Tutor") },
                            leadingIcon = { Text("📧") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            singleLine = true,
                            enabled = !isLoading
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    if (isLoading) {
                        CircularProgressIndicator(
                            color = KlexiColors.PurplePrimary,
                            modifier = Modifier.size(44.dp)
                        )
                    } else {
                        KlexiButton(
                            text = "Registrar Cuenta  🚀",
                            onClick = {
                                val selectedRange = stateManager.regAgeRange
                                if (selectedRange == null) {
                                    stateManager.regError = "Selecciona un rango de edad primero"
                                    return@KlexiButton
                                }
                                if (stateManager.regFullName.isBlank() || stateManager.regEmail.isBlank() || stateManager.regPassword.isBlank()) {
                                    stateManager.regError = "Completa todos los campos obligatorios"
                                    return@KlexiButton
                                }

                                val pass = stateManager.regPassword
                                val passValid = pass.length >= 8 &&
                                        pass.any { it.isUpperCase() } &&
                                        pass.any { it.isDigit() } &&
                                        pass.any { it in setOf('*', '#', '$', '%', '@') }

                                if (!passValid) {
                                    stateManager.regError = "La contraseña no cumple con todas las reglas"
                                    return@KlexiButton
                                }

                                if (isMinor && (stateManager.regTutorFullName.isBlank() || stateManager.regTutorEmail.isBlank())) {
                                    stateManager.regError = "Completa los datos del tutor"
                                    return@KlexiButton
                                }

                                coroutineScope.launch {
                                    isLoading = true
                                    stateManager.regError = null

                                    // Mapeo del rango de edad al rangoId (1..7) en MySQL
                                    val rangoId = selectedRange.ordinal + 1
                                    val userAlumno = stateManager.regEmail.trim().substringBefore("@")

                                    val response = if (isMinor) {
                                        val userTutor = stateManager.regTutorEmail.trim().substringBefore("@")
                                        KlexiAuthService.registrarMenorConTutor(
                                            RegistroMenorRequest(
                                                nombreMenor = stateManager.regFullName.trim(),
                                                userMenor = userAlumno,
                                                rangoId = rangoId,
                                                nombreTutor = stateManager.regTutorFullName.trim(),
                                                userTutor = userTutor,
                                                correoTutor = stateManager.regTutorEmail.trim(),
                                                contrasena = pass
                                            )
                                        )
                                    } else {
                                        KlexiAuthService.registrarAdulto(
                                            RegistroAdultoRequest(
                                                nombreCompleto = stateManager.regFullName.trim(),
                                                nombreUsuario = userAlumno,
                                                correoElectronico = stateManager.regEmail.trim(),
                                                rangoId = rangoId,
                                                contrasena = pass
                                            )
                                        )
                                    }

                                    isLoading = false

                                    if (response.exito) {
                                        stateManager.selectedRole = UserRole.ALUMNO
                                        stateManager.currentScreen = AppScreen.STUDENT_HOME
                                    } else {
                                        stateManager.regError = response.mensaje
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PasswordRuleItem(label: String, isMet: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 2.dp)
    ) {
        Text(
            text = if (isMet) "✓ " else "○ ",
            color = if (isMet) Color(0xFF2E7D32) else Color.Gray,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
        Text(
            text = label,
            color = if (isMet) Color(0xFF2E7D32) else KlexiColors.TextMuted,
            fontSize = 12.sp
        )
    }
}