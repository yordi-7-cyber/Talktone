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
import androidx.compose.ui.draw.clip
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
import com.example.talktone.data.UserProfile
import com.example.talktone.ui.theme.*
import com.example.talktone.ui.theme.ImageBackground
import com.example.talktone.R
import com.example.talktone.viewmodel.AppViewModel

data class CategoryCard(
    val category: LiteratureCategory,
    val gradient: List<Color>,
    val route: String
)

@Composable
fun HomeScreen(
    viewModel: AppViewModel,
    isDark: Boolean,
    language: String,
    streak: ReadingStreakEntity?,
    showCongrats: Boolean,
    userProfile: UserProfile?,
    onNavigate: (String) -> Unit
) {
    val categories = listOf(
        CategoryCard(LiteratureCategory.POEM,   listOf(Color(0xFF6A0DAD), Color(0xFF9B59B6)), "poems"),
        CategoryCard(LiteratureCategory.TERET,  listOf(Color(0xFF8B4513), Color(0xFFD2691E)), "terets"),
        CategoryCard(LiteratureCategory.MISALE, listOf(Color(0xFF006400), Color(0xFF228B22)), "misale"),
        CategoryCard(LiteratureCategory.NOVEL,  listOf(Color(0xFF1A237E), Color(0xFF3949AB)), "novels"),
        CategoryCard(LiteratureCategory.QUIZ,   listOf(Color(0xFF8B0000), Color(0xFFDC143C)), "quiz"),
        CategoryCard(LiteratureCategory.BOOKS,  listOf(Color(0xFF00008B), Color(0xFF4169E1)), "book_reader"),
        CategoryCard(LiteratureCategory.QUOTES, listOf(Color(0xFF8B6914), Color(0xFFD4AF37)), "quotes"),
        CategoryCard(LiteratureCategory.PODCAST,listOf(Color(0xFF880E4F), Color(0xFFE91E63)), "podcast"),
        CategoryCard(LiteratureCategory.COMMUNITY, listOf(Color(0xFF004D40), Color(0xFF00897B)), "community_feed"),
    )

    val infiniteTransition = rememberInfiniteTransition(label = "bg")
    val bgAnim by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(8000, easing = LinearEasing), RepeatMode.Reverse),
        label = "bg_anim"
    )

    if (showCongrats) {
        CongratsDialog(streak = streak?.currentStreak ?: 7, language = language,
            onDismiss = { viewModel.dismissCongrats() })
    }

    val bgColors = if (isDark)
        listOf(Color(0xFF0D0221), Color(0xFF1A0A2E), Color(0xFF0D1B2A))
    else
        listOf(Color(0xFF1A0050), Color(0xFF4A0E8F), Color(0xFF7B2FBE))

    ImageBackground(resId = R.drawable.home, isDark = isDark, overlayAlpha = if (isDark) 0.72f else 0.55f) {

        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            // Top bar
            Row(
                modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(horizontal = 20.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    val greeting = if (userProfile?.name?.isNotEmpty() == true)
                        "እንኳን ደህና መጡ, ${userProfile.name}! 👋"
                    else "ሰላም 👋"
                    Text(greeting, style = MaterialTheme.typography.bodyMedium, color = Color.White.copy(0.9f))
                    if (userProfile?.name?.isNotEmpty() == true) {
                        Text("Welcome back, ${userProfile.name}!", color = Color.White.copy(0.55f), fontSize = 12.sp)
                    }
                    Text("ብዕር", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = EthiopianGold)
                }                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextButton(onClick = { viewModel.setLanguage(if (language == "am") "en" else "am") },
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.White)) {
                        Text(if (language == "am") "EN" else "አማ",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
                    }
                    IconButton(onClick = { viewModel.toggleDarkMode() }) {
                        Icon(if (isDark) Icons.Default.LightMode else Icons.Default.DarkMode,
                            null, tint = EthiopianGold)
                    }
                    IconButton(onClick = { onNavigate("settings") }) {
                        Icon(Icons.Default.Settings, null, tint = Color.White)
                    }
                }
            }

            // Streak card
            streak?.let { s ->
                StreakCard(streak = s, language = language, modifier = Modifier.padding(horizontal = 20.dp))
                Spacer(Modifier.height(20.dp))
            }

            // Beginner shortcut
            if (userProfile?.level == "beginner") {
                Card(
                    onClick = { onNavigate("beginner_learn") },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                ) {
                    Box(modifier = Modifier.fillMaxWidth().background(
                        Brush.linearGradient(listOf(Color(0xFF1A237E), Color(0xFF4A148C))),
                        RoundedCornerShape(16.dp)
                    ).padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("📚", fontSize = 32.sp)
                            Spacer(Modifier.width(12.dp))
                            Column {
                                Text(if (language == "am") "ፊደላት ይማሩ" else "Learn Alphabet",
                                    color = EthiopianGold, fontWeight = FontWeight.Bold)
                                Text(if (language == "am") "ሀ ሁ ሂ — ቁጥሮች — ቃላት" else "ሀ ሁ ሂ — Numbers — Words",
                                    color = Color.White.copy(0.7f), fontSize = 13.sp)
                            }
                            Spacer(Modifier.weight(1f))
                            Icon(Icons.Default.ArrowForward, null, tint = EthiopianGold)
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
            }

            // Creator shortcut
            if (userProfile?.role == "creator") {
                Card(
                    onClick = { onNavigate("creator_submit") },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                ) {
                    Box(modifier = Modifier.fillMaxWidth().background(
                        Brush.linearGradient(listOf(Color(0xFF004D40), Color(0xFF1B5E20))),
                        RoundedCornerShape(16.dp)
                    ).padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("✍️", fontSize = 32.sp)
                            Spacer(Modifier.width(12.dp))
                            Column {
                                Text(if (language == "am") "ስራ አስገባ" else "Submit Work",
                                    color = EthiopianGold, fontWeight = FontWeight.Bold)
                                Text(if (language == "am") "ፈጠራ ስራዎን ያጋሩ" else "Share your creative work",
                                    color = Color.White.copy(0.7f), fontSize = 13.sp)
                            }
                            Spacer(Modifier.weight(1f))
                            Icon(Icons.Default.ArrowForward, null, tint = EthiopianGold)
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
            }

            Text(
                text = if (language == "am") "ምድቦች" else "Categories",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxWidth().height(if (categories.size <= 6) 340.dp else 520.dp).padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                userScrollEnabled = false
            ) {
                items(categories) { card ->
                    CategoryCardItem(card = card, language = language, onClick = { onNavigate(card.route) })
                }
            }

            Spacer(Modifier.height(20.dp))
            DailyQuoteTeaser(language = language, onClick = { onNavigate("quotes") })
            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
fun CategoryCardItem(card: CategoryCard, language: String, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(110.dp).shadow(6.dp, RoundedCornerShape(18.dp)),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(modifier = Modifier.fillMaxSize().background(Brush.linearGradient(card.gradient)),
            contentAlignment = Alignment.Center) {
            Box(modifier = Modifier.size(70.dp).align(Alignment.TopEnd).offset(18.dp, (-18).dp)
                .graphicsLayer { alpha = 0.18f }.background(Color.White, CircleShape))
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text(card.category.emoji, fontSize = 28.sp)
                Spacer(Modifier.height(6.dp))
                Text(
                    if (language == "am") card.category.labelAm else card.category.labelEn,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White, fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center, maxLines = 2
                )
            }
        }
    }
}

@Composable
fun StreakCard(streak: ReadingStreakEntity, language: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)) {
        Box(modifier = Modifier.fillMaxWidth()
            .background(Brush.linearGradient(listOf(Color(0xFFD4AF37), Color(0xFF8B6914))))
            .padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text(if (language == "am") "🔥 የማንበቢያ ቀናት" else "🔥 Reading Streak",
                        style = MaterialTheme.typography.titleMedium, color = Color(0xFF1A0A2E), fontWeight = FontWeight.Bold)
                    Text(if (language == "am") "${streak.currentStreak} ቀናት ተከታታይ" else "${streak.currentStreak} days in a row",
                        style = MaterialTheme.typography.bodyMedium, color = Color(0xFF2A1A0E))
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("${streak.currentStreak}", style = MaterialTheme.typography.displayMedium,
                        color = Color(0xFF1A0A2E), fontWeight = FontWeight.Bold)
                    Row {
                        repeat(7) { i ->
                            Box(modifier = Modifier.size(10.dp).padding(1.dp).background(
                                if (i < streak.currentStreak % 8) Color(0xFF1A0A2E) else Color(0xFF1A0A2E).copy(0.3f), CircleShape))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DailyQuoteTeaser(language: String, onClick: () -> Unit) {
    Card(onClick = onClick, modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
        shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = Color.Transparent)) {
        Box(modifier = Modifier.fillMaxWidth()
            .background(Brush.linearGradient(listOf(Color(0xFF1A0A2E), Color(0xFF2D1B69))))
            .padding(20.dp)) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("✨", fontSize = 20.sp)
                    Spacer(Modifier.width(8.dp))
                    Text(if (language == "am") "የዛሬ ጥቅስ" else "Quote of the Day",
                        style = MaterialTheme.typography.titleMedium, color = EthiopianGold, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.height(8.dp))
                Text(if (language == "am") "\"አንብቦ ያደገ ሰው ዓለምን ያሸንፋል\""
                     else "\"One who grows through reading conquers the world\"",
                    style = MaterialTheme.typography.bodyLarge, color = Color.White.copy(0.9f))
                Spacer(Modifier.height(8.dp))
                Text(if (language == "am") "ሁሉንም ጥቅሶች ይመልከቱ →" else "See all quotes →",
                    style = MaterialTheme.typography.labelLarge, color = EthiopianGold.copy(0.8f))
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
            Text("🎉 ብራቮ! 🎉", style = MaterialTheme.typography.headlineMedium,
                color = EthiopianGold, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(if (language == "am") "ዋው! $streak ቀናት ተከታታይ አንብበሃል!\nቀጥል! 💪"
                     else "Wow! $streak days in a row!\nKeep it up! 💪",
                    style = MaterialTheme.typography.bodyLarge, color = Color.White, textAlign = TextAlign.Center)
            }
        },
        confirmButton = {
            Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(containerColor = EthiopianGold)) {
                Text(if (language == "am") "አመሰግናለሁ!" else "Thank you!", color = Color(0xFF1A0A2E))
            }
        }
    )
}
