package com.example.klexifrontend.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.klexifrontend.theme.KlexiColors

@Composable
fun PlatformBackground(
    modifier: Modifier = Modifier,
    showBottomGround: Boolean = true,
    groundHeight: Dp = 50.dp,
    content: @Composable BoxScope.() -> Unit
) {
    val skyBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF89D4FF),
            Color(0xFFBAE6FD),
            Color(0xFFE0F2FE)
        )
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(skyBrush)
    ) {
        // Floating Clouds in the sky background
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // Cloud 1
            drawCloud(Offset(w * 0.15f, h * 0.12f), scale = 1.0f)
            // Cloud 2
            drawCloud(Offset(w * 0.82f, h * 0.08f), scale = 1.3f)
            // Cloud 3
            drawCloud(Offset(w * 0.55f, h * 0.22f), scale = 0.8f)
        }

        // Main Content Layer
        content()

        // Bottom Platformer Grass & Brick Ground
        if (showBottomGround) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(groundHeight)
                    .align(Alignment.BottomCenter)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height

                    // Ground Bricks Base
                    val brickHeight = h * 0.6f
                    drawRect(
                        color = KlexiColors.BrickBrown,
                        topLeft = Offset(0f, h - brickHeight),
                        size = Size(w, brickHeight)
                    )

                    // Brick Texture Pattern
                    val brickWidth = 40f
                    val brickH = 14f
                    var y = h - brickHeight
                    var row = 0
                    while (y < h) {
                        val offsetShift = if (row % 2 == 0) 0f else brickWidth / 2f
                        var x = -offsetShift
                        while (x < w) {
                            drawRect(
                                color = KlexiColors.BrickDark,
                                topLeft = Offset(x, y),
                                size = Size(brickWidth - 2f, brickH - 2f)
                            )
                            x += brickWidth
                        }
                        y += brickH
                        row++
                    }

                    // Green Grass Layer on top of Bricks
                    val grassHeight = h * 0.45f
                    val grassPath = Path().apply {
                        moveTo(0f, 0f)
                        var currentX = 0f
                        val step = 20f
                        while (currentX <= w + step) {
                            val bump = if ((currentX / step).toInt() % 2 == 0) 6f else -4f
                            lineTo(currentX, grassHeight + bump)
                            currentX += step
                        }
                        lineTo(w, 0f)
                        close()
                    }
                    drawPath(path = grassPath, color = KlexiColors.GrassGreen)

                    // Top Grass Highlight
                    drawRect(
                        color = Color(0xFF86EFAC),
                        topLeft = Offset(0f, 0f),
                        size = Size(w, 5f)
                    )
                }
            }
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawCloud(
    center: Offset,
    scale: Float
) {
    val cloudColor = Color.White.copy(alpha = 0.85f)
    val baseR = 25f * scale

    drawCircle(cloudColor, radius = baseR * 1.2f, center = center)
    drawCircle(cloudColor, radius = baseR * 0.9f, center = Offset(center.x - baseR * 1.1f, center.y + baseR * 0.2f))
    drawCircle(cloudColor, radius = baseR * 0.9f, center = Offset(center.x + baseR * 1.1f, center.y + baseR * 0.2f))
    drawCircle(cloudColor, radius = baseR * 0.7f, center = Offset(center.x - baseR * 1.8f, center.y + baseR * 0.4f))
    drawCircle(cloudColor, radius = baseR * 0.7f, center = Offset(center.x + baseR * 1.8f, center.y + baseR * 0.4f))
}
