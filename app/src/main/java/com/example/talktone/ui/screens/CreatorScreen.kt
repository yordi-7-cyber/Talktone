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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.talktone.R
import com.example.talktone.data.CreatorSubmission
import com.example.talktone.data.UserProfile
import com.example.talktone.ui.theme.EthiopianGold
import com.example.talktone.ui.theme.EthiopianRed
import com.example.talktone.ui.theme.ImageBackground
import com.example.talktone.viewmodel.AppViewModel

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

    ImageBackground(resId = R.drawable.candles, isDark = true, overlayAlpha = 0.75f) {
        if (showSuccess) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(32.dp)) {
                    Text("✅", fontSize = 72.sp)
                    Spacer(Modifier.height(16.dp))
                    Text("ስራዎ ተልኳል!", color = EthiopianGold, fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineMedium)
                    Text("Submitted for review!", color = Color.White.copy(0.7f))
                    Spacer(Modifier.height(32.dp))
                    Button(onClick = onSubmitted, colors = ButtonDefaults.buttonColors(containerColor = EthiopianGold),
                        shape = RoundedCornerShape(14.dp)) {
                        Text("ወደ ቤት / Home", color = Color(0xFF1A0A2E), fontWeight = FontWeight.Bold)
                    }
                }
            }
            return@ImageBackground
        }

        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null, tint = Color.White) }
                Text("✍️ ${if (language == "am") "ስራ አስገባ" else "Submit Work"}",
                    color = EthiopianGold, fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineSmall)
            }

            Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)) {

                Text(if (language == "am") "ምድብ ይምረጡ" else "Select Category",
                    color = Color.White.copy(0.8f), fontWeight = FontWeight.Medium)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("poem" to "ግጥም", "teret" to "ተረት", "misale" to "ምሳሌ", "novel" to "ልቦለድ").forEach { (key, label) ->
                        FilterChip(selected = category == key, onClick = { category = key },
                            label = { Text(label, fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = EthiopianGold,
                                selectedLabelColor = Color(0xFF1A0A2E),
                                containerColor = Color.Black.copy(0.3f), labelColor = Color.White))
                    }
                }

                OutlinedTextField(value = titleAm, onValueChange = { titleAm = it },
                    label = { Text(if (language == "am") "ርዕስ*" else "Title*", color = Color.White.copy(0.7f)) },
                    modifier = Modifier.fillMaxWidth(), singleLine = true,
                    colors = creatorColors(), shape = RoundedCornerShape(14.dp))

                OutlinedTextField(value = contentAm, onValueChange = { contentAm = it },
                    label = { Text(if (language == "am") "ይዘት*" else "Content*", color = Color.White.copy(0.7f)) },
                    modifier = Modifier.fillMaxWidth().height(200.dp), maxLines = 10,
                    colors = creatorColors(), shape = RoundedCornerShape(14.dp))

                userProfile?.let {
                    Card(shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = EthiopianGold.copy(0.12f)),
                        border = BorderStroke(1.dp, EthiopianGold.copy(0.3f)),
                        modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Person, null, tint = EthiopianGold, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("${it.name}${if (it.phone.isNotEmpty()) " • ${it.phone}" else ""}",
                                color = Color.White.copy(0.8f), fontSize = 13.sp)
                        }
                    }
                }

                Button(onClick = {
                    if (titleAm.isNotBlank() && contentAm.isNotBlank()) {
                        viewModel.submitWork(CreatorSubmission(
                            authorName = userProfile?.name ?: "Unknown",
                            authorPhone = userProfile?.phone ?: "",
                            category = category, titleAm = titleAm, contentAm = contentAm))
                        showSuccess = true
                    }
                }, modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = EthiopianGold),
                    enabled = titleAm.isNotBlank() && contentAm.isNotBlank()) {
                    Icon(Icons.Default.Send, null, tint = Color(0xFF1A0A2E))
                    Spacer(Modifier.width(8.dp))
                    Text(if (language == "am") "ላክ" else "Submit",
                        color = Color(0xFF1A0A2E), fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            }
        }
    }
}

@Composable
private fun creatorColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = Color.White, unfocusedTextColor = Color.White,
    focusedBorderColor = EthiopianGold, unfocusedBorderColor = Color.White.copy(0.3f),
    cursorColor = EthiopianGold,
    focusedContainerColor = Color.Black.copy(0.25f),
    unfocusedContainerColor = Color.Black.copy(0.2f)
)

@Composable
fun CommunityFeedScreen(viewModel: AppViewModel, isDark: Boolean, language: String, onBack: () -> Unit) {
    val approved by viewModel.approvedSubmissions.collectAsState()

    ImageBackground(resId = R.drawable.home, isDark = isDark, overlayAlpha = if (isDark) 0.75f else 0.6f) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null, tint = Color.White) }
                Text("🌟 ${if (language == "am") "የማህበረሰብ ስራዎች" else "Community Works"}",
                    color = EthiopianGold, fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineSmall)
            }
            if (approved.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("✍️", fontSize = 64.sp); Spacer(Modifier.height(16.dp))
                        Text(if (language == "am") "ገና ምንም ስራ አልተፈቀደም" else "No approved works yet",
                            color = Color.White.copy(0.6f))
                    }
                }
            } else {
                LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(approved) { sub ->
                        Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.Black.copy(0.35f)),
                            border = BorderStroke(1.dp, EthiopianGold.copy(0.25f))) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(when(sub.category){"poem"->"📜";"teret"->"🦁";"misale"->"💬";else->"📖"}, fontSize = 20.sp)
                                    Spacer(Modifier.width(8.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(sub.titleAm, color = EthiopianGold, fontWeight = FontWeight.Bold)
                                        Text("— ${sub.authorName}", color = Color.White.copy(0.5f), fontSize = 12.sp)
                                    }
                                }
                                Spacer(Modifier.height(8.dp))
                                Text(sub.contentAm, color = Color.White.copy(0.85f), maxLines = 4, overflow = TextOverflow.Ellipsis)
                                Spacer(Modifier.height(10.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    IconButton(onClick = { viewModel.likeSubmission(sub.id) }, modifier = Modifier.size(32.dp)) {
                                        Icon(Icons.Default.Favorite, null, tint = EthiopianRed, modifier = Modifier.size(18.dp))
                                    }
                                    Text("${sub.likes}", color = Color.White.copy(0.7f), fontSize = 14.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
