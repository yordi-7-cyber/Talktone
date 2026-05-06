package com.example.talktone.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.talktone.ui.theme.EthiopianGold
import com.example.talktone.ui.theme.DeepPurple
import com.example.talktone.ui.theme.MidnightBlue
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    var visible by remember { mutableStateOf(false) }
    var quoteVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
        delay(600)
        quoteVisible = true
        delay(3000)
        onFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF2D1B69),
                        DeepPurple,
                        MidnightBlue
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Decorative circles
        repeat(5) { i ->
            val size = (80 + i * 60).dp
            Box(
                modifier = Modifier
                    .size(size)
                    .graphicsLayer { alpha = 0.05f + i * 0.02f }
                    .background(
                        Brush.radialGradient(listOf(EthiopianGold, Color.Transparent)),
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(32.dp)
        ) {
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(800)) + scaleIn(tween(800))
            ) {
                Text(
                    text = "📚",
                    fontSize = 80.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(1000, delayMillis = 300)) + slideInVertically(
                    tween(1000, delayMillis = 300)
                ) { it / 2 }
            ) {
                Text(
                    text = "ብዕር",
                    style = MaterialTheme.typography.displayLarge,
                    color = EthiopianGold,
                    textAlign = TextAlign.Center,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(1000, delayMillis = 500))
            ) {
                Text(
                    text = "Ethiopian Literature",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            AnimatedVisibility(
                visible = quoteVisible,
                enter = fadeIn(tween(1200))
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "\"አንብቦ ያደገ ሰው ዓለምን ያሸንፋል\"",
                        style = MaterialTheme.typography.bodyLarge.copy(fontStyle = FontStyle.Italic),
                        color = Color.White.copy(alpha = 0.85f),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "— ሕዝባዊ",
                        style = MaterialTheme.typography.bodyMedium,
                        color = EthiopianGold.copy(alpha = 0.8f)
                    )
                }
            }
        }

        // Ethiopian flag colors at bottom
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .align(Alignment.BottomCenter)
        ) {
            Box(modifier = Modifier.weight(1f).fillMaxHeight().background(Color(0xFF078930)))
            Box(modifier = Modifier.weight(1f).fillMaxHeight().background(Color(0xFFFFD700)))
            Box(modifier = Modifier.weight(1f).fillMaxHeight().background(Color(0xFFDA121A)))
        }
    }
}
