package com.example.talktone.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.talktone.R
import com.example.talktone.data.LiteratureCategory
import com.example.talktone.data.ReadingStreakEntity
import com.example.talktone.data.UserProfile
import com.example.talktone.ui.theme.EthiopianGold
import com.example.talktone.ui.theme.ImageBackground
import com.example.talktone.viewmodel.AppViewModel
import kotlinx.coroutines.launch

data class CategoryCard(val category: LiteratureCategory, val bgRes: Int, val route: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: AppViewModel, isDark: Boolean, language: String,
    streak: ReadingStreakEntity?, showCongrats: Boolean,
    userProfile: UserProfile?, onNavigate: (String) -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val categories = listOf(
        CategoryCard(LiteratureCategory.POEM,    R.drawable.candles,  "poems"),
        CategoryCard(LiteratureCategory.TERET,   R.drawable.warm,     "terets"),
        CategoryCard(LiteratureCategory.MISALE,  R.drawable.proverbs, "misale"),
        CategoryCard(LiteratureCategory.NOVEL,   R.drawable.novel,    "novels"),
        CategoryCard(LiteratureCategory.QUIZ,    R.drawable.dark,     "quiz"),
        CategoryCard(LiteratureCategory.BOOKS,   R.drawable.reading,  "book_reader"),
        CategoryCard(LiteratureCategory.QUOTES,  R.drawable.dark,     "quotes"),
        CategoryCard(LiteratureCategory.PODCAST, R.drawable.warm,     "podcast"),
    )

    if (showCongrats) {
        CongratsDialog(streak?.currentStreak ?: 7, language) { viewModel.dismissCongrats() }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.width(290.dp), drawerContainerColor = Color(0xFF0D0221)) {
                DrawerContent(
                    userProfile = userProfile, language = language, categories = categories,
                    onNavigate = { route -> scope.launch { drawerState.close() }; onNavigate(route) },
                    onSettings = { scope.launch { drawerState.close() }; onNavigate("settings") },
                    onLogout = { scope.launch { drawerState.close() }; viewModel.logout(); onNavigate("onboarding") }
                )
            }
        }
    ) {
        ImageBackground(R.drawable.candles, isDark, if (isDark) 0.7f else 0.55f) {
            Column(Modifier.fillMaxSize()) {
                // Top bar
                Row(
                    Modifier.fillMaxWidth().statusBarsPadding().padding(horizontal = 16.dp, vertical = 12.dp),
                    Arrangement.SpaceBetween, Alignment.CenterVertically
                ) {
                    IconButton({ scope.launch { drawerState.open() } }) {
                        Icon(Icons.Default.Menu, null, tint = EthiopianGold, modifier = Modifier.size(28.dp))
                    }
                    Text("ብዕር", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = EthiopianGold)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        TextButton(
                            { viewModel.setLanguage(if (language == "am") "en" else "am") },
                            colors = ButtonDefaults.textButtonColors(contentColor = Color.White)
                        ) { Text(if (language == "am") "EN" else "አማ", fontWeight = FontWeight.Bold, fontSize = 13.sp) }
                        IconButton({ viewModel.toggleDarkMode() }) {
                            Icon(if (isDark) Icons.Default.LightMode else Icons.Default.DarkMode, null, tint = EthiopianGold)
                        }
                    }
                }

                Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(bottom = 24.dp)) {
                    WelcomeCard(userProfile, language, streak)
                    Spacer(Modifier.height(16.dp))

                    if (userProfile?.level == "beginner") {
                        ShortcutCard("📚", "ፊደላት ይማሩ", "Learn Alphabet", "ሀ ሁ ሂ — ቁጥሮች",
                            language, listOf(Color(0xFF1A237E), Color(0xFF4A148C))) { onNavigate("beginner_learn") }
                        Spacer(Modifier.height(10.dp))
                    }
                    if (userProfile?.role == "creator") {
                        ShortcutCard("✍️", "ስራ አስገባ", "Submit Work", "ፈጠራ ስራዎን ያጋሩ",
                            language, listOf(Color(0xFF004D40), Color(0xFF1B5E20))) { onNavigate("creator_submit") }
                        Spacer(Modifier.height(10.dp))
                    }

                    // Quick actions
                    Row(
                        Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        QuickActionCard("🧩", "የቃላት ጦርነት", "Word Puzzle", language, Modifier.weight(1f)) { onNavigate("word_puzzle") }
                        QuickActionCard("🎯", "ፈተና", "Quiz", language, Modifier.weight(1f)) { onNavigate("quiz") }
                    }
                    
                    Spacer(Modifier.height(16.dp))
                    
                    Row(
                        Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        QuickActionCard("📖", "መጽሐፎች", "Books", language, Modifier.weight(1f)) { onNavigate("book_reader") }
                        QuickActionCard("✨", "ጥቅሶች", "Quotes", language, Modifier.weight(1f)) { onNavigate("quotes") }
                    }

                    Spacer(Modifier.height(20.dp))
                    DailyQuoteTeaser(language) { onNavigate("quotes") }
                    Spacer(Modifier.height(24.dp))
                    
                    // Open menu hint
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = EthiopianGold.copy(0.15f)),
                        border = BorderStroke(1.dp, EthiopianGold.copy(0.3f))
                    ) {
                        Row(
                            Modifier.fillMaxWidth().clickable { scope.launch { drawerState.open() } }.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(Icons.Default.Menu, null, tint = EthiopianGold)
                            Spacer(Modifier.width(8.dp))
                            Text(
                                if (language == "am") "☰ ምድቦችን ለማየት ይክፈቱ" else "☰ Open menu for all categories",
                                color = EthiopianGold, fontWeight = FontWeight.Medium
                            )
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}

// ── Quick Action Card ────────────────────────────────────────────────────────

@Composable
fun QuickActionCard(emoji: String, titleAm: String, titleEn: String, language: String, modifier: Modifier, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = modifier.height(90.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(0.4f)),
        border = BorderStroke(1.dp, EthiopianGold.copy(0.3f))
    ) {
        Column(
            Modifier.fillMaxSize().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(emoji, fontSize = 28.sp)
            Spacer(Modifier.height(4.dp))
            Text(
                if (language == "am") titleAm else titleEn,
                color = Color.White, fontWeight = FontWeight.Medium, fontSize = 13.sp
            )
        }
    }
}

// ── Drawer ────────────────────────────────────────────────────────────────────

@Composable
private fun DrawerContent(
    userProfile: UserProfile?, language: String, categories: List<CategoryCard>,
    onNavigate: (String) -> Unit, onSettings: () -> Unit, onLogout: () -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        // Profile header
        Box(
            Modifier.fillMaxWidth()
                .background(Brush.verticalGradient(listOf(Color(0xFF1A0A2E), Color(0xFF0D0221))))
                .padding(20.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Box(
                    Modifier.size(72.dp).clip(CircleShape)
                        .background(EthiopianGold.copy(0.2f))
                        .border(2.dp, EthiopianGold, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    if (userProfile?.profilePicUri?.isNotEmpty() == true) {
                        AsyncImage(model = userProfile.profilePicUri, contentDescription = null,
                            modifier = Modifier.fillMaxSize().clip(CircleShape), contentScale = ContentScale.Crop)
                    } else {
                        Text(userProfile?.name?.firstOrNull()?.uppercase() ?: "👤",
                            fontSize = 28.sp, color = EthiopianGold)
                    }
                }
                Spacer(Modifier.height(10.dp))
                Text(userProfile?.name ?: "Guest", color = EthiopianGold, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                if (userProfile?.phone?.isNotEmpty() == true)
                    Text(userProfile.phone, color = Color.White.copy(0.5f), fontSize = 12.sp)
                Spacer(Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Surface(shape = RoundedCornerShape(20.dp), color = EthiopianGold.copy(0.2f)) {
                        Text(
                            when(userProfile?.level) { "beginner"->"ጀማሪ"; "intermediate"->"መካከለኛ"; else->"ከፍተኛ" },
                            color = EthiopianGold, fontSize = 11.sp, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                    Surface(shape = RoundedCornerShape(20.dp), color = Color.White.copy(0.1f)) {
                        Text(
                            if (userProfile?.role == "creator") "ፈጣሪ" else "አንባቢ",
                            color = Color.White, fontSize = 11.sp, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }

        // Categories list with image backgrounds
        LazyColumn(modifier = Modifier.weight(1f).padding(vertical = 8.dp)) {
            item {
                Text(
                    if (language == "am") "📚 ምድቦች" else "📚 Categories",
                    color = EthiopianGold, fontWeight = FontWeight.Bold, fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            items(categories) { card ->
                DrawerCategoryItem(card, language, onNavigate)
            }
            item {
                HorizontalDivider(color = Color.White.copy(0.1f), modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
            }
            item {
                Text(
                    if (language == "am") "🎮 ጨዋታዎች" else "🎮 Games",
                    color = EthiopianGold, fontWeight = FontWeight.Bold, fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            item {
                NavigationDrawerItem(
                    icon = { Text("🧩", fontSize = 20.sp) },
                    label = { Text(if (language == "am") "የቃላት ጦርነት" else "Word Puzzle", color = Color.White, fontWeight = FontWeight.Medium) },
                    selected = false, onClick = { onNavigate("word_puzzle") },
                    colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.Transparent),
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
            item {
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.EmojiEvents, null, tint = EthiopianGold) },
                    label = { Text(if (language == "am") "የማህበረሰብ ስራዎች" else "Community", color = Color.White) },
                    selected = false, onClick = { onNavigate("community_feed") },
                    colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.Transparent),
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
        }

        // Bottom actions
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(
                onClick = onSettings, modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.dp, Color.White.copy(0.3f))
            ) {
                Icon(Icons.Default.Settings, null, tint = Color.White, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text(if (language == "am") "ቅንብሮች" else "Settings", color = Color.White)
            }
            Button(
                onClick = onLogout, modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red.copy(0.7f))
            ) {
                Icon(Icons.Default.Logout, null, tint = Color.White, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text(if (language == "am") "ውጣ" else "Logout", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// ── Drawer Category Item with Image ───────────────────────────────────────────

@Composable
private fun DrawerCategoryItem(card: CategoryCard, language: String, onNavigate: (String) -> Unit) {
    Card(
        onClick = { onNavigate(card.route) },
        modifier = Modifier.fillMaxWidth().height(56.dp).padding(horizontal = 12.dp, vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(Modifier.fillMaxSize()) {
            AsyncImage(model = card.bgRes, contentDescription = null,
                modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop, alpha = 0.3f)
            Box(Modifier.fillMaxSize().background(Color.Black.copy(0.5f)))
            Row(
                Modifier.fillMaxSize().padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(card.category.emoji, fontSize = 22.sp)
                Spacer(Modifier.width(12.dp))
                Text(
                    if (language == "am") card.category.labelAm else card.category.labelEn,
                    color = Color.White, fontWeight = FontWeight.Medium, fontSize = 15.sp
                )
            }
        }
    }
}

// ── Shared composables ────────────────────────────────────────────────────────

@Composable
fun WelcomeCard(userProfile: UserProfile?, language: String, streak: ReadingStreakEntity?) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(0.4f)),
        border = BorderStroke(1.dp, EthiopianGold.copy(0.4f))
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(
                    if (userProfile?.name?.isNotEmpty() == true)
                        "እንኳን ደህና መጡ, ${userProfile.name}! 👋"
                    else "ሰላም! 👋",
                    color = EthiopianGold, fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                if (userProfile?.name?.isNotEmpty() == true)
                    Text("Welcome, ${userProfile.name}!", color = Color.White.copy(0.6f), fontSize = 12.sp)
            }
            streak?.let { s ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🔥", fontSize = 24.sp)
                    Text("${s.currentStreak}", color = EthiopianGold, fontWeight = FontWeight.Bold, fontSize = 22.sp)
                    Text(if (language == "am") "ቀናት" else "days", color = Color.White.copy(0.6f), fontSize = 11.sp)
                }
            }
        }
    }
}

@Composable
fun ShortcutCard(
    emoji: String, titleAm: String, titleEn: String, subtitle: String,
    language: String, gradient: List<Color>, onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(Modifier.fillMaxWidth().background(Brush.linearGradient(gradient), RoundedCornerShape(16.dp)).padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(emoji, fontSize = 32.sp)
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(if (language == "am") titleAm else titleEn, color = EthiopianGold, fontWeight = FontWeight.Bold)
                    Text(subtitle, color = Color.White.copy(0.7f), fontSize = 12.sp)
                }
                Spacer(Modifier.weight(1f))
                Icon(Icons.Default.ArrowForward, null, tint = EthiopianGold)
            }
        }
    }
}

@Composable
fun DailyQuoteTeaser(language: String, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(0.4f)),
        border = BorderStroke(1.dp, EthiopianGold.copy(0.3f))
    ) {
        Column(Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("✨", fontSize = 20.sp); Spacer(Modifier.width(8.dp))
                Text(if (language == "am") "የዛሬ ጥቅስ" else "Quote of the Day",
                    style = MaterialTheme.typography.titleMedium, color = EthiopianGold, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(8.dp))
            Text(
                if (language == "am") "\"አንብቦ ያደገ ሰው ዓለምን ያሸንፋል\""
                else "\"One who grows through reading conquers the world\"",
                style = MaterialTheme.typography.bodyLarge, color = Color.White.copy(0.9f)
            )
            Spacer(Modifier.height(8.dp))
            Text(if (language == "am") "ሁሉንም ጥቅሶች ይመልከቱ →" else "See all quotes →",
                style = MaterialTheme.typography.labelLarge, color = EthiopianGold.copy(0.8f))
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
            Text(
                if (language == "am") "ዋው! $streak ቀናት ተከታታይ አንብበሃል!\nቀጥል! 💪"
                else "Wow! $streak days in a row!\nKeep it up! 💪",
                style = MaterialTheme.typography.bodyLarge, color = Color.White, textAlign = TextAlign.Center
            )
        },
        confirmButton = {
            Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(containerColor = EthiopianGold)) {
                Text(if (language == "am") "አመሰግናለሁ!" else "Thank you!", color = Color(0xFF1A0A2E))
            }
        }
    )
}

@Composable
fun StreakCard(streak: ReadingStreakEntity, language: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)) {
        Box(Modifier.fillMaxWidth()
            .background(Brush.linearGradient(listOf(Color(0xFFD4AF37), Color(0xFF8B6914))))
            .padding(16.dp)) {
            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                Column {
                    Text(if (language == "am") "🔥 የማንበቢያ ቀናት" else "🔥 Reading Streak",
                        style = MaterialTheme.typography.titleMedium, color = Color(0xFF1A0A2E), fontWeight = FontWeight.Bold)
                    Text(if (language == "am") "${streak.currentStreak} ቀናት ተከታታይ" else "${streak.currentStreak} days in a row",
                        style = MaterialTheme.typography.bodyMedium, color = Color(0xFF2A1A0E))
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("${streak.currentStreak}", style = MaterialTheme.typography.displayMedium,
                        color = Color(0xFF1A0A2E), fontWeight = FontWeight.Bold)
                    Row { repeat(7) { i ->
                        Box(Modifier.size(10.dp).padding(1.dp).background(
                            if (i < streak.currentStreak % 8) Color(0xFF1A0A2E) else Color(0xFF1A0A2E).copy(0.3f), CircleShape))
                    }}
                }
            }
        }
    }
}
