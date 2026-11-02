package com.app.povcontroller.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BluetoothSearching
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.povcontroller.ui.theme.BackgroundDark
import com.app.povcontroller.ui.theme.PrimaryNeon
import kotlinx.coroutines.delay

@Composable
fun ScanScreen(onDeviceFound: () -> Unit) {
    var isScanning by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(3000)
        isScanning = false
        delay(500)
        onDeviceFound()
    }

    val infiniteTransition = rememberInfiniteTransition(label = "RadarPulse")

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ScalePulse"
    )

    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "AlphaPulse"
    )

    val fadeAlpha by animateFloatAsState(
        targetValue = if (isScanning) 1f else 0f,
        animationSpec = tween(500),
        label = "FadeOut"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(300.dp)
        ) {
            // Круги радара
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .scale(scale)
                    .alpha(fadeAlpha) // Применяем плавное исчезновение здесь
                    .clip(CircleShape)
                    .border(1.dp, PrimaryNeon.copy(alpha = pulseAlpha), CircleShape)
                    .background(Color.Transparent)
            )

            // Центральная иконка
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(PrimaryNeon.copy(alpha = 0.1f))
                    .border(2.dp, PrimaryNeon, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.BluetoothSearching,
                    contentDescription = "Scanning",
                    tint = PrimaryNeon,
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = if (isScanning) "SEARCHING FOR DISPLAY..." else "DEVICE FOUND!",
            color = PrimaryNeon,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )
    }
}