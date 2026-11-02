package com.app.povcontroller.components

import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.app.povcontroller.ui.theme.BackgroundDark

@Composable
fun PovPseudo3DPreview(
    icon: ImageVector?,
    uri: Uri?,
    tint: Color,
    rpm: Float,
    brightness: Float,
    isPowerOn: Boolean
) {
    var currentAngle by remember { mutableFloatStateOf(0f) }
    var currentRpm by remember { mutableFloatStateOf(0f) }
    var flashState by remember { mutableStateOf(false) }

    val brightnessFactor = brightness / 100f

    LaunchedEffect(isPowerOn, rpm) {
        var lastFrameTime = withFrameNanos { it }
        while (true) {
            val frameTime = withFrameNanos { it }
            val dt = (frameTime - lastFrameTime) / 1_000_000_000f
            lastFrameTime = frameTime

            val targetRpm = if (isPowerOn) rpm else 0f

            currentRpm += (targetRpm - currentRpm) * (dt * 10f)

            if (currentRpm > 1f) {
                val visualRpm = currentRpm * 0.15f
                val degreesPerSec = (visualRpm / 60f) * 360f
                currentAngle = (currentAngle + degreesPerSec * dt) % 360f
            }

            if (isPowerOn && currentRpm > 50f) {
                val visualFlashRpm = currentRpm * 0.2f
                val cycleTimeNanos = (60_000_000_000L / visualFlashRpm.coerceAtLeast(1f) / 2).toLong()
                val flashDurationNanos = (cycleTimeNanos * 0.15f).toLong().coerceIn(1_000_000L, 20_000_000L)

                flashState = (frameTime % cycleTimeNanos) < flashDurationNanos
            } else {
                flashState = false
            }
        }
    }

    val rotationAlpha = (currentRpm / 600f).coerceIn(0.1f, 1f)
    val colorMatrix = remember(brightnessFactor) {
        val b = brightnessFactor * 1.5f
        ColorMatrix(floatArrayOf(
            b, 0f, 0f, 0f, 0f,
            0f, b, 0f, 0f, 0f,
            0f, 0f, b, 0f, 0f,
            0f, 0f, 0f, 1f, 0f
        ))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(BackgroundDark)
                .border(2.dp, Color.White.copy(alpha = 0.05f), CircleShape)
        )

        Canvas(modifier = Modifier.fillMaxSize()) {
            val bladeWidth = size.width * 0.07f
            rotate(degrees = currentAngle) {
                drawLine(
                    color = Color(0xFF1A1A1A),
                    start = Offset(size.width / 2, size.height * 0.12f),
                    end = Offset(size.width / 2, size.height * 0.88f),
                    strokeWidth = bladeWidth,
                    cap = StrokeCap.Round
                )
                drawCircle(color = Color(0xFF222222), radius = bladeWidth * 0.7f, center = center)
            }
        }

        Box(
            modifier = Modifier
                .size(160.dp)
                .clip(CircleShape)
                .graphicsLayer {
                    compositingStrategy = CompositingStrategy.Offscreen
                    val flashAlpha = if (flashState) 1f else 0.2f
                    alpha = rotationAlpha * flashAlpha * brightnessFactor.coerceAtLeast(0.1f)
                    colorFilter = ColorFilter.colorMatrix(colorMatrix)
                }
                .drawWithContent {
                    clipPath(Path().apply { addOval(Rect(Offset.Zero, size)) }) {
                        this@drawWithContent.drawContent()

                        if (isPowerOn) {
                            drawRect(
                                brush = Brush.radialGradient(
                                    colors = listOf(tint.copy(alpha = 0.2f), Color.Transparent),
                                    radius = size.width / 1.1f
                                ),
                                blendMode = BlendMode.Screen
                            )

                            val numLines = 40
                            val gap = size.height / numLines
                            for (i in 0 until numLines) {
                                val y = i * gap
                                drawLine(
                                    color = tint.copy(alpha = if (i % 2 == 0) 0.1f else 0.03f),
                                    start = Offset(0f, y),
                                    end = Offset(size.width, y),
                                    strokeWidth = 1.dp.toPx()
                                )
                            }
                        }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            if (uri != null) {
                AsyncImage(model = uri, contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
            } else if (icon != null) {
                Icon(imageVector = icon, contentDescription = null, tint = tint, modifier = Modifier.size(100.dp))
            }
        }

        if (isPowerOn) {
            Canvas(modifier = Modifier.size(164.dp)) {
                drawCircle(
                    brush = Brush.sweepGradient(
                        0.0f to tint.copy(alpha = 0f),
                        0.5f to tint.copy(alpha = 0.3f * rotationAlpha * brightnessFactor),
                        1.0f to tint.copy(alpha = 0f),
                        center = center
                    ),
                    radius = size.width / 2,
                    center = center,
                    style = Stroke(width = 2.dp.toPx())
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(
                    width = 24.dp,
                    brush = Brush.radialGradient(
                        colors = listOf(Color.Transparent, BackgroundDark.copy(alpha = 0.95f)),
                        radius = 450f
                    ),
                    shape = CircleShape
                )
        )
    }
}