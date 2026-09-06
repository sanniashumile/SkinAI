package com.skinai.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Soft ambient glow orbs meant to sit behind screen content for visual depth.
 * Degrades gracefully to a subtle gradient (no blur) on pre-API 31 devices.
 */
@Composable
fun AmbientGlowBackground(
    modifier: Modifier = Modifier,
    topColor: Color,
    bottomColor: Color
) {
    Box(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .offset(x = (-60).dp, y = (-80).dp)
                .size(280.dp)
                .blur(90.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(topColor.copy(alpha = 0.35f), Color.Transparent)
                    )
                )
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 60.dp, y = 100.dp)
                .size(260.dp)
                .blur(100.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(bottomColor.copy(alpha = 0.3f), Color.Transparent)
                    )
                )
        )
    }
}
