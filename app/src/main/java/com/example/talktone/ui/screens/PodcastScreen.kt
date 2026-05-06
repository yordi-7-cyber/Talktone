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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.talktone.R
import com.example.talktone.ui.theme.EthiopianGold
import com.example.talktone.ui.theme.ImageBackground

private data class PodcastEntry(val title: String, val channel: String, val desc: String, val url: String, val emoji: String)

private val PODCASTS = listOf(
    PodcastEntry("የአማርኛ ሥነ ጽሑፍ ትምህርት","Ethiopian Literature Channel","ስለ ኢትዮጵያ ሥነ ጽሑፍ ታሪክ","https://www.youtube.com/results?search_query=ethiopian+amharic+literature","🎙️"),
    PodcastEntry("ፀጋዬ ገብረ መድህን ግጥሞች","Tsegaye Gebre-Medhin","የታዋቂው ኢትዮጵያዊ ገጣሚ ስራዎች","https://www.youtube.com/results?search_query=tsegaye+gebre+medhin+poetry","📜"),
    PodcastEntry("አማርኛ ተረቶች","Amharic Stories","ለልጆች እና ለትልቅ ሰዎች ተረቶች","https://www.youtube.com/results?search_query=amharic+folktales","🦁"),
    PodcastEntry("ኢትዮጵያዊ ምሳሌዎች","Ethiopian Proverbs","ጥልቅ ትርጉም ያላቸው ምሳሌዎች","https://www.youtube.com/results?search_query=ethiopian+proverbs+amharic","💬"),
    PodcastEntry("Amharic Audiobooks","Amharic Audio","ታዋቂ ልቦለዶች በድምፅ","https://www.youtube.com/results?search_query=amharic+audiobook","🎧"),
    PodcastEntry("ሀበሻ ፖድካስት","Habesha Podcast","ስለ ኢትዮጵያ ባህልና ሥነ ጽሑፍ","https://www.youtube.com/results?search_query=habesha+podcast+amharic","🎤"),
    PodcastEntry("Ethiopian Classic Literature","Classic Ethiopia","ክላሲካዊ ኢትዮጵያዊ ሥነ ጽሑፍ","https://www.youtube.com/results?search_query=ethiopian+classic+literature","📚"),
)

@Composable
fun PodcastScreen(isDark: Boolean, language: String, onBack: () -> Unit) {
    val context = LocalContext.current

    ImageBackground(resId = R.drawable.warm, isDark = isDark, overlayAlpha = if (isDark) 0.75f else 0.6f) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null, tint = Color.White) }
                Text("🎙️ ${if (language == "am") "ፖድካስት" else "Podcasts"}",
                    color = EthiopianGold, fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineMedium)
            }
            Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Black.copy(0.3f)),
                border = BorderStroke(1.dp, EthiopianGold.copy(0.3f))) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("ℹ️", fontSize = 18.sp); Spacer(Modifier.width(8.dp))
                    Text(if (language == "am") "ለማዳመጥ ይጫኑ — YouTube ይከፍታል" else "Tap to listen — opens YouTube",
                        color = Color.White.copy(0.85f), fontSize = 13.sp)
                }
            }
            LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(PODCASTS) { p ->
                    Card(onClick = { context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(p.url))) },
                        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(0.35f)),
                        border = BorderStroke(1.dp, EthiopianGold.copy(0.25f))) {
                        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text(p.emoji, fontSize = 28.sp)
                            Spacer(Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(p.title, color = EthiopianGold, fontWeight = FontWeight.Bold,
                                    maxLines = 2, overflow = TextOverflow.Ellipsis)
                                Text(p.channel, color = Color.White.copy(0.5f), fontSize = 11.sp)
                                Text(p.desc, color = Color.White.copy(0.75f), fontSize = 12.sp, maxLines = 2)
                            }
                            Icon(Icons.Default.PlayArrow, null, tint = EthiopianGold, modifier = Modifier.size(28.dp))
                        }
                    }
                }
            }
        }
    }
}
