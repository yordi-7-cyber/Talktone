package com.example.talktone.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.talktone.data.LiteratureCategory
import com.example.talktone.data.ReadingStreakEntity
import com.example.talktone.ui.theme.*
import com.example.talktone.viewmodel.AppViewModel

data class CategoryCard(
    val category: LiteratureCategory,
    val gradient: List<Color>,
    val route: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: AppViewModel,
    isDark: Boolean,
    language: String,
    streak: ReadingStreakEntity?,
    showCongrats: Boolean,
    onNavigate: (String) -> Unit
) {
    val categories = listOf(
        CategoryCard(LiteratureCategory.POEM, listOf(Color(0xFF6A0DAD), Color(0xFF9B59B6)), "poems"),
        CategoryCard(LiteratureCategory.TERET, listOf(Color(0xFF8B4513), Color(0xFFD2691E)), "terets"),
        CategoryCard(LiteratureCategory.MISALE, listOf(Color(0xFF006400), Color(0xFF228B22)), "misale"),
        CategoryCard(LiteratureCategory.QUIZ, listOf(Color(0xFF8B0000), Color(0xFFDC143C)), "quiz"),
        CategoryCard(LiteratureCategory.BOOKS, listOf(Color(0xFF00008B), Color(0xFF4169E1)), "book_reader"),
        CategoryCard(LiteratureCategory.QUOTES, listOf(Color(0xFF8B6914), Color(0xFFD4AF37)), "quotes"),
    )

    val infiniteTransition = rememberInfiniteTransition(label = "home_bg")
    val bgAnim by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(6000, easing = LinearEasing), RepeatMode.Reverse),
        label = "bg_anim"
    )

    if (showCongrats) {
        CongratsDialog(
            streak = streak?.currentStreak ?: 7,
            language = language,
            onDismiss = { viewModel.dismissCongrats() }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    if (isDark) listOf(Color(0xFF1A0A2E), Color(0xFF0D1B2A), Color(0xFF16213E))
                    else listOf(Color(0xFF4A0E8F), Color(0xFF7B2FBE), Color(0xFF9B59B6))
                )
            )
    ) {
        // Animated background orbs
        Box(
            modifier = Modifier
                .size(300.dp)
                .offset(x = (-50).dp, y = (-50 + bgAnim * 30).dp)
                .graphicsLayer { alpha = 0.15f }
                .background(
                    Brush.radialGradient(listOf(EthiopianGold, Color.Transparent)),
                    CircleShape
                )
        )
        Box(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 50.dp, y = (50 - bgAnim * 20).dp)
                .graphicsLayer { alpha = 0.1f }
                .background(
                    Brush.radialGradient(listOf(EthiopianGreen, Color.Transparent)),
                    CircleShape
                )
        )

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
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = if (language == "am") "ሰላም 👋" else "Hello 👋",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Text(
                        text = if (language == "am") "የኢትዮጵያ ሥነ ጽሑፍ" else "Ethiopian Literature",
                        style = MaterialTheme.typography.headlineMedium,
                        color = EthiopianGold,
                        fontWeight = FontWeight.Bold
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Language toggle
                    TextButton(
                        onClick = { viewModel.setLanguage(if (language == "am") "en" else "am") },
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.White)
                    ) {
                        Text(if (language == "am") "EN" else "አማ", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
                    }
                    // Dark mode toggle
                    IconButton(onClick = { viewModel.toggleDarkMode() }) {
                        Icon(
                            if (isDark) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "Toggle theme",
                            tint = EthiopianGold
                        )
                    }
                    // Settings
                    IconButton(onClick = { onNavigate("settings") }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color.White)
                    }
                }
            }

            // Streak card
            streak?.let { s ->
                StreakCard(streak = s, language = language, modifier = Modifier.padding(horizontal = 20.dp))
                Spacer(modifier = Modifier.height(20.dp))
            }

            // Section title
            Text(
                text = if (language == "am") "ምድቦች" else "Categories",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
            )

            // Categories grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                userScrollEnabled = false
            ) {
                items(categories) { card ->
                    CategoryCardItem(
                        card = card,
                        language = language,
                        onClick = { onNavigate(card.route) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Daily quote teaser
            DailyQuoteTeaser(language = language, onClick = { onNavigate("quotes") })

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun CategoryCardItem(card: CategoryCard, language: String, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .shadow(8.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.linearGradient(card.gradient)),
            contentAlignment = Alignment.Center
        ) {
            // Decorative circle
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = 20.dp, y = (-20).dp)
                    .graphicsLayer { alpha = 0.2f }
                    .background(Color.White, CircleShape)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = card.category.emoji, fontSize = 36.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (language == "am") card.category.labelAm else card.category.labelEn,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun StreakCard(streak: ReadingStreakEntity, language: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(listOf(Color(0xFFD4AF37), Color(0xFF8B6914)))
                )
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = if (language == "am") "🔥 የማንበቢያ ቀናት" else "🔥 Reading Streak",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF1A0A2E),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (language == "am") "${streak.currentStreak} ቀናት ተከታታይ"
                        else "${streak.currentStreak} days in a row",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF2A1A0E)
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${streak.currentStreak}",
                        style = MaterialTheme.typography.displayMedium,
                        color = Color(0xFF1A0A2E),
                        fontWeight = FontWeight.Bold
                    )
                    // Streak dots
                    Row {
                        repeat(7) { i ->
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .padding(1.dp)
                                    .background(
                                        if (i < streak.currentStreak % 8) Color(0xFF1A0A2E)
                                        else Color(0xFF1A0A2E).copy(alpha = 0.3f),
                                        CircleShape
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DailyQuoteTeaser(language: String, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(listOf(Color(0xFF1A0A2E), Color(0xFF2D1B69)))
                )
                .padding(20.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("✨", fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (language == "am") "የዛሬ ጥቅስ" else "Quote of the Day",
                        style = MaterialTheme.typography.titleMedium,
                        color = EthiopianGold,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (language == "am") "\"አንብቦ ያደገ ሰው ዓለምን ያሸንፋል\""
                    else "\"One who grows through reading conquers the world\"",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.9f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (language == "am") "ሁሉንም ጥቅሶች ይመልከቱ →" else "See all quotes →",
                    style = MaterialTheme.typography.labelLarge,
                    color = EthiopianGold.copy(alpha = 0.8f)
                )
            }
        }
    }
}

@Composable
fun CongratsDialog(streak: Int, language: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF1A0A2E),
        title = {
            Text(
                text = "🎉 ብራቮ! 🎉",
                style = MaterialTheme.typography.headlineMedium,
                color = EthiopianGold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = if (language == "am")
                        "ዋው! $streak ቀናት ተከታታይ አንብበሃል!\nማንበብ ሕይወትን ይቀይራል!\nቀጥል! 💪"
                    else
                        "Wow! $streak days in a row!\nReading changes life!\nKeep it up! 💪",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = EthiopianGold)
            ) {
                Text(
                    if (language == "am") "አመሰግናለሁ!" else "Thank you!",
                    color = Color(0xFF1A0A2E)
                )
            }
        }
    )
}


