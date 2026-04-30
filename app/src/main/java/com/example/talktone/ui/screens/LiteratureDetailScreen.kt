package com.example.talktone.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
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
import android.content.Intent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiteratureDetailScreen(
    item: LiteratureItem,
    language: String,
    isDark: Boolean,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val bgColors = if (isDark)
        listOf(Color(0xFF1A0A2E), Color(0xFF0D1B2A))
    else
        listOf(Color(0xFF4A0E8F), Color(0xFF9B59B6))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(bgColors))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
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
                IconButton(onClick = {
                    val shareText = if (language == "am")
                        "${item.titleAm}\n\n${item.contentAm}\n\n— ${item.author}\n\nየኢትዮጵያ ሥነ ጽሑፍ App"
                    else
                        "${item.titleEn}\n\n${item.contentEn}\n\n— ${item.author}\n\nEthiopian Literature App"
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, shareText)
                    }
                    context.startActivity(Intent.createChooser(intent, "Share"))
                }) {
                    Icon(Icons.Default.Share, contentDescription = "Share", tint = EthiopianGold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Content card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.08f)),
                border = BorderStroke(1.dp, EthiopianGold.copy(alpha = 0.4f))
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    // Category emoji
                    Text(
                        text = item.category.emoji,
                        style = MaterialTheme.typography.displayMedium
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // Title
                    Text(
                        text = if (language == "am") item.titleAm else item.titleEn,
                        style = MaterialTheme.typography.headlineLarge,
                        color = EthiopianGold,
                        fontWeight = FontWeight.Bold
                    )

                    if (item.author.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "— ${item.author}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.6f),
                            fontStyle = FontStyle.Italic
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    HorizontalDivider(color = EthiopianGold.copy(alpha = 0.3f))
                    Spacer(modifier = Modifier.height(24.dp))

                    // Main content
                    Text(
                        text = if (language == "am") item.contentAm else item.contentEn,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.92f),
                        lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
                    )

                    // Show both languages if content differs
                    if (language == "am" && item.contentEn.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(24.dp))
                        HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "English",
                            style = MaterialTheme.typography.labelLarge,
                            color = EthiopianGold.copy(alpha = 0.6f)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = item.contentEn,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.5f),
                            fontStyle = FontStyle.Italic
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
