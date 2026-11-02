package com.app.povcontroller.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.povcontroller.components.AnimatedNeonButton
import com.app.povcontroller.components.DeviceStatusCard
import com.app.povcontroller.ui.theme.BackgroundDark
import com.app.povcontroller.ui.theme.ErrorRed
import com.app.povcontroller.ui.theme.GlassmorphismBackground
import com.app.povcontroller.ui.theme.GlassmorphismBorder
import com.app.povcontroller.ui.theme.PrimaryNeon
import com.app.povcontroller.ui.theme.TextPrimary
import com.app.povcontroller.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    var serverIp by remember { mutableStateOf("192.168.4.1") }
    var autoConnect by remember { mutableStateOf(true) }
    var hardwareSync by remember { mutableStateOf(true) }
    var ledCompensation by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "СИСТЕМА",
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp,
                        fontSize = 20.sp
                    )
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

            Text(
                text = "СЕТЕВЫЕ НАСТРОЙКИ",
                color = TextSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = serverIp,
                onValueChange = { serverIp = it },
                label = { Text("IP-адрес сервера управления") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryNeon,
                    unfocusedBorderColor = GlassmorphismBorder,
                    focusedLabelColor = PrimaryNeon,
                    unfocusedLabelColor = TextSecondary,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "НАСТРОЙКИ УСТРОЙСТВА",
                color = TextSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(GlassmorphismBackground)
                    .border(1.dp, GlassmorphismBorder, RoundedCornerShape(20.dp))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SettingsSwitchItem(
                    title = "Автоподключение",
                    subtitle = "Подключаться при запуске приложения",
                    checked = autoConnect,
                    onCheckedChange = { autoConnect = it }
                )

                SettingsSwitchItem(
                    title = "Аппаратная синхронизация",
                    subtitle = "Синхронизация таймингов с датчиком Холла",
                    checked = hardwareSync,
                    onCheckedChange = { hardwareSync = it }
                )

                SettingsSwitchItem(
                    title = "Компенсация LED",
                    subtitle = "Коррекция цветовых отклонений",
                    checked = ledCompensation,
                    onCheckedChange = { ledCompensation = it }
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            AnimatedNeonButton(
                text = "ОТКЛЮЧИТЬ УСТРОЙСТВО",
                onClick = {
                    Toast.makeText(context, "Демо: Отключение...", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.fillMaxWidth(),
                color = ErrorRed
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Версия приложения: 1.0.0 (Демо)",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun SettingsSwitchItem(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Text(text = subtitle, color = TextSecondary, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = BackgroundDark,
                checkedTrackColor = PrimaryNeon,
                uncheckedThumbColor = TextSecondary,
                uncheckedTrackColor = BackgroundDark,
                uncheckedBorderColor = GlassmorphismBorder
            )
        )
    }
}