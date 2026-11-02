package com.app.povcontroller.data

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Hive
import androidx.compose.material.icons.filled.Polymer
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.RocketLaunch

data class PovProjectItem(
    val id: Int,
    val name: String,
    val size: String,
    val icon: ImageVector? = null,
    val tint: Color,
    val uri: Uri? = null
)

object MockDataProvider {
    val projectItems = mutableStateListOf(
        PovProjectItem(1, "Rotor Logo", "1.2 MB", Icons.Default.Hive, Color(0xFF00E5FF)),
        PovProjectItem(2, "Matrix Rain", "4.5 MB", Icons.Default.QrCode2, Color(0xFF00E676)),
        PovProjectItem(3, "3D Polymer", "2.1 MB", Icons.Default.Polymer, Color(0xFFD500F9)),
        PovProjectItem(4, "Launch Anim", "9.8 MB", Icons.Default.RocketLaunch, Color(0xFFFF1744))
    )
}