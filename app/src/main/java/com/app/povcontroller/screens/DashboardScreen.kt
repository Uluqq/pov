package com.app.povcontroller.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.povcontroller.components.DeviceStatusCard
import com.app.povcontroller.components.PovPseudo3DPreview
import com.app.povcontroller.data.MockDataProvider
import com.app.povcontroller.ui.theme.BackgroundDark
import com.app.povcontroller.ui.theme.ErrorRed
import com.app.povcontroller.ui.theme.GlassmorphismBackground
import com.app.povcontroller.ui.theme.GlassmorphismBorder
import com.app.povcontroller.ui.theme.PrimaryNeon
import com.app.povcontroller.ui.theme.SecondaryNeon
import com.app.povcontroller.ui.theme.TextPrimary
import com.app.povcontroller.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(selectedProjectId: Int) {
    var brightness by remember { mutableStateOf(80f) }
    var speedRpm by remember { mutableStateOf(1500f) }
    var isPowerOn by remember { mutableStateOf(true) }

    val scrollState = rememberScrollState()
    val selectedProject = MockDataProvider.projectItems.find { it.id == selectedProjectId }
        ?: MockDataProvider.projectItems.first()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "POV ROTOR",
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp,
                        fontSize = 20.sp
                    )
                },
                actions = {
                    IconButton(onClick = { isPowerOn = !isPowerOn }) {
                        Icon(
                            imageVector = Icons.Default.PowerSettingsNew,
                            contentDescription = "Питание",
                            tint = if (isPowerOn) PrimaryNeon else ErrorRed
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BackgroundDark
                )
            )
        },
        containerColor = BackgroundDark
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .padding(24.dp)
        ) {
            DeviceStatusCard()

            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(GlassmorphismBackground)
                    .border(1.dp, GlassmorphismBorder, RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                PovPseudo3DPreview(
                    icon = selectedProject.icon,
                    uri = selectedProject.uri,
                    tint = selectedProject.tint,
                    rpm = speedRpm,
                    brightness = brightness,
                    isPowerOn = isPowerOn
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Проект: ${selectedProject.name}",
                    color = selectedProject.tint,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "ПАРАМЕТРЫ ДИСПЛЕЯ",
                color = TextSecondary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Lightbulb,
                    contentDescription = "Яркость",
                    tint = PrimaryNeon
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Яркость: ${brightness.toInt()}%",
                        color = TextPrimary,
                        fontSize = 14.sp
                    )
                    Slider(
                        value = brightness,
                        onValueChange = { brightness = it },
                        valueRange = 0f..100f,
                        enabled = isPowerOn,
                        colors = SliderDefaults.colors(
                            thumbColor = PrimaryNeon,
                            activeTrackColor = PrimaryNeon,
                            inactiveTrackColor = GlassmorphismBorder,
                            disabledThumbColor = Color.Gray,
                            disabledActiveTrackColor = Color.Gray
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Speed,
                    contentDescription = "Скорость",
                    tint = SecondaryNeon
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Скорость ротора: ${speedRpm.toInt()} RPM",
                        color = TextPrimary,
                        fontSize = 14.sp
                    )
                    Slider(
                        value = speedRpm,
                        onValueChange = { speedRpm = it },
                        valueRange = 500f..2500f,
                        enabled = isPowerOn,
                        colors = SliderDefaults.colors(
                            thumbColor = SecondaryNeon,
                            activeTrackColor = SecondaryNeon,
                            inactiveTrackColor = GlassmorphismBorder,
                            disabledThumbColor = Color.Gray,
                            disabledActiveTrackColor = Color.Gray
                        )
                    )
                }
            }
        }
    }
}