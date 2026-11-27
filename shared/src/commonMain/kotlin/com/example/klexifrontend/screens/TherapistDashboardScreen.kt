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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.klexifrontend.components.*
import com.example.klexifrontend.state.AppScreen
import com.example.klexifrontend.state.AppStateManager
import com.example.klexifrontend.theme.KlexiColors

@Composable
fun TherapistDashboardScreen(stateManager: AppStateManager) {
    var showAddNoteDialog by remember { mutableStateOf(false) }
    var noteText by remember { mutableStateOf("") }
    var noteArea by remember { mutableStateOf("Lectura") }

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
                    text = "Panel Terapeuta",
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
                    Text("👩‍⚕️", fontSize = 24.sp)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Main Therapist Card (Image 9 Right)
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
                    // Patient Selector & Progress
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Paciente Activo:", fontSize = 12.sp, color = KlexiColors.TextMuted)
                            Text(stateManager.currentUser.fullName, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = KlexiColors.TextDark)
                        }
                        StarPointsBadge(level = stateManager.currentUser.level, points = stateManager.currentUser.points)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Progress Donut & Areas Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Avance de usuarios", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = KlexiColors.TextDark)
                            Spacer(modifier = Modifier.height(10.dp))
                            DonutProgressChart(percentage = 78, size = 120.dp)
                        }

                        Column {
                            Text("Áreas a reforzar", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = KlexiColors.TextDark)
                            Spacer(modifier = Modifier.height(10.dp))
                            LegendItem(color = Color(0xFFEAB308), label = "Lectura")
                            LegendItem(color = Color(0xFFEF4444), label = "Escritura")
                            LegendItem(color = Color(0xFFA855F7), label = "Comprensión")
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Add Notes Button
                    KlexiButton(
                        text = "Agregar notas  📝",
                        onClick = { showAddNoteDialog = true },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Notas y Diagnósticos Registrados",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = KlexiColors.TextDark,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    stateManager.therapistNotes.forEach { note ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            shape = RoundedCornerShape(16.dp),
                            color = KlexiColors.CardBackground
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Área: ${note.area}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = KlexiColors.PurplePrimary)
                                    Text(note.date, fontSize = 12.sp, color = KlexiColors.TextMuted)
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(note.text, fontSize = 14.sp, color = KlexiColors.TextDark)
                            }
                        }
                    }
                }
            }
        }

        // Add Notes Dialog
        if (showAddNoteDialog) {
            Dialog(onDismissRequest = { showAddNoteDialog = false }) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .shadow(12.dp, RoundedCornerShape(28.dp)),
                    shape = RoundedCornerShape(28.dp),
                    color = Color.White
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Agregar Nota para el Tutor", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = KlexiColors.TextDark)

                        Spacer(modifier = Modifier.height(14.dp))

                        OutlinedTextField(
                            value = noteText,
                            onValueChange = { noteText = it },
                            label = { Text("Escribe las observaciones y recomendaciones...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            shape = RoundedCornerShape(16.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            OutlinedButton(
                                onClick = { showAddNoteDialog = false },
                                shape = RoundedCornerShape(20.dp)
                            ) {
                                Text("Cancelar")
                            }

                            KlexiButton(
                                text = "Guardar",
                                onClick = {
                                    stateManager.addTherapistNote(noteText, noteArea)
                                    noteText = ""
                                    showAddNoteDialog = false
                                },
                                modifier = Modifier.width(130.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
