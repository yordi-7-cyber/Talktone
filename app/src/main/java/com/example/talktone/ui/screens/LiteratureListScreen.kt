package com.example.talktone.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.talktone.data.LiteratureCategory
import com.example.talktone.data.LiteratureItem
import com.example.talktone.ui.theme.EthiopianGold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiteratureListScreen(
    items: List<LiteratureItem>,
    category: LiteratureCategory,
    language: String,
    isDark: Boolean,
    onBack: () -> Unit,
    onItemClick: (Int) -> Unit
) {
    val bgColors = if (isDark)
        listOf(Color(0xFF1A0A2E), Color(0xFF16213E))
    else
        listOf(Color(0xFF4A0E8F), Color(0xFF7B2FBE))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(bgColors))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${category.emoji} ${if (language == "am") category.labelAm else category.labelEn}",
                    style = MaterialTheme.typography.headlineMedium,
                    color = EthiopianGold,
                    fontWeight = FontWeight.Bold
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items) { item ->
                    LiteratureItemCard(
                        item = item,
                        language = language,
                        onClick = { onItemClick(item.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun LiteratureItemCard(item: LiteratureItem, language: String, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f)),
        border = BorderStroke(1.dp, EthiopianGold.copy(alpha = 0.3f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = if (language == "am") item.titleAm else item.titleEn,
                style = MaterialTheme.typography.titleLarge,
                color = EthiopianGold,
                fontWeight = FontWeight.Bold
            )
            if (item.author.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "— ${item.author}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.6f)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (language == "am") item.contentAm else item.contentEn,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.85f),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (language == "am") "ተጨማሪ ያንብቡ →" else "Read more →",
                style = MaterialTheme.typography.labelLarge,
                color = EthiopianGold.copy(alpha = 0.8f)
            )
        }
    }
}
