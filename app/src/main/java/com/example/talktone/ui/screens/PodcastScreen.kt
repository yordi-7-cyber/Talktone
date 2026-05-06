package com.example.talktone.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.talktone.ui.theme.EthiopianGold

data class PodcastItem(
    val title: String,
    val channel: String,
    val description: String,
    val url: String,
    val emoji: String
)

private val PODCASTS = listOf(
    PodcastItem("የአማርኛ ሥነ ጽሑፍ ትምህርት", "Ethiopian Literature Channel",
        "ስለ ኢትዮጵያ ሥነ ጽሑፍ ታሪክ እና ዘመናዊ ጸሐፊዎች", "https://www.youtube.com/@EthiopianLiterature", "🎙️"),
    PodcastItem("ፀጋዬ ገብረ መድህን ግጥሞች", "Tsegaye Gebre-Medhin",
        "የታዋቂው ኢትዮጵያዊ ገጣሚ ስራዎች", "https://www.youtube.com/results?search_query=tsegaye+gebre+medhin+poetry", "📜"),
    PodcastItem("አማርኛ ተረቶች", "Amharic Stories",
        "ለልጆች እና ለትልቅ ሰዎች የሚሆኑ ተረቶች", "https://www.youtube.com/results?search_query=amharic+folktales", "🦁"),
    PodcastItem("ኢትዮጵያዊ ምሳሌዎች", "Ethiopian Proverbs",
        "ጥልቅ ትርጉም ያላቸው ምሳሌዎች ትንታኔ", "https://www.youtube.com/results?search_query=ethiopian+proverbs+amharic", "💬"),
    PodcastItem("አዲስ አበባ ሬዲዮ - ሥነ ጽሑፍ", "Addis Ababa Radio",
        "ሥነ ጽሑፍ ፕሮግራሞች", "https://www.youtube.com/results?search_query=addis+ababa+radio+literature", "📻"),
    PodcastItem("Amharic Audiobooks", "Amharic Audio",
        "ታዋቂ ልቦለዶች በድምፅ", "https://www.youtube.com/results?search_query=amharic+audiobook", "🎧"),
    PodcastItem("ሀበሻ ፖድካስት", "Habesha Podcast",
        "ስለ ኢትዮጵያ ባህልና ሥነ ጽሑፍ ውይይቶች", "https://www.youtube.com/results?search_query=habesha+podcast+amharic", "🎤"),
    PodcastItem("Ethiopian Classic Literature", "Classic Ethiopia",
        "ክላሲካዊ ኢትዮጵያዊ ሥነ ጽሑፍ", "https://www.youtube.com/results?search_query=ethiopian+classic+literature", "📚"),
)

@Composable
fun PodcastScreen(isDark: Boolean, language: String, onBack: () -> Unit) {
    val context = LocalContext.current
    val bg = if (isDark)
        Brush.verticalGradient(listOf(Color(0xFF0D0221), Color(0xFF1A0A2E), Color(0xFF0D1B2A)))
    else
        Brush.verticalGradient(listOf(Color(0xFF1A0050), Color(0xFF2D1B69), Color(0xFF4A0E8F)))

    Box(modifier = Modifier.fillMaxSize().background(bg)) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, null, tint = Color.White)
                }
                Text(
                    "🎙️ ${if (language == "am") "ፖድካስት" else "Podcasts"}",
                    color = EthiopianGold, fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = EthiopianGold.copy(0.15f)),
                border = BorderStroke(1.dp, EthiopianGold.copy(0.4f))
            ) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("ℹ️", fontSize = 20.sp)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        if (language == "am") "ለማዳመጥ ይጫኑ — YouTube ይከፍታል"
                        else "Tap to listen — opens YouTube",
                        color = Color.White.copy(0.85f), fontSize = 13.sp
                    )
                }
            }

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(PODCASTS) { podcast ->
                    PodcastCard(podcast = podcast, onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(podcast.url))
                        context.startActivity(intent)
                    })
                }
            }
        }
    }
}

@Composable
private fun PodcastCard(podcast: PodcastItem, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(0.08f)),
        border = BorderStroke(1.dp, EthiopianGold.copy(0.25f))
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(56.dp).background(
                    Brush.linearGradient(listOf(Color(0xFF4A0E8F), Color(0xFF9B59B6))),
                    RoundedCornerShape(12.dp)
                ), contentAlignment = Alignment.Center
            ) { Text(podcast.emoji, fontSize = 26.sp) }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(podcast.title, color = EthiopianGold, fontWeight = FontWeight.Bold,
                    maxLines = 2, overflow = TextOverflow.Ellipsis)
                Text(podcast.channel, color = Color.White.copy(0.5f), fontSize = 12.sp)
                Spacer(Modifier.height(4.dp))
                Text(podcast.description, color = Color.White.copy(0.75f), fontSize = 13.sp,
                    maxLines = 2, overflow = TextOverflow.Ellipsis)
            }
            Icon(Icons.Default.PlayArrow, null, tint = EthiopianGold, modifier = Modifier.size(28.dp))
        }
    }
}
