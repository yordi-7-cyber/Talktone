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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.talktone.R
import com.example.talktone.ui.theme.EthiopianGold
import com.example.talktone.ui.theme.ImageBackground
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    var visible by remember { mutableStateOf(false) }
    var quoteVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
        delay(600)
        quoteVisible = true
        delay(2800)
        onFinished()
    }

    ImageBackground(resId = R.drawable.reading, isDark = true, overlayAlpha = 0.65f) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize().padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AnimatedVisibility(visible = visible, enter = fadeIn(tween(800)) + scaleIn(tween(800))) {
                    Text("✍️", fontSize = 80.sp)
                }
                Spacer(Modifier.height(20.dp))
                AnimatedVisibility(visible = visible,
                    enter = fadeIn(tween(1000, 300)) + slideInVertically(tween(1000, 300)) { it / 2 }) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("ብዕር", fontSize = 64.sp, fontWeight = FontWeight.Bold, color = EthiopianGold)
                        Text("Ethiopian Literature", style = MaterialTheme.typography.titleLarge,
                            color = Color.White.copy(0.8f))
                    }
                }
                Spacer(Modifier.height(56.dp))
                AnimatedVisibility(visible = quoteVisible, enter = fadeIn(tween(1200))) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("\"አንብቦ ያደገ ሰው ዓለምን ያሸንፋል\"",
                            style = MaterialTheme.typography.bodyLarge.copy(fontStyle = FontStyle.Italic),
                            color = Color.White.copy(0.9f), textAlign = TextAlign.Center)
                        Spacer(Modifier.height(6.dp))
                        Text("\"One who grows through reading conquers the world\"",
                            style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
                            color = Color.White.copy(0.6f), textAlign = TextAlign.Center)
                        Spacer(Modifier.height(8.dp))
                        Text("— ሕዝባዊ", color = EthiopianGold.copy(0.85f),
                            style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
            // Ethiopian flag strip
            Row(modifier = Modifier.fillMaxWidth().height(5.dp).align(Alignment.BottomCenter)) {
                Box(modifier = Modifier.weight(1f).fillMaxHeight().background(Color(0xFF078930)))
                Box(modifier = Modifier.weight(1f).fillMaxHeight().background(Color(0xFFFFD700)))
                Box(modifier = Modifier.weight(1f).fillMaxHeight().background(Color(0xFFDA121A)))
            }
        }
    }
}
