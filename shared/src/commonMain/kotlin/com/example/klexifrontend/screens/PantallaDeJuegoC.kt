package com.example.klexifrontend.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.klexifrontend.components.KlexiButton
import com.example.klexifrontend.components.KlexiChameleon
import com.example.klexifrontend.components.PlatformBackground
import com.example.klexifrontend.state.AppStateManager
import com.example.klexifrontend.theme.KlexiColors

// Una pregunta: la oración se parte en "antes" y "después" de la palabra faltante
private data class SentenceQuestion(
    val before: String,
    val after: String,
    val options: List<String>,
    val correctIndex: Int
)

private val CorrectGreen = Color(0xFF4CAF50)
private val WrongRed = Color(0xFFE53935)

@Composable
fun SentenceGameScreen(
    stateManager: AppStateManager,
    onBack: () -> Unit = {} // aquí pon la navegación a la pantalla anterior
) {
    val questions = remember {
        listOf(
            SentenceQuestion("El perro ", " en el jardín.", listOf("corre", "mesa", "azul"), 0),
            SentenceQuestion("Me lavo las ", " antes de comer.", listOf("nubes", "manos", "sillas"), 1),
            SentenceQuestion("La luna sale por la ", ".", listOf("sopa", "playa", "noche"), 2),
            SentenceQuestion("Mi mamá cocina una rica ", ".", listOf("sopa", "ventana", "zapato"), 0),
            SentenceQuestion("Los pájaros ", " en el cielo.", listOf("beben", "vuelan", "pintan"), 1)
        )
    }

    var currentIndex by remember { mutableStateOf(0) }
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    var score by remember { mutableStateOf(0) }
    var finished by remember { mutableStateOf(false) }

    PlatformBackground(showBottomGround = true) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Barra superior: volver + marcador
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "←",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = KlexiColors.PurplePrimary,
                    modifier = Modifier.clickable { onBack() }
                )
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White.copy(alpha = 0.9f))
                        .padding(horizontal = 14.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("⭐", fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "$score",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = KlexiColors.TextDark
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (!finished) {
                val question = questions[currentIndex]
                val answered = selectedIndex != null
                val isCorrect = answered && selectedIndex == question.correctIndex

                // Progreso
                Text(
                    text = "Pregunta ${currentIndex + 1} de ${questions.size}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = KlexiColors.TextMuted
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.7f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth((currentIndex + 1) / questions.size.toFloat())
                            .fillMaxHeight()
                            .clip(CircleShape)
                            .background(KlexiColors.PurplePrimary)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                KlexiChameleon(
                    size = 120.dp,
                    equippedAccessory = stateManager.equippedAccessory,
                    isCheering = isCorrect
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Tarjeta con la oración
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White.copy(alpha = 0.95f),
                    shadowElevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Completa la oración",
                            fontSize = 16.sp,
                            color = KlexiColors.TextMuted
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = buildAnnotatedString {
                                append(question.before)
                                val word = selectedIndex?.let { question.options[it] } ?: "_____"
                                withStyle(
                                    SpanStyle(
                                        color = when {
                                            !answered -> KlexiColors.PurplePrimary
                                            isCorrect -> CorrectGreen
                                            else -> WrongRed
                                        },
                                        fontWeight = FontWeight.ExtraBold,
                                        textDecoration = TextDecoration.Underline
                                    )
                                ) { append(word) }
                                append(question.after)
                            },
                            fontSize = 26.sp,
                            lineHeight = 38.sp,
                            fontWeight = FontWeight.Bold,
                            color = KlexiColors.TextDark,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Opciones
                question.options.forEachIndexed { index, option ->
                    val bgColor = when {
                        !answered -> Color.White
                        index == question.correctIndex -> CorrectGreen
                        index == selectedIndex -> WrongRed
                        else -> Color.White.copy(alpha = 0.6f)
                    }
                    val textColor = when {
                        answered && (index == question.correctIndex || index == selectedIndex) -> Color.White
                        else -> KlexiColors.TextDark
                    }

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .clickable(enabled = !answered) {
                                selectedIndex = index
                                if (index == question.correctIndex) score++
                            },
                        shape = RoundedCornerShape(20.dp),
                        color = bgColor,
                        shadowElevation = 4.dp
                    ) {
                        Text(
                            text = option,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp)
                        )
                    }
                }

                // Mensaje y botón siguiente
                if (answered) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = if (isCorrect) "¡Muy bien! 🎉"
                        else "¡Casi! La respuesta era: ${question.options[question.correctIndex]}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isCorrect) CorrectGreen else WrongRed,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    KlexiButton(
                        text = if (currentIndex == questions.lastIndex) "Ver resultado" else "Siguiente  ➔",
                        onClick = {
                            if (currentIndex == questions.lastIndex) {
                                finished = true
                            } else {
                                currentIndex++
                                selectedIndex = null
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            } else {
                // Pantalla final
                Spacer(modifier = Modifier.height(20.dp))

                KlexiChameleon(
                    size = 180.dp,
                    equippedAccessory = stateManager.equippedAccessory,
                    isCheering = true
                )

                Spacer(modifier = Modifier.height(20.dp))

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White.copy(alpha = 0.95f),
                    shadowElevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "¡Terminaste!",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = KlexiColors.TextDark
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Acertaste $score de ${questions.size}",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = KlexiColors.PurplePrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "⭐".repeat(score.coerceAtLeast(1)),
                            fontSize = 28.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        KlexiButton(
                            text = "Jugar de nuevo",
                            onClick = {
                                currentIndex = 0
                                selectedIndex = null
                                score = 0
                                finished = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        KlexiButton(
                            text = "Volver",
                            onClick = onBack,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}
// Cómo revertir la navegación al terminar tus pruebas:
//Cuando termines de probar la pantalla y quieras volver al flujo normal:
//1.
//Abre   WelcomeScreen.kt.
//2.
//Busca la línea del botón "Comenzar" y cambia:
//Kotlin
//// ❌ Cambiar esto:
//onClick = { stateManager.currentScreen = AppScreen.SENTENCE_GAME }
//
//// ✅ Regresar a esto:
//onClick = { stateManager.currentScreen = AppScreen.ROLE_SELECTION }
//3.
//¡El juego SentenceGameScreen seguirá registrado y listo para ser llamado desde cualquier otra pantalla como la del estudiante!
