package com.example.talktone.ui.screens

import android.content.Intent
import android.speech.tts.TextToSpeech
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.talktone.data.LiteratureItem
import com.example.talktone.ui.theme.EthiopianGold

@Composable
fun LiteratureDetailScreen(
    item: LiteratureItem,
    language: String,
    isDark: Boolean,
    tts: TextToSpeech? = null,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    var isSpeaking by remember { mutableStateOf(false) }

    val bgColors = if (isDark)
        listOf(Color(0xFF0D0221), Color(0xFF1A0A2E), Color(0xFF0D1B2A))
    else
        listOf(Color(0xFFFAF0FF), Color(0xFFEDE0FF), Color(0xFFD8C4FF))

    val textColor = if (isDark) Color(0xFFEEE8D5) else Color(0xFF1A0A2E)
    val cardBg = if (isDark) Color.White.copy(0.07f) else Color.White.copy(0.85f)

    DisposableEffect(Unit) {
        onDispose { tts?.stop() }
    }

    Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(bgColors))) {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            Row(
                modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(horizontal = 8.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, null, tint = if (isDark) Color.White else Color(0xFF1A0A2E))
                }
                Row {
                    // TTS button
                    if (tts != null) {
                        IconButton(onClick = {
                            if (isSpeaking) {
                                tts.stop()
                                isSpeaking = false
                            } else {
                                val text = if (language == "am") item.contentAm else item.contentEn
                                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts_id")
                                isSpeaking = true
                            }
                        }) {
                            Icon(
                                if (isSpeaking) Icons.Default.StopCircle else Icons.Default.VolumeUp,
                                null,
                                tint = if (isSpeaking) Color.Red else EthiopianGold
                            )
                        }
                    }
                    IconButton(onClick = {
                        val shareText = if (language == "am")
                            "${item.titleAm}\n\n${item.contentAm}\n\n— ${item.author}\n\nብዕር App"
                        else
                            "${item.titleEn}\n\n${item.contentEn}\n\n— ${item.author}\n\nBiir App"
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, shareText)
                        }
                        context.startActivity(Intent.createChooser(intent, "Share"))
                    }) {
                        Icon(Icons.Default.Share, null, tint = EthiopianGold)
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = cardBg),
                border = BorderStroke(1.dp, EthiopianGold.copy(0.4f))
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(item.category.emoji, style = MaterialTheme.typography.displayMedium)
                    Spacer(Modifier.height(12.dp))
                    Text(
                        if (language == "am") item.titleAm else item.titleEn,
                        style = MaterialTheme.typography.headlineLarge,
                        color = EthiopianGold, fontWeight = FontWeight.Bold
                    )
                    if (item.author.isNotEmpty()) {
                        Spacer(Modifier.height(6.dp))
                        Text("— ${item.author}", style = MaterialTheme.typography.bodyMedium,
                            color = textColor.copy(0.6f), fontStyle = FontStyle.Italic)
                    }
                    Spacer(Modifier.height(24.dp))
                    HorizontalDivider(color = EthiopianGold.copy(0.3f))
                    Spacer(Modifier.height(24.dp))
                    Text(
                        if (language == "am") item.contentAm else item.contentEn,
                        style = MaterialTheme.typography.bodyLarge,
                        color = textColor,
                        lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
                    )
                    // Show both when language == "both" or when "am" and English exists
                    if ((language == "both" || language == "am") && item.contentEn.isNotEmpty()) {
                        Spacer(Modifier.height(24.dp))
                        HorizontalDivider(color = textColor.copy(0.1f))
                        Spacer(Modifier.height(16.dp))
                        Text("English", style = MaterialTheme.typography.labelLarge,
                            color = EthiopianGold.copy(0.6f))
                        Spacer(Modifier.height(8.dp))
                        Text(item.contentEn, style = MaterialTheme.typography.bodyMedium,
                            color = textColor.copy(0.5f), fontStyle = FontStyle.Italic)
                    }
                }
            }
            Spacer(Modifier.height(32.dp))
        }
    }
}
