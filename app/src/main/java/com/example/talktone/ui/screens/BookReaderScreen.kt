package com.example.talktone.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.talktone.data.BookEntity
import com.example.talktone.R
import com.example.talktone.ui.theme.EthiopianGold
import com.example.talktone.ui.theme.ImageBackground
import com.example.talktone.viewmodel.AppViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookReaderScreen(
    viewModel: AppViewModel,
    language: String,
    isDark: Boolean,
    onBack: () -> Unit,
    onOpenBook: (Int) -> Unit
) {
    val books by viewModel.books.collectAsState()
    val context = LocalContext.current
    var showDeleteDialog by remember { mutableStateOf<BookEntity?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            context.contentResolver.takePersistableUriPermission(
                it, android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            val fileName = getFileName(context, it) ?: "Unknown Book"
            val ext = fileName.substringAfterLast('.', "txt").lowercase()
            viewModel.addBook(
                title = fileName.substringBeforeLast('.'),
                uri = it.toString(),
                fileType = ext
            )
        }
    }

    ImageBackground(resId = R.drawable.novel, isDark = isDark, overlayAlpha = if (isDark) 0.75f else 0.6f) {
        showDeleteDialog?.let { book ->
        AlertDialog(
            onDismissRequest = { showDeleteDialog = null },
            containerColor = Color(0xFF1A0A2E),
            title = {
                Text(
                    if (language == "am") "መጽሐፍ ሰርዝ" else "Delete Book",
                    color = EthiopianGold
                )
            },
            text = {
                Text(
                    if (language == "am") "\"${book.title}\" ሊሰረዝ ነው። እርግጠኛ ነህ?"
                    else "Delete \"${book.title}\"? Are you sure?",
                    color = Color.White
                )
            },
            confirmButton = {
                Button(
                    onClick = { viewModel.deleteBook(book); showDeleteDialog = null },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) { Text(if (language == "am") "ሰርዝ" else "Delete") }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = null }) {
                    Text(if (language == "am") "ሰርዝ" else "Cancel", color = Color.White)
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                    Text(
                        text = "📚 ${if (language == "am") "መጽሐፍ አንባቢ" else "Book Reader"}",
                        style = MaterialTheme.typography.headlineMedium,
                        color = EthiopianGold,
                        fontWeight = FontWeight.Bold
                    )
                }
                FloatingActionButton(
                    onClick = { launcher.launch(arrayOf("application/pdf", "text/plain", "text/html")) },
                    containerColor = EthiopianGold,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Book", tint = Color(0xFF1A0A2E))
                }
            }

            if (books.isEmpty()) {
                EmptyBooksView(language = language, onAdd = {
                    launcher.launch(arrayOf("application/pdf", "text/plain", "text/html"))
                })
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(books) { book ->
                        BookCard(
                            book = book,
                            language = language,
                            onClick = { onOpenBook(book.id) },
                            onDelete = { showDeleteDialog = book }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BookCard(book: BookEntity, language: String, onClick: () -> Unit, onDelete: () -> Unit) {
    val progress = if (book.totalPages > 0) book.lastReadPage.toFloat() / book.totalPages else 0f
    val dateStr = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        .format(Date(book.addedAt))

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f)),
        border = BorderStroke(1.dp, EthiopianGold.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Book icon
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        Brush.linearGradient(listOf(Color(0xFF4A0E8F), Color(0xFF9B59B6))),
                        RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (book.fileType == "pdf") "📄" else "📝",
                    fontSize = 28.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = EthiopianGold,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = book.fileType.uppercase(),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.5f)
                )
                Text(
                    text = dateStr,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.4f)
                )
                if (book.totalPages > 0) {
                    Spacer(modifier = Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = progress,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp),
                        color = EthiopianGold,
                        trackColor = Color.White.copy(alpha = 0.2f)
                    )
                    Text(
                        text = "${book.lastReadPage}/${book.totalPages} ${if (language == "am") "ገጽ" else "pages"}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.5f)
                    )
                }
            }

            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red.copy(alpha = 0.7f))
            }
        }
    }
}

@Composable
fun EmptyBooksView(language: String, onAdd: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "📚", fontSize = 80.sp)
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = if (language == "am") "ምንም መጽሐፍ የለም" else "No books yet",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = if (language == "am") "PDF ወይም TXT ፋይሎችን ጨምር"
            else "Add PDF or TXT files to start reading",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White.copy(alpha = 0.7f),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onAdd,
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = EthiopianGold)
        ) {
            Icon(Icons.Default.Add, contentDescription = null, tint = Color(0xFF1A0A2E))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                if (language == "am") "መጽሐፍ ጨምር" else "Add Book",
                color = Color(0xFF1A0A2E),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

fun getFileName(context: android.content.Context, uri: Uri): String? {
    var result: String? = null
    if (uri.scheme == "content") {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val idx = it.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
                if (idx >= 0) result = it.getString(idx)
            }
        }
    }
    if (result == null) {
        result = uri.path
        val cut = result?.lastIndexOf('/')
        if (cut != null && cut != -1) result = result?.substring(cut + 1)
    }
    return result
}
