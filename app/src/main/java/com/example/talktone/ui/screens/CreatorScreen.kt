package com.example.talktone.ui.screens

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.talktone.data.CreatorSubmission
import com.example.talktone.data.UserProfile
import com.example.talktone.ui.theme.EthiopianGold
import com.example.talktone.ui.theme.EthiopianGreen
import com.example.talktone.ui.theme.EthiopianRed
import com.example.talktone.viewmodel.AppViewModel

// ── Submit Work Screen ────────────────────────────────────────────────────────

@Composable
fun CreatorSubmitScreen(
    viewModel: AppViewModel,
    userProfile: UserProfile?,
    isDark: Boolean,
    language: String,
    onBack: () -> Unit,
    onSubmitted: () -> Unit
) {
    var titleAm by remember { mutableStateOf("") }
    var contentAm by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("poem") }
    var showSuccess by remember { mutableStateOf(false) }

    val categories = listOf("poem" to "ግጥም", "teret" to "ተረት", "misale" to "ምሳሌ", "novel" to "ልቦለድ")

    val bg = if (isDark)
        Brush.verticalGradient(listOf(Color(0xFF0D0221), Color(0xFF1A0A2E)))
    else
        Brush.verticalGradient(listOf(Color(0xFF1A0050), Color(0xFF4A0E8F)))

    if (showSuccess) {
        Box(modifier = Modifier.fillMaxSize().background(bg), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(32.dp)) {
                Text("✅", fontSize = 72.sp)
                Spacer(Modifier.height(16.dp))
                Text("ስራዎ ተልኳል!", color = EthiopianGold, fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineMedium)
                Text("ከአስተዳዳሪ ፈቃድ በኋላ ይታተማል", color = Color.White.copy(0.7f), textAlign = TextAlign.Center)
                Spacer(Modifier.height(32.dp))
                Button(onClick = onSubmitted, colors = ButtonDefaults.buttonColors(containerColor = EthiopianGold),
                    shape = RoundedCornerShape(14.dp)) {
                    Text("ወደ ቤት", color = Color(0xFF1A0A2E), fontWeight = FontWeight.Bold)
                }
            }
        }
        return
    }

    Box(modifier = Modifier.fillMaxSize().background(bg)) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null, tint = Color.White) }
                Text("✍️ ስራ አስገባ", color = EthiopianGold, fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineSmall)
            }

            Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)) {

                // Category selector
                Text("ምድብ ይምረጡ", color = Color.White.copy(0.8f), fontWeight = FontWeight.Medium)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    categories.forEach { (key, label) ->
                        FilterChip(
                            selected = category == key,
                            onClick = { category = key },
                            label = { Text(label, fontSize = 13.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = EthiopianGold,
                                selectedLabelColor = Color(0xFF1A0A2E),
                                containerColor = Color.White.copy(0.1f),
                                labelColor = Color.White
                            )
                        )
                    }
                }

                OutlinedTextField(
                    value = titleAm, onValueChange = { titleAm = it },
                    label = { Text("ርዕስ (አማርኛ)*", color = Color.White.copy(0.7f)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = creatorFieldColors(), singleLine = true
                )

                OutlinedTextField(
                    value = contentAm, onValueChange = { contentAm = it },
                    label = { Text("ይዘት (አማርኛ)*", color = Color.White.copy(0.7f)) },
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    colors = creatorFieldColors(), maxLines = 10
                )

                if (userProfile != null) {
                    Card(shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = EthiopianGold.copy(0.1f)),
                        border = BorderStroke(1.dp, EthiopianGold.copy(0.3f)),
                        modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Person, null, tint = EthiopianGold, modifier = Modifier.size(20.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("${userProfile.name} • ${userProfile.phone}", color = Color.White.copy(0.8f), fontSize = 13.sp)
                        }
                    }
                }

                Button(
                    onClick = {
                        if (titleAm.isNotBlank() && contentAm.isNotBlank()) {
                            viewModel.submitWork(CreatorSubmission(
                                authorName = userProfile?.name ?: "Unknown",
                                authorPhone = userProfile?.phone ?: "",
                                category = category,
                                titleAm = titleAm, contentAm = contentAm
                            ))
                            showSuccess = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = EthiopianGold),
                    enabled = titleAm.isNotBlank() && contentAm.isNotBlank()
                ) {
                    Icon(Icons.Default.Send, null, tint = Color(0xFF1A0A2E))
                    Spacer(Modifier.width(8.dp))
                    Text("ላክ", color = Color(0xFF1A0A2E), fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            }
        }
    }
}

@Composable
private fun creatorFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = Color.White, unfocusedTextColor = Color.White,
    focusedBorderColor = EthiopianGold, unfocusedBorderColor = Color.White.copy(0.3f),
    cursorColor = EthiopianGold
)

// ── Community Feed ────────────────────────────────────────────────────────────

@Composable
fun CommunityFeedScreen(
    viewModel: AppViewModel,
    isDark: Boolean,
    language: String,
    onBack: () -> Unit
) {
    val approved by viewModel.approvedSubmissions.collectAsState()
    val bg = if (isDark)
        Brush.verticalGradient(listOf(Color(0xFF0D0221), Color(0xFF1A0A2E)))
    else
        Brush.verticalGradient(listOf(Color(0xFF1A0050), Color(0xFF4A0E8F)))

    Box(modifier = Modifier.fillMaxSize().background(bg)) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null, tint = Color.White) }
                Text("🌟 የማህበረሰብ ስራዎች", color = EthiopianGold, fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineSmall)
            }

            if (approved.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("✍️", fontSize = 64.sp)
                        Spacer(Modifier.height(16.dp))
                        Text("ገና ምንም ስራ አልተፈቀደም", color = Color.White.copy(0.6f))
                    }
                }
            } else {
                LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(approved) { sub ->
                        CommunityCard(sub = sub, onLike = { viewModel.likeSubmission(sub.id) })
                    }
                }
            }
        }
    }
}

@Composable
private fun CommunityCard(sub: CreatorSubmission, onLike: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(0.08f)),
        border = BorderStroke(1.dp, EthiopianGold.copy(0.25f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(when(sub.category) { "poem" -> "📜"; "teret" -> "🦁"; "misale" -> "💬"; else -> "📖" }, fontSize = 20.sp)
                Spacer(Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(sub.titleAm, color = EthiopianGold, fontWeight = FontWeight.Bold)
                    Text("— ${sub.authorName}", color = Color.White.copy(0.5f), fontSize = 12.sp)
                }
            }
            Spacer(Modifier.height(8.dp))
            Text(sub.contentAm, color = Color.White.copy(0.85f), maxLines = 4)
            Spacer(Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onLike, modifier = Modifier.size(36.dp)) {
                    Icon(Icons.Default.Favorite, null, tint = EthiopianRed, modifier = Modifier.size(20.dp))
                }
                Text("${sub.likes}", color = Color.White.copy(0.7f), fontSize = 14.sp)
            }
        }
    }
}
