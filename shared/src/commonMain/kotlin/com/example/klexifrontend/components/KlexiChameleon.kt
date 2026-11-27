package com.example.klexifrontend.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.klexifrontend.models.AccessoryType

@Composable
fun KlexiChameleon(
    modifier: Modifier = Modifier,
    size: Dp = 180.dp,
    bodyColor: Color = Color(0xFF20C997), // Vibrant Teal
    equippedAccessory: AccessoryType? = null,
    isCheering: Boolean = false
) {
    Box(modifier = modifier.size(size)) {
        Canvas(modifier = Modifier.size(size)) {
            val w = this.size.width
            val h = this.size.height

            val scale = w / 200f // Reference canvas 200x200

            // 1. Tail (Curled tail on the left)
            val tailPath = Path().apply {
                moveTo(50f * scale, 130f * scale)
                cubicTo(
                    20f * scale, 130f * scale,
                    15f * scale, 90f * scale,
                    35f * scale, 85f * scale
                )
                cubicTo(
                    50f * scale, 80f * scale,
                    45f * scale, 110f * scale,
                    30f * scale, 105f * scale
                )
            }
            drawPath(
                path = tailPath,
                color = bodyColor,
                style = Stroke(width = 14f * scale, cap = StrokeCap.Round)
            )

            // 2. Main Body
            val bodyCenter = Offset(95f * scale, 125f * scale)
            drawCircle(
                color = bodyColor,
                radius = 45f * scale,
                center = bodyCenter
            )

            // 3. Back Spines (purple/magenta bumps along the back)
            val spineColor = Color(0xFF9C27B0)
            val spineOffsets = listOf(
                Offset(60f * scale, 90f * scale),
                Offset(75f * scale, 80f * scale),
                Offset(95f * scale, 75f * scale),
                Offset(115f * scale, 78f * scale),
                Offset(130f * scale, 88f * scale)
            )
            spineOffsets.forEach { offset ->
                drawCircle(color = spineColor, radius = 9f * scale, center = offset)
            }

            // 4. Head
            val headCenter = Offset(135f * scale, 105f * scale)
            drawCircle(
                color = bodyColor,
                radius = 38f * scale,
                center = headCenter
            )

            // Head Curl / Antenna
            val antennaPath = Path().apply {
                moveTo(140f * scale, 68f * scale)
                cubicTo(
                    145f * scale, 45f * scale,
                    165f * scale, 40f * scale,
                    160f * scale, 55f * scale
                )
            }
            drawPath(
                path = antennaPath,
                color = bodyColor,
                style = Stroke(width = 8f * scale, cap = StrokeCap.Round)
            )

            // 5. Feet / Paws
            drawCircle(
                color = bodyColor,
                radius = 12f * scale,
                center = Offset(75f * scale, 160f * scale)
            )
            drawCircle(
                color = bodyColor,
                radius = 12f * scale,
                center = Offset(120f * scale, 160f * scale)
            )

            // 6. Belly Accent (Soft light green belly)
            drawCircle(
                color = Color(0xFFC8E6C9),
                radius = 24f * scale,
                center = Offset(105f * scale, 130f * scale)
            )

            // 7. Large Cute Eyes
            val eyeCenter = Offset(148f * scale, 98f * scale)

            // Eye outer white
            drawCircle(
                color = Color.White,
                radius = 20f * scale,
                center = eyeCenter
            )
            // Eye border
            drawCircle(
                color = Color(0xFF1B5E20),
                radius = 20f * scale,
                center = eyeCenter,
                style = Stroke(width = 3f * scale)
            )
            // Pupil
            val pupilOffset = if (isCheering) Offset(150f * scale, 96f * scale) else Offset(150f * scale, 98f * scale)
            drawCircle(
                color = Color(0xFF1A1A1A),
                radius = 12f * scale,
                center = pupilOffset
            )
            // Eye Catchlight (White sparkle)
            drawCircle(
                color = Color.White,
                radius = 4f * scale,
                center = Offset(145f * scale, 93f * scale)
            )
            drawCircle(
                color = Color.White,
                radius = 2f * scale,
                center = Offset(153f * scale, 101f * scale)
            )

            // 8. Cute Smile
            val mouthPath = Path().apply {
                moveTo(145f * scale, 120f * scale)
                cubicTo(
                    152f * scale, 128f * scale,
                    162f * scale, 126f * scale,
                    165f * scale, 118f * scale
                )
            }
            drawPath(
                path = mouthPath,
                color = Color(0xFF1D2A44),
                style = Stroke(width = 4f * scale, cap = StrokeCap.Round)
            )
            // Rosy Cheek
            drawCircle(
                color = Color(0xFFFF8A80).copy(alpha = 0.6f),
                radius = 7f * scale,
                center = Offset(132f * scale, 118f * scale)
            )

            // 9. Accessories
            when (equippedAccessory) {
                AccessoryType.HAT -> {
                    // Wizard Hat (Purple with yellow band and stars)
                    val hatPath = Path().apply {
                        moveTo(115f * scale, 75f * scale)
                        lineTo(145f * scale, 15f * scale)
                        lineTo(175f * scale, 75f * scale)
                        close()
                    }
                    drawPath(path = hatPath, color = Color(0xFF5E35B1))
                    // Brim
                    drawOval(
                        color = Color(0xFF4527A0),
                        topLeft = Offset(105f * scale, 70f * scale),
                        size = Size(80f * scale, 14f * scale)
                    )
                    // Yellow Star on Hat
                    drawCircle(
                        color = Color(0xFFFFD54F),
                        radius = 6f * scale,
                        center = Offset(145f * scale, 45f * scale)
                    )
                }

                AccessoryType.GLASSES -> {
                    // Cool Sunglasses
                    drawCircle(
                        color = Color(0xFF212121),
                        radius = 16f * scale,
                        center = Offset(148f * scale, 98f * scale)
                    )
                    // Frame bridge
                    drawLine(
                        color = Color(0xFF212121),
                        start = Offset(132f * scale, 98f * scale),
                        end = Offset(120f * scale, 98f * scale),
                        strokeWidth = 4f * scale
                    )
                    // Lens Glint
                    drawLine(
                        color = Color.White.copy(alpha = 0.7f),
                        start = Offset(142f * scale, 90f * scale),
                        end = Offset(152f * scale, 102f * scale),
                        strokeWidth = 3f * scale
                    )
                }

                AccessoryType.BOW -> {
                    // Cute Pink Bow
                    val bowCenter = Offset(130f * scale, 65f * scale)
                    val leftBow = Path().apply {
                        moveTo(bowCenter.x, bowCenter.y)
                        lineTo(bowCenter.x - 20f * scale, bowCenter.y - 12f * scale)
                        lineTo(bowCenter.x - 20f * scale, bowCenter.y + 12f * scale)
                        close()
                    }
                    val rightBow = Path().apply {
                        moveTo(bowCenter.x, bowCenter.y)
                        lineTo(bowCenter.x + 20f * scale, bowCenter.y - 12f * scale)
                        lineTo(bowCenter.x + 20f * scale, bowCenter.y + 12f * scale)
                        close()
                    }
                    drawPath(leftBow, color = Color(0xFFEC407A))
                    drawPath(rightBow, color = Color(0xFFEC407A))
                    drawCircle(color = Color(0xFFD81B60), radius = 6f * scale, center = bowCenter)
                }

                AccessoryType.CROWN -> {
                    // Royal Golden Crown
                    val crownPath = Path().apply {
                        moveTo(125f * scale, 70f * scale)
                        lineTo(120f * scale, 42f * scale)
                        lineTo(137f * scale, 56f * scale)
                        lineTo(147f * scale, 38f * scale)
                        lineTo(157f * scale, 56f * scale)
                        lineTo(175f * scale, 42f * scale)
                        lineTo(170f * scale, 70f * scale)
                        close()
                    }
                    drawPath(crownPath, color = Color(0xFFFFD54F))
                    // Crown Base Border
                    drawLine(
                        color = Color(0xFFF57F17),
                        start = Offset(125f * scale, 70f * scale),
                        end = Offset(170f * scale, 70f * scale),
                        strokeWidth = 4f * scale
                    )
                    // Crown Jewels
                    drawCircle(
                        color = Color(0xFFE53935),
                        radius = 3f * scale,
                        center = Offset(147f * scale, 45f * scale)
                    )
                }

                else -> {}
            }
        }
    }
}
