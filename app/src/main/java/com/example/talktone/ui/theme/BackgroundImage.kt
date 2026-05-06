package com.example.talktone.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.example.talktone.R
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Brush

object BgRes {
    val home     = R.drawable.home
    val splash   = R.drawable.reading
    val candles  = R.drawable.candles
    val cozy     = R.drawable.cozy
    val dark     = R.drawable.dark
    val warm     = R.drawable.warm
    val novel    = R.drawable.novel
    val proverbs = R.drawable.proverbs
    val logo     = R.drawable.logo
    val icon     = R.drawable.icon
}

@Composable
fun ImageBackground(
    resId: Int,
    isDark: Boolean = true,
    overlayAlpha: Float = 0.62f,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        AsyncImage(
            model = resId,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        // Overlay for text readability
        Box(
            modifier = Modifier.fillMaxSize().background(
                if (isDark)
                    Brush.verticalGradient(listOf(
                        Color(0xFF0D0221).copy(alpha = overlayAlpha),
                        Color(0xFF1A0A2E).copy(alpha = overlayAlpha + 0.05f),
                        Color(0xFF0D1B2A).copy(alpha = overlayAlpha + 0.1f)
                    ))
                else
                    Brush.verticalGradient(listOf(
                        Color.White.copy(alpha = 0.45f),
                        Color(0xFFFFF8F0).copy(alpha = 0.55f),
                        Color(0xFFF5E6D3).copy(alpha = 0.65f)
                    ))
            )
        )
        content()
    }
}
