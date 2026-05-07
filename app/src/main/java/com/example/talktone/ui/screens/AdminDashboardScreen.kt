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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.talktone.data.*
import com.example.talktone.R
import com.example.talktone.ui.theme.EthiopianGold
import com.example.talktone.ui.theme.ImageBackground
import com.example.talktone.viewmodel.AppViewModel
import androidx.compose.ui.text.style.TextAlign

private enum class AdminTab { SUBMISSIONS, POEMS, TERETS, MISALE, QUIZ, QUOTES }

@Composable
fun AdminDashboardScreen(
    viewModel: AppViewModel,
    isDark: Boolean,
    onBack: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(AdminTab.POEMS) }
    val bgColors = if (isDark) listOf(Color(0xFF1A0A2E), Color(0xFF0D1B2A))
    else listOf(Color(0xFF2D1B69), Color(0xFF4A0E8F))

    ImageBackground(resId = R.drawable.dark, isDark = true, overlayAlpha = 0.78f) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth().statusBarsPadding()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Text(
                    "🔐 Admin Dashboard",
                    style = MaterialTheme.typography.headlineSmall,
                    color = EthiopianGold, fontWeight = FontWeight.Bold
                )
            }

            ScrollableTabRow(
                selectedTabIndex = selectedTab.ordinal,
                containerColor = Color.Transparent,
                contentColor = EthiopianGold,
                edgePadding = 8.dp
            ) {
                AdminTab.values().forEach { tab ->
                    Tab(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        text = {
                            Text(
                                when (tab) {
                                    AdminTab.SUBMISSIONS -> "ስራዎች"
                                    AdminTab.POEMS -> "ግጥም"
                                    AdminTab.TERETS -> "ተረት"
                                    AdminTab.MISALE -> "ምሳሌ"
                                    AdminTab.QUIZ -> "ፈተና"
                                    AdminTab.QUOTES -> "ጥቅስ"
                                },
                                color = if (selectedTab == tab) EthiopianGold else Color.White.copy(alpha = 0.6f),
                                fontWeight = if (selectedTab == tab) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                }
            }

            when (selectedTab) {
                AdminTab.SUBMISSIONS -> SubmissionsAdminTab(viewModel)
                AdminTab.POEMS -> PoemsAdminTab(viewModel)
                AdminTab.TERETS -> TeretsAdminTab(viewModel)
                AdminTab.MISALE -> MisaleAdminTab(viewModel)
                AdminTab.QUIZ -> QuizAdminTab(viewModel)
                AdminTab.QUOTES -> QuotesAdminTab(viewModel)
            }
        }
    }
}

// ── Shared UI helpers ─────────────────────────────────────────────────────────

@Composable
private fun AdminListScaffold(onAdd: () -> Unit, content: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        content()
        FloatingActionButton(
            onClick = onAdd,
            modifier = Modifier.align(Alignment.BottomEnd).padding(24.dp),
            containerColor = EthiopianGold
        ) { Icon(Icons.Default.Add, contentDescription = "Add", tint = Color(0xFF1A0A2E)) }
    }
}

@Composable
private fun AdminEmptyState(msg: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(msg, color = Color.White.copy(alpha = 0.5f), fontSize = 18.sp)
    }
}

@Composable
private fun AdminItemCard(
    titleAm: String, titleEn: String, sub: String,
    onEdit: () -> Unit, onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f)),
        border = BorderStroke(1.dp, EthiopianGold.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(titleAm, color = EthiopianGold, fontWeight = FontWeight.Bold,
                    maxLines = 1, overflow = TextOverflow.Ellipsis)
                if (titleEn.isNotEmpty())
                    Text(titleEn, color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp,
                        maxLines = 1, overflow = TextOverflow.Ellipsis)
                if (sub.isNotEmpty())
                    Text(sub, color = Color.White.copy(alpha = 0.4f), fontSize = 11.sp)
            }
            IconButton(onClick = onEdit) {
                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = EthiopianGold)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red.copy(alpha = 0.7f))
            }
        }
    }
}

@Composable
private fun AdminFormDialog(
    title: String,
    onDismiss: () -> Unit,
    onSave: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1A0A2E)),
            border = BorderStroke(1.dp, EthiopianGold.copy(alpha = 0.4f))
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(title, color = EthiopianGold, fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(16.dp))
                content()
                Spacer(Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismiss) { Text("ሰርዝ", color = Color.White.copy(alpha = 0.6f)) }
                    Spacer(Modifier.width(8.dp))
                    Button(
                        onClick = onSave,
                        colors = ButtonDefaults.buttonColors(containerColor = EthiopianGold)
                    ) { Text("ጠብቅ", color = Color(0xFF1A0A2E), fontWeight = FontWeight.Bold) }
                }
            }
        }
    }
}

@Composable
private fun AdminTextField(label: String, value: String, maxLines: Int = 1, onChange: (String) -> Unit) {
    OutlinedTextField(
        value = value, onValueChange = onChange,
        label = { Text(label, color = Color.White.copy(alpha = 0.6f)) },
        maxLines = maxLines,
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White, unfocusedTextColor = Color.White,
            focusedBorderColor = EthiopianGold, unfocusedBorderColor = EthiopianGold.copy(alpha = 0.4f),
            cursorColor = EthiopianGold,
            focusedContainerColor = Color.Black.copy(alpha = 0.4f),
            unfocusedContainerColor = Color.Black.copy(alpha = 0.3f)
        )
    )
}

// ── POEMS ─────────────────────────────────────────────────────────────────────

@Composable
fun PoemsAdminTab(viewModel: AppViewModel) {
    val items by viewModel.adminPoems.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var editing by remember { mutableStateOf<AdminPoem?>(null) }

    if (showDialog) {
        AdminPoemDialog(editing,
            onDismiss = { showDialog = false; editing = null },
            onSave = { item ->
                if (editing == null) viewModel.insertAdminPoem(item) else viewModel.updateAdminPoem(item)
                showDialog = false; editing = null
            })
    }
    AdminListScaffold(onAdd = { showDialog = true }) {
        if (items.isEmpty()) AdminEmptyState("ምንም ግጥም የለም")
        else LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(items) { p ->
                AdminItemCard(p.titleAm, p.titleEn, p.author,
                    onEdit = { editing = p; showDialog = true },
                    onDelete = { viewModel.deleteAdminPoem(p) })
            }
        }
    }
}

@Composable
private fun AdminPoemDialog(initial: AdminPoem?, onDismiss: () -> Unit, onSave: (AdminPoem) -> Unit) {
    var titleAm by remember { mutableStateOf(initial?.titleAm ?: "") }
    var titleEn by remember { mutableStateOf(initial?.titleEn ?: "") }
    var contentAm by remember { mutableStateOf(initial?.contentAm ?: "") }
    var contentEn by remember { mutableStateOf(initial?.contentEn ?: "") }
    var author by remember { mutableStateOf(initial?.author ?: "") }
    AdminFormDialog("ግጥም ${if (initial == null) "ጨምር" else "አርትዕ"}", onDismiss, {
        if (titleAm.isNotBlank() && contentAm.isNotBlank())
            onSave(AdminPoem(id = initial?.id ?: 0, titleAm = titleAm, titleEn = titleEn,
                contentAm = contentAm, contentEn = contentEn, author = author))
    }) {
        AdminTextField("ርዕስ (አማርኛ)*", titleAm) { titleAm = it }
        AdminTextField("Title (English)", titleEn) { titleEn = it }
        AdminTextField("ይዘት (አማርኛ)*", contentAm, 5) { contentAm = it }
        AdminTextField("Content (English)", contentEn, 5) { contentEn = it }
        AdminTextField("ደራሲ / Author", author) { author = it }
    }
}

// ── TERETS ────────────────────────────────────────────────────────────────────

@Composable
fun TeretsAdminTab(viewModel: AppViewModel) {
    val items by viewModel.adminTerets.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var editing by remember { mutableStateOf<AdminTeret?>(null) }

    if (showDialog) {
        AdminTeretDialog(editing,
            onDismiss = { showDialog = false; editing = null },
            onSave = { item ->
                if (editing == null) viewModel.insertAdminTeret(item) else viewModel.updateAdminTeret(item)
                showDialog = false; editing = null
            })
    }
    AdminListScaffold(onAdd = { showDialog = true }) {
        if (items.isEmpty()) AdminEmptyState("ምንም ተረት የለም")
        else LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(items) { t ->
                AdminItemCard(t.titleAm, t.titleEn, t.author,
                    onEdit = { editing = t; showDialog = true },
                    onDelete = { viewModel.deleteAdminTeret(t) })
            }
        }
    }
}

@Composable
private fun AdminTeretDialog(initial: AdminTeret?, onDismiss: () -> Unit, onSave: (AdminTeret) -> Unit) {
    var titleAm by remember { mutableStateOf(initial?.titleAm ?: "") }
    var titleEn by remember { mutableStateOf(initial?.titleEn ?: "") }
    var contentAm by remember { mutableStateOf(initial?.contentAm ?: "") }
    var contentEn by remember { mutableStateOf(initial?.contentEn ?: "") }
    var author by remember { mutableStateOf(initial?.author ?: "") }
    AdminFormDialog("ተረት ${if (initial == null) "ጨምር" else "አርትዕ"}", onDismiss, {
        if (titleAm.isNotBlank() && contentAm.isNotBlank())
            onSave(AdminTeret(id = initial?.id ?: 0, titleAm = titleAm, titleEn = titleEn,
                contentAm = contentAm, contentEn = contentEn, author = author))
    }) {
        AdminTextField("ርዕስ (አማርኛ)*", titleAm) { titleAm = it }
        AdminTextField("Title (English)", titleEn) { titleEn = it }
        AdminTextField("ይዘት (አማርኛ)*", contentAm, 5) { contentAm = it }
        AdminTextField("Content (English)", contentEn, 5) { contentEn = it }
        AdminTextField("ደራሲ / Author", author) { author = it }
    }
}

// ── MISALE ────────────────────────────────────────────────────────────────────

@Composable
fun MisaleAdminTab(viewModel: AppViewModel) {
    val items by viewModel.adminMisale.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var editing by remember { mutableStateOf<AdminMisale?>(null) }

    if (showDialog) {
        AdminMisaleDialog(editing,
            onDismiss = { showDialog = false; editing = null },
            onSave = { item ->
                if (editing == null) viewModel.insertAdminMisale(item) else viewModel.updateAdminMisale(item)
                showDialog = false; editing = null
            })
    }
    AdminListScaffold(onAdd = { showDialog = true }) {
        if (items.isEmpty()) AdminEmptyState("ምንም ምሳሌ የለም")
        else LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(items) { m ->
                AdminItemCard(m.titleAm, m.titleEn, m.author,
                    onEdit = { editing = m; showDialog = true },
                    onDelete = { viewModel.deleteAdminMisale(m) })
            }
        }
    }
}

@Composable
private fun AdminMisaleDialog(initial: AdminMisale?, onDismiss: () -> Unit, onSave: (AdminMisale) -> Unit) {
    var titleAm by remember { mutableStateOf(initial?.titleAm ?: "") }
    var titleEn by remember { mutableStateOf(initial?.titleEn ?: "") }
    var contentAm by remember { mutableStateOf(initial?.contentAm ?: "") }
    var contentEn by remember { mutableStateOf(initial?.contentEn ?: "") }
    var author by remember { mutableStateOf(initial?.author ?: "") }
    AdminFormDialog("ምሳሌ ${if (initial == null) "ጨምር" else "አርትዕ"}", onDismiss, {
        if (titleAm.isNotBlank() && contentAm.isNotBlank())
            onSave(AdminMisale(id = initial?.id ?: 0, titleAm = titleAm, titleEn = titleEn,
                contentAm = contentAm, contentEn = contentEn, author = author))
    }) {
        AdminTextField("ርዕስ (አማርኛ)*", titleAm) { titleAm = it }
        AdminTextField("Title (English)", titleEn) { titleEn = it }
        AdminTextField("ይዘት (አማርኛ)*", contentAm, 3) { contentAm = it }
        AdminTextField("Content (English)", contentEn, 3) { contentEn = it }
        AdminTextField("ደራሲ / Author", author) { author = it }
    }
}

// ── QUIZ ──────────────────────────────────────────────────────────────────────

@Composable
fun QuizAdminTab(viewModel: AppViewModel) {
    val items by viewModel.adminQuizzes.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var editing by remember { mutableStateOf<AdminQuiz?>(null) }

    if (showDialog) {
        AdminQuizDialog(editing,
            onDismiss = { showDialog = false; editing = null },
            onSave = { item ->
                if (editing == null) viewModel.insertAdminQuiz(item) else viewModel.updateAdminQuiz(item)
                showDialog = false; editing = null
            })
    }
    AdminListScaffold(onAdd = { showDialog = true }) {
        if (items.isEmpty()) AdminEmptyState("ምንም ፈተና የለም")
        else LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(items) { q ->
                AdminItemCard(q.questionAm, q.questionEn, "✓ ${q.correctIndex + 1}",
                    onEdit = { editing = q; showDialog = true },
                    onDelete = { viewModel.deleteAdminQuiz(q) })
            }
        }
    }
}

@Composable
private fun AdminQuizDialog(initial: AdminQuiz?, onDismiss: () -> Unit, onSave: (AdminQuiz) -> Unit) {
    var questionAm by remember { mutableStateOf(initial?.questionAm ?: "") }
    var questionEn by remember { mutableStateOf(initial?.questionEn ?: "") }
    var opt0 by remember { mutableStateOf(initial?.option0 ?: "") }
    var opt1 by remember { mutableStateOf(initial?.option1 ?: "") }
    var opt2 by remember { mutableStateOf(initial?.option2 ?: "") }
    var opt3 by remember { mutableStateOf(initial?.option3 ?: "") }
    var correctIdx by remember { mutableStateOf(initial?.correctIndex?.toString() ?: "0") }
    var expAm by remember { mutableStateOf(initial?.explanationAm ?: "") }
    var expEn by remember { mutableStateOf(initial?.explanationEn ?: "") }
    AdminFormDialog("ፈተና ${if (initial == null) "ጨምር" else "አርትዕ"}", onDismiss, {
        val idx = correctIdx.toIntOrNull()?.coerceIn(0, 3) ?: 0
        if (questionAm.isNotBlank() && opt0.isNotBlank())
            onSave(AdminQuiz(id = initial?.id ?: 0, questionAm = questionAm, questionEn = questionEn,
                option0 = opt0, option1 = opt1, option2 = opt2, option3 = opt3,
                correctIndex = idx, explanationAm = expAm, explanationEn = expEn))
    }) {
        AdminTextField("ጥያቄ (አማርኛ)*", questionAm, 2) { questionAm = it }
        AdminTextField("Question (English)", questionEn, 2) { questionEn = it }
        AdminTextField("አማራጭ 1*", opt0) { opt0 = it }
        AdminTextField("አማራጭ 2", opt1) { opt1 = it }
        AdminTextField("አማራጭ 3", opt2) { opt2 = it }
        AdminTextField("አማራጭ 4", opt3) { opt3 = it }
        AdminTextField("ትክክለኛ (0-3)*", correctIdx) { correctIdx = it }
        AdminTextField("ማብራሪያ (አማርኛ)", expAm, 2) { expAm = it }
        AdminTextField("Explanation (English)", expEn, 2) { expEn = it }
    }
}

// ── QUOTES ────────────────────────────────────────────────────────────────────

@Composable
fun QuotesAdminTab(viewModel: AppViewModel) {
    val items by viewModel.adminQuotes.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var editing by remember { mutableStateOf<AdminQuote?>(null) }

    if (showDialog) {
        AdminQuoteDialog(editing,
            onDismiss = { showDialog = false; editing = null },
            onSave = { item ->
                if (editing == null) viewModel.insertAdminQuote(item) else viewModel.updateAdminQuote(item)
                showDialog = false; editing = null
            })
    }
    AdminListScaffold(onAdd = { showDialog = true }) {
        if (items.isEmpty()) AdminEmptyState("ምንም ጥቅስ የለም")
        else LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(items) { q ->
                AdminItemCard(q.textAm, q.textEn, q.author,
                    onEdit = { editing = q; showDialog = true },
                    onDelete = { viewModel.deleteAdminQuote(q) })
            }
        }
    }
}

@Composable
private fun AdminQuoteDialog(initial: AdminQuote?, onDismiss: () -> Unit, onSave: (AdminQuote) -> Unit) {
    var textAm by remember { mutableStateOf(initial?.textAm ?: "") }
    var textEn by remember { mutableStateOf(initial?.textEn ?: "") }
    var author by remember { mutableStateOf(initial?.author ?: "") }
    AdminFormDialog("ጥቅስ ${if (initial == null) "ጨምር" else "አርትዕ"}", onDismiss, {
        if (textAm.isNotBlank())
            onSave(AdminQuote(id = initial?.id ?: 0, textAm = textAm, textEn = textEn, author = author))
    }) {
        AdminTextField("ጥቅስ (አማርኛ)*", textAm, 3) { textAm = it }
        AdminTextField("Quote (English)", textEn, 3) { textEn = it }
        AdminTextField("ደራሲ / Author", author) { author = it }
    }
}

// ── CREATOR SUBMISSIONS ───────────────────────────────────────────────────────

@Composable
fun SubmissionsAdminTab(viewModel: AppViewModel) {
    val submissions by viewModel.allSubmissions.collectAsState()

    if (submissions.isEmpty()) {
        AdminEmptyState("ምንም ስራ አልተላከም")
        return
    }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(submissions) { sub ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f)),
                border = BorderStroke(1.dp, when (sub.status) {
                    "approved" -> Color(0xFF4CAF50).copy(alpha = 0.6f)
                    "rejected" -> Color.Red.copy(alpha = 0.4f)
                    else -> EthiopianGold.copy(alpha = 0.3f)
                })
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(when(sub.category) { "poem"->"📜"; "teret"->"🦁"; "misale"->"💬"; else->"📖" }, fontSize = 20.sp)
                        Spacer(Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(sub.titleAm, color = EthiopianGold, fontWeight = FontWeight.Bold,
                                maxLines = 1, overflow = TextOverflow.Ellipsis)
                            Text("${sub.authorName} • ${sub.authorPhone}", color = Color.White.copy(0.5f), fontSize = 11.sp)
                        }
                        // Status badge
                        Surface(shape = RoundedCornerShape(8.dp), color = when (sub.status) {
                            "approved" -> Color(0xFF4CAF50).copy(0.3f)
                            "rejected" -> Color.Red.copy(0.3f)
                            else -> EthiopianGold.copy(0.2f)
                        }) {
                            Text(when (sub.status) { "approved"->"✓ ፈቀደ"; "rejected"->"✗ ተቀበለ"; else->"⏳ ይጠብቃል" },
                                color = Color.White, fontSize = 11.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(sub.contentAm, color = Color.White.copy(0.75f), maxLines = 3, overflow = TextOverflow.Ellipsis, fontSize = 13.sp)
                    if (sub.status == "pending") {
                        Spacer(Modifier.height(10.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = { viewModel.approveSubmission(sub) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.weight(1f)
                            ) { Text("✓ ፍቀድ", color = Color.White, fontWeight = FontWeight.Bold) }
                            OutlinedButton(
                                onClick = { viewModel.rejectSubmission(sub) },
                                border = BorderStroke(1.dp, Color.Red.copy(0.6f)),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.weight(1f)
                            ) { Text("✗ ተቀበለ", color = Color.Red) }
                        }
                    }
                }
            }
        }
    }
}
