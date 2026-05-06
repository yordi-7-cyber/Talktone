package com.example.talktone.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.talktone.data.ReadingStreakEntity
import com.example.talktone.R
import com.example.talktone.ui.theme.EthiopianGold
import com.example.talktone.ui.theme.ImageBackground
import com.example.talktone.viewmodel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: AppViewModel,
    isDark: Boolean,
    language: String,
    streak: ReadingStreakEntity?,
    onBack: () -> Unit,
    onAdminClick: (() -> Unit)? = null
) {
    ImageBackground(resId = R.drawable.cozy, isDark = isDark, overlayAlpha = if (isDark) 0.78f else 0.62f) {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {

            Row(
                modifier = Modifier.fillMaxWidth().statusBarsPadding()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Text(
                    text = "⚙️ ${if (language == "am") "ቅንብሮች" else "Settings"}",
                    style = MaterialTheme.typography.headlineMedium,
                    color = EthiopianGold, fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            SettingsSectionHeader(
                title = if (language == "am") "መልክ" else "Appearance",
                icon = Icons.Default.Palette
            )

            SettingsToggleItem(
                title = if (language == "am") "ጨለማ ሁነታ" else "Dark Mode",
                subtitle = if (language == "am") "ጨለማ ዳራ ይጠቀሙ" else "Use dark background",
                icon = if (isDark) Icons.Default.DarkMode else Icons.Default.LightMode,
                checked = isDark,
                onToggle = { viewModel.toggleDarkMode() }
            )

            SettingsToggleItem(
                title = if (language == "am") "ቋንቋ: አማርኛ" else "Language: English",
                subtitle = if (language == "am") "ወደ እንግሊዝኛ ቀይር" else "Switch to Amharic",
                icon = Icons.Default.Language,
                checked = language == "am",
                onToggle = { viewModel.setLanguage(if (language == "am") "en" else "am") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            SettingsSectionHeader(
                title = if (language == "am") "የማንበቢያ ስታቲስቲክስ" else "Reading Stats",
                icon = Icons.Default.BarChart
            )

            streak?.let { s ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f)),
                    border = BorderStroke(1.dp, EthiopianGold.copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        StatRow(
                            label = if (language == "am") "🔥 ተከታታይ ቀናት" else "🔥 Current Streak",
                            value = "${s.currentStreak} ${if (language == "am") "ቀናት" else "days"}"
                        )
                        HorizontalDivider(color = Color.White.copy(alpha = 0.1f), modifier = Modifier.padding(vertical = 8.dp))
                        StatRow(
                            label = if (language == "am") "🏆 ረጅሙ ተከታታይ" else "🏆 Longest Streak",
                            value = "${s.longestStreak} ${if (language == "am") "ቀናት" else "days"}"
                        )
                        HorizontalDivider(color = Color.White.copy(alpha = 0.1f), modifier = Modifier.padding(vertical = 8.dp))
                        StatRow(
                            label = if (language == "am") "📅 ጠቅላላ ቀናት" else "📅 Total Days Read",
                            value = "${s.totalDaysRead} ${if (language == "am") "ቀናት" else "days"}"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Admin section
            if (onAdminClick != null) {
                SettingsSectionHeader(
                    title = if (language == "am") "አስተዳዳሪ" else "Admin",
                    icon = Icons.Default.AdminPanelSettings
                )
                Card(
                    onClick = onAdminClick,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f)),
                    border = BorderStroke(1.dp, EthiopianGold.copy(alpha = 0.2f))
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Lock, contentDescription = null, tint = EthiopianGold,
                            modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (language == "am") "Admin Panel" else "Admin Panel",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
                                color = Color.White
                            )
                            Text(
                                text = if (language == "am") "ይዘቶችን ያስተዳድሩ" else "Manage content",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.6f)
                            )
                        }
                        Icon(Icons.Default.ChevronRight, contentDescription = null,
                            tint = Color.White.copy(alpha = 0.4f))
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            SettingsSectionHeader(
                title = if (language == "am") "ስለ መተግበሪያው" else "About",
                icon = Icons.Default.Info
            )

            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f)),
                border = BorderStroke(1.dp, EthiopianGold.copy(alpha = 0.3f))
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "📚", fontSize = 48.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (language == "am") "የኢትዮጵያ ሥነ ጽሑፍ" else "Ethiopian Literature",
                        style = MaterialTheme.typography.titleLarge,
                        color = EthiopianGold, fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Version 1.0.0",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (language == "am")
                            "ይህ መተግበሪያ የኢትዮጵያ ሥነ ጽሑፍን ለማስተዋወቅ ተሠርቷል።\nግጥም፣ ተረት፣ ምሳሌ እና ፈተናዎችን ያካትታል።"
                        else
                            "This app is built to promote Ethiopian literature.\nIt includes poems, folktales, proverbs and quizzes.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.8f),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun SettingsSectionHeader(title: String, icon: ImageVector) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = EthiopianGold, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = title, style = MaterialTheme.typography.titleMedium,
            color = EthiopianGold, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun SettingsToggleItem(
    title: String, subtitle: String, icon: ImageVector,
    checked: Boolean, onToggle: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f)),
        border = BorderStroke(1.dp, EthiopianGold.copy(alpha = 0.2f))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = EthiopianGold, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(text = title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
                        color = Color.White)
                    Text(text = subtitle, style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.6f))
                }
            }
            Switch(
                checked = checked, onCheckedChange = { onToggle() },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color(0xFF1A0A2E),
                    checkedTrackColor = EthiopianGold
                )
            )
        }
    }
}

@Composable
fun StatRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium,
            color = Color.White.copy(alpha = 0.8f))
        Text(text = value,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = EthiopianGold)
    }
}
