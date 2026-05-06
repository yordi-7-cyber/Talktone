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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.talktone.R
import com.example.talktone.ui.theme.EthiopianGold
import com.example.talktone.ui.theme.ImageBackground

private val ABUGIDA = listOf(
    "ሀ","ሁ","ሂ","ሃ","ሄ","ህ","ሆ","ለ","ሉ","ሊ","ላ","ሌ","ል","ሎ",
    "ሐ","ሑ","ሒ","ሓ","ሔ","ሕ","ሖ","መ","ሙ","ሚ","ማ","ሜ","ም","ሞ",
    "ሠ","ሡ","ሢ","ሣ","ሤ","ሥ","ሦ","ረ","ሩ","ሪ","ራ","ሬ","ር","ሮ",
    "ሰ","ሱ","ሲ","ሳ","ሴ","ስ","ሶ","ሸ","ሹ","ሺ","ሻ","ሼ","ሽ","ሾ",
    "ቀ","ቁ","ቂ","ቃ","ቄ","ቅ","ቆ","በ","ቡ","ቢ","ባ","ቤ","ብ","ቦ",
    "ተ","ቱ","ቲ","ታ","ቴ","ት","ቶ","ቸ","ቹ","ቺ","ቻ","ቼ","ች","ቾ",
    "ነ","ኑ","ኒ","ና","ኔ","ን","ኖ","አ","ኡ","ኢ","ኣ","ኤ","እ","ኦ",
    "ከ","ኩ","ኪ","ካ","ኬ","ክ","ኮ","ወ","ዉ","ዊ","ዋ","ዌ","ው","ዎ",
    "ዘ","ዙ","ዚ","ዛ","ዜ","ዝ","ዞ","የ","ዩ","ዪ","ያ","ዬ","ይ","ዮ",
    "ደ","ዱ","ዲ","ዳ","ዴ","ድ","ዶ","ጀ","ጁ","ጂ","ጃ","ጄ","ጅ","ጆ",
    "ገ","ጉ","ጊ","ጋ","ጌ","ግ","ጎ","ጠ","ጡ","ጢ","ጣ","ጤ","ጥ","ጦ",
    "ፈ","ፉ","ፊ","ፋ","ፌ","ፍ","ፎ","ፐ","ፑ","ፒ","ፓ","ፔ","ፕ","ፖ"
)

private val NUMBERS = listOf("፩"to"1","፪"to"2","፫"to"3","፬"to"4","፭"to"5",
    "፮"to"6","፯"to"7","፰"to"8","፱"to"9","፲"to"10",
    "፳"to"20","፴"to"30","፵"to"40","፶"to"50","፷"to"60",
    "፸"to"70","፹"to"80","፺"to"90","፻"to"100")

private val WORDS = listOf("ሰላም"to"Hello","አመሰግናለሁ"to"Thank you","አዎ"to"Yes","አይ"to"No",
    "ቤት"to"House","ውሃ"to"Water","ምግብ"to"Food","ፀሐይ"to"Sun",
    "ጨረቃ"to"Moon","ልጅ"to"Child","እናት"to"Mother","አባት"to"Father",
    "ወንድም"to"Brother","እህት"to"Sister","ጓደኛ"to"Friend","ሀገር"to"Country")

@Composable
fun BeginnerLearnScreen(isDark: Boolean, onBack: () -> Unit) {
    var tab by remember { mutableStateOf(0) }

    ImageBackground(resId = R.drawable.reading, isDark = isDark, overlayAlpha = if (isDark) 0.78f else 0.62f) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null, tint = Color.White) }
                Text("📚 ጀማሪ ትምህርት", color = EthiopianGold, fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineSmall)
            }

            TabRow(selectedTabIndex = tab, containerColor = Color.Black.copy(0.3f), contentColor = EthiopianGold) {
                listOf("ፊደላት", "ቁጥሮች", "ቃላት").forEachIndexed { i, t ->
                    Tab(selected = tab == i, onClick = { tab = i },
                        text = { Text(t, color = if (tab == i) EthiopianGold else Color.White.copy(0.6f),
                            fontWeight = if (tab == i) FontWeight.Bold else FontWeight.Normal) })
                }
            }

            when (tab) {
                0 -> LazyVerticalGrid(columns = GridCells.Fixed(7),
                    contentPadding = PaddingValues(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier.fillMaxSize()) {
                    items(ABUGIDA) { letter ->
                        Card(shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.Black.copy(0.35f)),
                            border = BorderStroke(1.dp, EthiopianGold.copy(0.3f))) {
                            Box(modifier = Modifier.fillMaxWidth().aspectRatio(1f), contentAlignment = Alignment.Center) {
                                Text(letter, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                            }
                        }
                    }
                }
                1 -> LazyVerticalGrid(columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(14.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxSize()) {
                    items(NUMBERS) { (geez, arabic) ->
                        Card(shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.Black.copy(0.35f)),
                            border = BorderStroke(1.dp, EthiopianGold.copy(0.4f))) {
                            Column(modifier = Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(geez, color = EthiopianGold, fontSize = 30.sp, fontWeight = FontWeight.Bold)
                                Text(arabic, color = Color.White.copy(0.7f), fontSize = 13.sp)
                            }
                        }
                    }
                }
                2 -> Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    WORDS.forEach { (am, en) ->
                        Card(shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.Black.copy(0.35f)),
                            border = BorderStroke(1.dp, EthiopianGold.copy(0.3f)),
                            modifier = Modifier.fillMaxWidth()) {
                            Row(modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(am, color = EthiopianGold, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                                Text(en, color = Color.White.copy(0.8f), fontSize = 16.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
