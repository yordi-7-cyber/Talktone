package com.example.talktone.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.talktone.R
import com.example.talktone.ui.theme.EthiopianGold
import com.example.talktone.ui.theme.EthiopianGreen
import com.example.talktone.ui.theme.ImageBackground
import com.example.talktone.viewmodel.AppViewModel

@Composable
fun OnboardingScreen(viewModel: AppViewModel, onFinished: () -> Unit) {
    var step by remember { mutableStateOf(0) }
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var level by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("reader") }
    var langPref by remember { mutableStateOf("am") }
    var profilePicUri by remember { mutableStateOf<Uri?>(null) }
    
    val picker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        profilePicUri = uri
    }

    val bgRes = when (step) {
        0 -> R.drawable.home
        1 -> R.drawable.cozy
        2 -> R.drawable.warm
        3 -> R.drawable.candles
        else -> R.drawable.reading
    }

    ImageBackground(resId = bgRes, isDark = true, overlayAlpha = 0.72f) {
        AnimatedContent(
            targetState = step,
            transitionSpec = {
                slideInHorizontally { it } + fadeIn() togetherWith
                        slideOutHorizontally { -it } + fadeOut()
            },
            label = "onboarding_step"
        ) { currentStep ->
            when (currentStep) {
                0 -> WelcomeStep(onNext = { step = 1 })
                1 -> NamePhoneStep(
                    name = name, phone = phone, profilePicUri = profilePicUri,
                    onNameChange = { name = it }, onPhoneChange = { phone = it },
                    onPickImage = { picker.launch("image/*") },
                    onNext = { if (name.isNotBlank()) step = 2 }
                )
                2 -> LangLevelStep(
                    langPref = langPref, level = level,
                    onLangChange = { langPref = it }, onLevelChange = { level = it },
                    onNext = { if (level.isNotEmpty()) step = if (level == "advanced") 3 else 4 }
                )
                3 -> RoleStep(
                    selected = role, onSelect = { role = it },
                    onNext = { step = 4 }
                )
                else -> {
                    LaunchedEffect(Unit) {
                        viewModel.saveUserProfile(name, phone, level, role, profilePicUri?.toString() ?: "")
                        viewModel.setLanguage(langPref)
                        onFinished()
                    }
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("🎉", fontSize = 64.sp)
                            Spacer(Modifier.height(16.dp))
                            Text("እንኳን ደህና መጡ, $name!", color = EthiopianGold, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineMedium, textAlign = TextAlign.Center)
                            Text("Welcome, $name!", color = Color.White.copy(0.8f), style = MaterialTheme.typography.titleLarge)
                        }
                    }
                }
            }
        }
    }
}

// ── Step 0: Welcome ───────────────────────────────────────────────────────────

@Composable
private fun WelcomeStep(onNext: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("✍️", fontSize = 72.sp)
        Spacer(Modifier.height(20.dp))
        Text("ብዕር", fontSize = 56.sp, fontWeight = FontWeight.Bold, color = EthiopianGold)
        Text("Ethiopian Literature", color = Color.White.copy(0.75f), fontSize = 18.sp)
        Spacer(Modifier.height(24.dp))

        Card(shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = Color.Black.copy(0.35f)), border = BorderStroke(1.dp, EthiopianGold.copy(0.4f)), modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("ወደ ብዕር እንኳን ደህና መጡ!", color = EthiopianGold, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
                Spacer(Modifier.height(6.dp))
                Text("Welcome to Biir!", color = Color.White.copy(0.85f), style = MaterialTheme.typography.titleMedium, textAlign = TextAlign.Center)
                Spacer(Modifier.height(12.dp))
                Text("ግጥም • ተረት • ምሳሌ • ልቦለድ • ፈተና", color = Color.White.copy(0.7f), textAlign = TextAlign.Center, fontSize = 14.sp)
                Text("Poems • Folktales • Proverbs • Novels • Quiz", color = Color.White.copy(0.5f), textAlign = TextAlign.Center, fontSize = 12.sp)
            }
        }

        Spacer(Modifier.height(40.dp))
        Button(onClick = onNext, modifier = Modifier.fillMaxWidth().height(58.dp), shape = RoundedCornerShape(29.dp), colors = ButtonDefaults.buttonColors(containerColor = EthiopianGold)) {
            Text("ጀምር / Get Started →", color = Color(0xFF1A0A2E), fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
    }
}

// ── Step 1: Name & Phone ──────────────────────────────────────────────────────

@Composable
private fun NamePhoneStep(
    name: String, phone: String, profilePicUri: Uri?,
    onNameChange: (String) -> Unit, onPhoneChange: (String) -> Unit,
    onPickImage: () -> Unit, onNext: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Profile picture picker
        Box(
            modifier = Modifier.size(110.dp).clip(CircleShape)
                .border(3.dp, EthiopianGold, CircleShape)
                .clickable { onPickImage() },
            contentAlignment = Alignment.Center
        ) {
            if (profilePicUri != null) {
                AsyncImage(model = profilePicUri, contentDescription = null, modifier = Modifier.fillMaxSize().clip(CircleShape), contentScale = ContentScale.Crop)
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.AddAPhoto, null, tint = EthiopianGold, modifier = Modifier.size(36.dp))
                    Text("Add Photo", color = EthiopianGold.copy(0.7f), fontSize = 11.sp)
                }
            }
        }
        Spacer(Modifier.height(6.dp))
        Text("(Optional)", color = Color.White.copy(0.5f), fontSize = 11.sp)
        Spacer(Modifier.height(20.dp))
        
        Text("👤", fontSize = 40.sp)
        Spacer(Modifier.height(8.dp))
        Text("ስምዎን ያስገቡ", color = EthiopianGold, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineMedium)
        Text("Enter your name", color = Color.White.copy(0.6f), style = MaterialTheme.typography.titleSmall)
        Spacer(Modifier.height(24.dp))

        OnboardField("ሙሉ ስም / Full Name (e.g. ልዑል, Abebe)", name, Icons.Default.Person, onNameChange)
        Spacer(Modifier.height(14.dp))
        OnboardField("ስልክ ቁጥር / Phone (Optional)", phone, Icons.Default.Phone, onPhoneChange)

        Spacer(Modifier.height(28.dp))
        Button(onClick = onNext, modifier = Modifier.fillMaxWidth().height(56.dp), shape = RoundedCornerShape(28.dp), colors = ButtonDefaults.buttonColors(containerColor = EthiopianGold), enabled = name.isNotBlank()) {
            Text("ቀጣይ / Next →", color = Color(0xFF1A0A2E), fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
    }
}

// ── Step 2: Language + Level ──────────────────────────────────────────────────

@Composable
private fun LangLevelStep(
    langPref: String, level: String,
    onLangChange: (String) -> Unit, onLevelChange: (String) -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(28.dp).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("🌐", fontSize = 48.sp)
        Spacer(Modifier.height(8.dp))
        Text("ቋንቋ ምርጫ", color = EthiopianGold, fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineSmall)
        Text("Language Preference", color = Color.White.copy(0.6f), fontSize = 13.sp)
        Spacer(Modifier.height(16.dp))

        // Language toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            listOf("am" to "አማርኛ ብቻ", "both" to "አማርኛ + English").forEach { (key, label) ->
                val selected = langPref == key
                Card(
                    onClick = { onLangChange(key) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (selected) EthiopianGold.copy(0.25f) else Color.Black.copy(0.3f)
                    ),
                    border = BorderStroke(if (selected) 2.dp else 1.dp,
                        if (selected) EthiopianGold else Color.White.copy(0.2f))
                ) {
                    Column(modifier = Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(if (key == "am") "🇪🇹" else "🌍", fontSize = 24.sp)
                        Spacer(Modifier.height(4.dp))
                        Text(label, color = if (selected) EthiopianGold else Color.White,
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 13.sp, textAlign = TextAlign.Center)
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))
        Text("📊 የቋንቋ ደረጃ / Level", color = EthiopianGold, fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(12.dp))

        listOf(
            Triple("beginner", "ጀማሪ", "Beginner — ሀ ሁ ሂ, ቁጥሮች, ቃላት"),
            Triple("intermediate", "መካከለኛ", "Intermediate — ግጥም, ተረት, ምሳሌ"),
            Triple("advanced", "ከፍተኛ", "Advanced — ልቦለድ, ፈጠራ ጽሑፍ")
        ).forEach { (key, am, desc) ->
            val selected = level == key
            Card(
                onClick = { onLevelChange(key) },
                modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (selected) EthiopianGold.copy(0.2f) else Color.Black.copy(0.3f)
                ),
                border = BorderStroke(if (selected) 2.dp else 1.dp,
                    if (selected) EthiopianGold else Color.White.copy(0.2f))
            ) {
                Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier.size(22.dp).clip(CircleShape)
                            .border(2.dp, EthiopianGold, CircleShape)
                            .background(if (selected) EthiopianGold else Color.Transparent),
                        contentAlignment = Alignment.Center
                    ) {
                        if (selected) Icon(Icons.Default.Check, null,
                            tint = Color(0xFF1A0A2E), modifier = Modifier.size(14.dp))
                    }
                    Spacer(Modifier.width(14.dp))
                    Column {
                        Text(am, color = if (selected) EthiopianGold else Color.White,
                            fontWeight = FontWeight.Bold)
                        Text(desc, color = Color.White.copy(0.55f), fontSize = 12.sp)
                    }
                }
            }
        }

        Spacer(Modifier.height(28.dp))
        Button(
            onClick = onNext,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = EthiopianGold),
            enabled = level.isNotEmpty()
        ) {
            Text("ቀጣይ / Next →", color = Color(0xFF1A0A2E),
                fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
    }
}

// ── Step 3: Role (Advanced only) ──────────────────────────────────────────────

@Composable
private fun RoleStep(selected: String, onSelect: (String) -> Unit, onNext: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("🎭", fontSize = 56.sp)
        Spacer(Modifier.height(12.dp))
        Text("ሚናዎ ምንድን ነው?", color = EthiopianGold, fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium)
        Text("What is your role?", color = Color.White.copy(0.6f))
        Spacer(Modifier.height(32.dp))

        listOf(
            Triple("reader", "📖", "አንባቢ / Reader"),
            Triple("creator", "✍️", "ፈጣሪ / Creator — ስራዎን ያስገቡ")
        ).forEach { (key, emoji, label) ->
            val isSelected = selected == key
            Card(
                onClick = { onSelect(key) },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) EthiopianGold.copy(0.22f) else Color.Black.copy(0.3f)
                ),
                border = BorderStroke(if (isSelected) 2.dp else 1.dp,
                    if (isSelected) EthiopianGold else Color.White.copy(0.2f))
            ) {
                Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(emoji, fontSize = 36.sp)
                    Spacer(Modifier.width(16.dp))
                    Text(label, color = if (isSelected) EthiopianGold else Color.White,
                        fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            }
        }

        Spacer(Modifier.height(36.dp))
        Button(
            onClick = onNext,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = EthiopianGold)
        ) {
            Text("ጀምር! 🚀", color = Color(0xFF1A0A2E), fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
    }
}

// ── Shared field with perfect rounded corners ─────────────────────────────────

@Composable
private fun OnboardField(
    label: String, value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value, onValueChange = onChange,
        label = { Text(label, color = Color.White.copy(0.65f), fontSize = 12.sp) },
        leadingIcon = { 
            Box(Modifier.padding(start = 8.dp)) {
                Icon(icon, null, tint = EthiopianGold) 
            }
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White, unfocusedTextColor = Color.White,
            focusedBorderColor = EthiopianGold, unfocusedBorderColor = EthiopianGold.copy(0.4f),
            cursorColor = EthiopianGold,
            focusedContainerColor = Color.Black.copy(0.5f),
            unfocusedContainerColor = Color.Black.copy(0.4f)
        ),
        shape = RoundedCornerShape(28.dp),
        textStyle = androidx.compose.ui.text.TextStyle(
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    )
}
