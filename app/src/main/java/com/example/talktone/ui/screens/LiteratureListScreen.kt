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
import com.example.talktone.data.*
import com.example.talktone.R
import com.example.talktone.ui.theme.EthiopianGold
import com.example.talktone.ui.theme.ImageBackground
import com.example.talktone.viewmodel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiteratureListScreen(
    items: List<LiteratureItem>,
    category: LiteratureCategory,
    language: String,
    isDark: Boolean,
    onBack: () -> Unit,
    onItemClick: (Int) -> Unit,
    viewModel: AppViewModel? = null
) {
    val bgRes = when (category) {
        LiteratureCategory.POEM -> R.drawable.candles
        LiteratureCategory.TERET -> R.drawable.warm
        LiteratureCategory.MISALE -> R.drawable.proverbs
        LiteratureCategory.NOVEL -> R.drawable.novel
        else -> R.drawable.reading
    }

    // Get approved creator submissions for this category
    val approvedSubmissions by viewModel?.approvedSubmissions?.collectAsState(initial = emptyList()) 
        ?: remember { mutableStateOf(emptyList()) }
    
    val categorySubmissions = approvedSubmissions.filter { 
        it.category == category.name.lowercase() && it.status == "approved" 
    }

    // Get admin content based on category
    val adminPoems by viewModel?.adminPoems?.collectAsState(initial = emptyList()) 
        ?: remember { mutableStateOf(emptyList()) }
    val adminTerets by viewModel?.adminTerets?.collectAsState(initial = emptyList()) 
        ?: remember { mutableStateOf(emptyList()) }
    val adminMisale by viewModel?.adminMisale?.collectAsState(initial = emptyList()) 
        ?: remember { mutableStateOf(emptyList()) }

    // Combine all content for this category
    val allItems = remember(items, categorySubmissions, adminPoems, adminTerets, adminMisale) {
        val baseItems = items.toMutableList()
        
        // Add admin content
        when (category) {
            LiteratureCategory.POEM -> {
                baseItems.addAll(adminPoems.map { p ->
                    LiteratureItem(
                        id = 1000 + p.id,
                        titleAm = p.titleAm,
                        titleEn = p.titleEn,
                        contentAm = p.contentAm,
                        contentEn = p.contentEn,
                        author = p.author,
                        category = LiteratureCategory.POEM
                    )
                })
            }
            LiteratureCategory.TERET -> {
                baseItems.addAll(adminTerets.map { t ->
                    LiteratureItem(
                        id = 2000 + t.id,
                        titleAm = t.titleAm,
                        titleEn = t.titleEn,
                        contentAm = t.contentAm,
                        contentEn = t.contentEn,
                        author = t.author,
                        category = LiteratureCategory.TERET
                    )
                })
            }
            LiteratureCategory.MISALE -> {
                baseItems.addAll(adminMisale.map { m ->
                    LiteratureItem(
                        id = 3000 + m.id,
                        titleAm = m.titleAm,
                        titleEn = m.titleEn,
                        contentAm = m.contentAm,
                        contentEn = m.contentEn,
                        author = m.author,
                        category = LiteratureCategory.MISALE
                    )
                })
            }
            else -> {}
        }
        
        // Add approved creator submissions
        baseItems.addAll(categorySubmissions.map { s ->
            LiteratureItem(
                id = 5000 + s.id,
                titleAm = s.titleAm,
                titleEn = s.titleEn,
                contentAm = s.contentAm,
                contentEn = s.contentEn,
                author = s.authorName,
                category = category
            )
        })
        
        baseItems
    }

    ImageBackground(resId = bgRes, isDark = isDark, overlayAlpha = if (isDark) 0.75f else 0.6f) {
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

            if (allItems.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("📭", fontSize = 48.sp)
                        Spacer(Modifier.height(12.dp))
                        Text(
                            if (language == "am") "በዚህ ምድብ ውስጥ ይዘት የለም" else "No content in this category yet",
                            color = Color.White.copy(0.6f), fontSize = 16.sp
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(allItems) { item ->
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
}

@Composable
fun LiteratureItemCard(item: LiteratureItem, language: String, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.45f)),
        border = BorderStroke(1.dp, EthiopianGold.copy(alpha = 0.4f))
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = if (language == "am") item.titleAm else if (language == "both") item.titleAm else item.titleEn,
                style = MaterialTheme.typography.titleLarge,
                color = EthiopianGold,
                fontWeight = FontWeight.Bold
            )
            if (language == "both" && item.titleEn.isNotEmpty()) {
                Text(text = item.titleEn, style = MaterialTheme.typography.titleSmall,
                    color = EthiopianGold.copy(0.7f))
            }
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
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = if (language == "am") "ተጨማሪ ያንብቡ →" else "Read more →",
                style = MaterialTheme.typography.labelLarge,
                color = EthiopianGold.copy(alpha = 0.8f)
            )
        }
    }
}
