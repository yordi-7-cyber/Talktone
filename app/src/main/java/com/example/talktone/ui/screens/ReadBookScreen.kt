package com.example.talktone.ui.screens

import android.net.Uri
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.talktone.data.BookEntity
import com.example.talktone.data.BookmarkEntity
import com.example.talktone.ui.theme.EthiopianGold
import com.example.talktone.viewmodel.AppViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadBookScreen(
    bookId: Int,
    viewModel: AppViewModel,
    language: String,
    isDark: Boolean,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val books by viewModel.books.collectAsState()
    val book = books.find { it.id == bookId }
    val bookmarks by viewModel.getBookmarksForBook(bookId).collectAsState(initial = emptyList())

    var textContent by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }
    var fontSize by remember { mutableStateOf(16f) }
    var showBookmarks by remember { mutableStateOf(false) }
    var showBookmarkDialog by remember { mutableStateOf(false) }
    var bookmarkNote by remember { mutableStateOf("") }

    LaunchedEffect(book) {
        book?.let {
            isLoading = true
            textContent = withContext(Dispatchers.IO) {
                try {
                    if (it.fileType == "txt" || it.fileType == "html") {
                        context.contentResolver.openInputStream(Uri.parse(it.uri))
                            ?.bufferedReader()?.readText() ?: "ፋይሉ ሊነበብ አልቻለም"
                    } else {
                        "PDF ፋይሎችን ለማንበብ ውጫዊ PDF አንባቢ ይጠቀሙ።\n\nTo read PDF files, please use an external PDF reader.\n\nFile: ${it.title}"
                    }
                } catch (e: Exception) {
                    "ፋይሉ ሊነበብ አልቻለም: ${e.message}"
                }
            }
            isLoading = false
            viewModel.recordReadingToday()
        }
    }

    val bgColors = if (isDark)
        listOf(Color(0xFF0D0D1A), Color(0xFF1A1A2E))
    else
        listOf(Color(0xFFFFF8F0), Color(0xFFF5E6D3))

    val textColor = if (isDark) Color(0xFFE8DCC8) else Color(0xFF2A1A0E)
    val surfaceColor = if (isDark) Color(0xFF1A1A2E) else Color(0xFFFFFFFF)

    if (showBookmarkDialog) {
        AlertDialog(
            onDismissRequest = { showBookmarkDialog = false },
            containerColor = if (isDark) Color(0xFF1A0A2E) else Color.White,
            title = {
                Text(
                    if (language == "am") "📌 ቦታ ምልክት ጨምር" else "📌 Add Bookmark",
                    color = EthiopianGold
                )
            },
            text = {
                OutlinedTextField(
                    value = bookmarkNote,
                    onValueChange = { bookmarkNote = it },
                    label = { Text(if (language == "am") "ማስታወሻ (አማራጭ)" else "Note (optional)") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = textColor,
                        unfocusedTextColor = textColor
                    )
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.addBookmark(bookId, 0, bookmarkNote)
                        bookmarkNote = ""
                        showBookmarkDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = EthiopianGold)
                ) { Text(if (language == "am") "ጨምር" else "Add", color = Color(0xFF1A0A2E)) }
            },
            dismissButton = {
                TextButton(onClick = { showBookmarkDialog = false }) {
                    Text(if (language == "am") "ሰርዝ" else "Cancel")
                }
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(bgColors))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back",
                        tint = if (isDark) Color.White else Color(0xFF1A0A2E))
                }
                Text(
                    text = book?.title ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    color = EthiopianGold,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Row {
                    IconButton(onClick = { showBookmarks = !showBookmarks }) {
                        Icon(
                            Icons.Default.Bookmarks,
                            contentDescription = "Bookmarks",
                            tint = if (showBookmarks) EthiopianGold else (if (isDark) Color.White else Color(0xFF1A0A2E))
                        )
                    }
                    IconButton(onClick = { showBookmarkDialog = true }) {
                        Icon(Icons.Default.BookmarkAdd, contentDescription = "Add Bookmark",
                            tint = EthiopianGold)
                    }
                }
            }

            // Font size controls
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (language == "am") "ፊደል:" else "Font:",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isDark) Color.White.copy(alpha = 0.6f) else Color(0xFF1A0A2E).copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = { if (fontSize > 12f) fontSize -= 2f },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(Icons.Default.Remove, contentDescription = "Decrease font",
                        tint = EthiopianGold, modifier = Modifier.size(18.dp))
                }
                Text(
                    text = "${fontSize.toInt()}sp",
                    style = MaterialTheme.typography.bodySmall,
                    color = EthiopianGold
                )
                IconButton(
                    onClick = { if (fontSize < 28f) fontSize += 2f },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Increase font",
                        tint = EthiopianGold, modifier = Modifier.size(18.dp))
                }
            }

            if (showBookmarks) {
                BookmarkPanel(
                    bookmarks = bookmarks,
                    language = language,
                    isDark = isDark,
                    onDelete = { viewModel.deleteBookmark(it) },
                    onClose = { showBookmarks = false }
                )
            } else {
                if (isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = EthiopianGold)
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 20.dp, vertical = 16.dp)
                    ) {
                        Text(
                            text = textContent,
                            fontSize = fontSize.sp,
                            color = textColor,
                            lineHeight = (fontSize * 1.7f).sp
                        )
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun BookmarkPanel(
    bookmarks: List<BookmarkEntity>,
    language: String,
    isDark: Boolean,
    onDelete: (BookmarkEntity) -> Unit,
    onClose: () -> Unit
) {
    val bgColor = if (isDark) Color(0xFF1A1A2E) else Color(0xFFF5E6D3)
    val textColor = if (isDark) Color.White else Color(0xFF1A0A2E)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
            .background(bgColor)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "📌 ${if (language == "am") "ቦታ ምልክቶች" else "Bookmarks"}",
                style = MaterialTheme.typography.titleLarge,
                color = EthiopianGold,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = onClose) {
                Icon(Icons.Default.Close, contentDescription = "Close", tint = textColor)
            }
        }

        if (bookmarks.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (language == "am") "ምንም ቦታ ምልክት የለም" else "No bookmarks yet",
                    color = textColor.copy(alpha = 0.6f)
                )
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(bookmarks) { bookmark ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = EthiopianGold.copy(alpha = 0.15f)
                        ),
                        border = BorderStroke(1.dp, EthiopianGold.copy(alpha = 0.4f))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .background(EthiopianGold, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("📌", fontSize = 18.sp)
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    if (bookmark.note.isNotEmpty()) {
                                        Text(
                                            text = bookmark.note,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = textColor,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                    Text(
                                        text = java.text.SimpleDateFormat("MMM dd, HH:mm", java.util.Locale.getDefault())
                                            .format(java.util.Date(bookmark.createdAt)),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = textColor.copy(alpha = 0.5f)
                                    )
                                }
                            }
                            IconButton(onClick = { onDelete(bookmark) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete",
                                    tint = Color.Red.copy(alpha = 0.7f), modifier = Modifier.size(20.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}
