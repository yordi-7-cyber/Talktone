package com.example.talktone.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.talktone.ui.theme.EthiopianGold

private val ABUGIDA = listOf(
    "ሀ","ሁ","ሂ","ሃ","ሄ","ህ","ሆ",
    "ለ","ሉ","ሊ","ላ","ሌ","ል","ሎ",
    "ሐ","ሑ","ሒ","ሓ","ሔ","ሕ","ሖ",
    "መ","ሙ","ሚ","ማ","ሜ","ም","ሞ",
    "ሠ","ሡ","ሢ","ሣ","ሤ","ሥ","ሦ",
    "ረ","ሩ","ሪ","ራ","ሬ","ር","ሮ",
    "ሰ","ሱ","ሲ","ሳ","ሴ","ስ","ሶ",
    "ሸ","ሹ","ሺ","ሻ","ሼ","ሽ","ሾ",
    "ቀ","ቁ","ቂ","ቃ","ቄ","ቅ","ቆ",
    "በ","ቡ","ቢ","ባ","ቤ","ብ","ቦ",
    "ቨ","ቩ","ቪ","ቫ","ቬ","ቭ","ቮ",
    "ተ","ቱ","ቲ","ታ","ቴ","ት","ቶ",
    "ቸ","ቹ","ቺ","ቻ","ቼ","ች","ቾ",
    "ነ","ኑ","ኒ","ና","ኔ","ን","ኖ",
    "ኘ","ኙ","ኚ","ኛ","ኜ","ኝ","ኞ",
    "አ","ኡ","ኢ","ኣ","ኤ","እ","ኦ",
    "ከ","ኩ","ኪ","ካ","ኬ","ክ","ኮ",
    "ወ","ዉ","ዊ","ዋ","ዌ","ው","ዎ",
    "ዐ","ዑ","ዒ","ዓ","ዔ","ዕ","ዖ",
    "ዘ","ዙ","ዚ","ዛ","ዜ","ዝ","ዞ",
    "ዠ","ዡ","ዢ","ዣ","ዤ","ዥ","ዦ",
    "የ","ዩ","ዪ","ያ","ዬ","ይ","ዮ",
    "ደ","ዱ","ዲ","ዳ","ዴ","ድ","ዶ",
    "ጀ","ጁ","ጂ","ጃ","ጄ","ጅ","ጆ",
    "ገ","ጉ","ጊ","ጋ","ጌ","ግ","ጎ",
    "ጠ","ጡ","ጢ","ጣ","ጤ","ጥ","ጦ",
    "ጨ","ጩ","ጪ","ጫ","ጬ","ጭ","ጮ",
    "ጰ","ጱ","ጲ","ጳ","ጴ","ጵ","ጶ",
    "ጸ","ጹ","ጺ","ጻ","ጼ","ጽ","ጾ",
    "ፀ","ፁ","ፂ","ፃ","ፄ","ፅ","ፆ",
    "ፈ","ፉ","ፊ","ፋ","ፌ","ፍ","ፎ",
    "ፐ","ፑ","ፒ","ፓ","ፔ","ፕ","ፖ"
)

private val GEEZ_NUMBERS = listOf(
    "፩"to"1","፪"to"2","፫"to"3","፬"to"4","፭"to"5",
    "፮"to"6","፯"to"7","፰"to"8","፱"to"9","፲"to"10",
    "፳"to"20","፴"to"30","፵"to"40","፶"to"50",
    "፷"to"60","፸"to"70","፹"to"80","፺"to"90","፻"to"100"
)

private val BASIC_WORDS = listOf(
    "ሰላም" to "Hello","አመሰግናለሁ" to "Thank you","አዎ" to "Yes","አይ" to "No",
    "ቤት" to "House","ውሃ" to "Water","ምግብ" to "Food","ፀሐይ" to "Sun",
    "ጨረቃ" to "Moon","ልጅ" to "Child","እናት" to "Mother","አባት" to "Father"
)

@Composable
fun BeginnerLearnScreen(isDark: Boolean, onBack: () -> Unit) {
    var tab by remember { mutableStateOf(0) }
    val tabs = listOf("ፊደላት", "ቁጥሮች", "ቃላት")

    val bg = if (isDark)
        Brush.verticalGradient(listOf(Color(0xFF0D0221), Color(0xFF1A0A2E)))
    else
        Brush.verticalGradient(listOf(Color(0xFF1A0050), Color(0xFF4A0E8F)))

    Box(modifier = Modifier.fillMaxSize().background(bg)) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, null, tint = Color.White)
                }
                Text("📚 ጀማሪ ትምህርት", color = EthiopianGold, fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineSmall)
            }

            TabRow(selectedTabIndex = tab, containerColor = Color.Transparent, contentColor = EthiopianGold) {
                tabs.forEachIndexed { i, t ->
                    Tab(selected = tab == i, onClick = { tab = i },
                        text = { Text(t, color = if (tab == i) EthiopianGold else Color.White.copy(0.6f),
                            fontWeight = if (tab == i) FontWeight.Bold else FontWeight.Normal) })
                }
            }

            when (tab) {
                0 -> AbugidaGrid()
                1 -> NumbersGrid()
                2 -> WordsList()
            }
        }
    }
}

@Composable
private fun AbugidaGrid() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        contentPadding = PaddingValues(12.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(ABUGIDA) { letter ->
            Card(
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(0.1f)),
                border = BorderStroke(1.dp, EthiopianGold.copy(0.3f))
            ) {
                Box(modifier = Modifier.fillMaxWidth().aspectRatio(1f), contentAlignment = Alignment.Center) {
                    Text(letter, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Composable
private fun NumbersGrid() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(GEEZ_NUMBERS) { (geez, arabic) ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(0.1f)),
                border = BorderStroke(1.dp, EthiopianGold.copy(0.4f))
            ) {
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(geez, color = EthiopianGold, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                    Text(arabic, color = Color.White.copy(0.7f), fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
private fun WordsList() {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)) {
        BASIC_WORDS.forEach { (am, en) ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(0.1f)),
                border = BorderStroke(1.dp, EthiopianGold.copy(0.3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(am, color = EthiopianGold, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Text(en, color = Color.White.copy(0.8f), fontSize = 16.sp)
                }
            }
        }
    }
}
