package com.example.talktone.ui.screens

import android.content.Intent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.talktone.data.AmharicContent
import com.example.talktone.data.QuoteItem
import com.example.talktone.R
import com.example.talktone.ui.theme.EthiopianGold
import com.example.talktone.ui.theme.ImageBackground
import com.example.talktone.viewmodel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuotesScreen(
    viewModel: AppViewModel,
    language: String,
    isDark: Boolean,
    onBack: () -> Unit
) {
    val currentIndex by viewModel.currentQuoteIndex.collectAsState()
    val quotes by viewModel.allQuotes.collectAsState()
    val context = LocalContext.current

    AnimatedContent(
        targetState = currentIndex,
        transitionSpec = {
            slideInHorizontally { it } + fadeIn() togetherWith
                    slideOutHorizontally { -it } + fadeOut()
        },
        label = "quote_transition"
    ) { idx ->
        val q = quotes[idx]
        ImageBackground(resId = R.drawable.dark, isDark = true, overlayAlpha = 0.65f) {
            // Decorative elements
            repeat(3) { i ->
                Box(
                    modifier = Modifier
                        .size((150 + i * 80).dp)
                        .align(if (i % 2 == 0) Alignment.TopStart else Alignment.BottomEnd)
                        .offset(
                            x = if (i % 2 == 0) (-40).dp else 40.dp,
                            y = if (i % 2 == 0) (-40).dp else 40.dp
                        )
                        .graphicsLayer { alpha = 0.08f }
                        .background(Color.White, CircleShape)
                )
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                    Text(
                        text = "✨ ${if (language == "am") "ጥቅሶች" else "Quotes"}",
                        style = MaterialTheme.typography.titleLarge,
                        color = EthiopianGold,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = {
                        val shareText = if (language == "am")
                            "\"${q.textAm}\"\n— ${q.author}\n\nየኢትዮጵያ ሥነ ጽሑፍ App"
                        else
                            "\"${q.textEn}\"\n— ${q.author}\n\nEthiopian Literature App"
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, shareText)
                        }
                        context.startActivity(Intent.createChooser(intent, "Share Quote"))
                    }) {
                        Icon(Icons.Default.Share, contentDescription = "Share", tint = EthiopianGold)
                    }
                }

                // Quote content
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(32.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "❝", fontSize = 60.sp, color = Color.White.copy(alpha = 0.3f))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = if (language == "am") q.textAm else q.textEn,
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        lineHeight = 40.sp
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "— ${q.author}",
                        style = MaterialTheme.typography.titleMedium,
                        color = EthiopianGold,
                        fontStyle = FontStyle.Italic
                    )

                    if (language == "am" && q.textEn.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "\"${q.textEn}\"",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.5f),
                            textAlign = TextAlign.Center,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }

                // Navigation
                Column(
                    modifier = Modifier.padding(bottom = 48.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Dots indicator
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        quotes.forEachIndexed { i, _ ->
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .size(if (i == idx) 12.dp else 8.dp)
                                    .background(
                                        if (i == idx) EthiopianGold else Color.White.copy(alpha = 0.4f),
                                        CircleShape
                                    )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        FilledTonalButton(
                            onClick = { viewModel.prevQuote() },
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = Color.White.copy(alpha = 0.2f)
                            )
                        ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Prev", tint = Color.White)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(if (language == "am") "ቀዳሚ" else "Prev", color = Color.White)
                        }

                        FilledTonalButton(
                            onClick = {
                                val shareText = if (language == "am")
                                    "\"${q.textAm}\"\n— ${q.author}\n\nየኢትዮጵያ ሥነ ጽሑፍ App"
                                else
                                    "\"${q.textEn}\"\n— ${q.author}\n\nEthiopian Literature App"
                                val intent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_TEXT, shareText)
                                }
                                context.startActivity(Intent.createChooser(intent, "Share"))
                            },
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = EthiopianGold.copy(alpha = 0.9f)
                            )
                        ) {
                            Icon(Icons.Default.Share, contentDescription = "Share", tint = Color(0xFF1A0A2E))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(if (language == "am") "አጋራ" else "Share", color = Color(0xFF1A0A2E), style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
                        }

                        FilledTonalButton(
                            onClick = { viewModel.nextQuote() },
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = Color.White.copy(alpha = 0.2f)
                            )
                        ) {
                            Text(if (language == "am") "ቀጣይ" else "Next", color = Color.White)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.Default.ArrowForward, contentDescription = "Next", tint = Color.White)
                        }
                    }
                }
            }
        }
    }
}
