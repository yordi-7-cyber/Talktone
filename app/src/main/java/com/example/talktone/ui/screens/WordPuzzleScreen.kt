package com.example.talktone.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.talktone.ui.theme.EthiopianGold
import com.example.talktone.ui.theme.ImageBackground
import com.example.talktone.viewmodel.AppViewModel
import com.example.talktone.R
import kotlinx.coroutines.launch

data class PuzzleWord(val amharic: String, val english: String, val hint: String)

val puzzleWords = listOf(
    PuzzleWord("ሰላም", "Peace", "Common greeting"),
    PuzzleWord("ፍቅር", "Love", "Strong emotion"),
    PuzzleWord("ቤት", "House", "Where you live"),
    PuzzleWord("ውሃ", "Water", "You drink it"),
    PuzzleWord("እናት", "Mother", "Female parent"),
    PuzzleWord("አባት", "Father", "Male parent"),
    PuzzleWord("ልጅ", "Child", "Young person"),
    PuzzleWord("መጽሐፍ", "Book", "You read it"),
    PuzzleWord("ሰው", "Person", "Human being"),
    PuzzleWord("ጊዜ", "Time", "It passes")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordPuzzleScreen(viewModel: AppViewModel, language: String, onBack: () -> Unit) {
    var currentPuzzleIndex by remember { mutableStateOf(0) }
    var selectedLetters by remember { mutableStateOf(listOf<String>()) }
    var score by remember { mutableStateOf(0) }
    var showHint by remember { mutableStateOf(false) }
    var isCorrect by remember { mutableStateOf<Boolean?>(null) }
    val scope = rememberCoroutineScope()

    val currentWord = puzzleWords.getOrNull(currentPuzzleIndex) ?: puzzleWords.first()
    val letters = currentWord.amharic.toList().shuffled() + 
        listOf("ሀ", "ለ", "ሐ", "መ", "ሠ", "ረ", "ሰ", "ሸ", "ቀ", "በ").shuffled().take(3)
    val allLetters = letters.shuffled()

    ImageBackground(R.drawable.candles, true, 0.75f) {
        Column(Modifier.fillMaxSize().padding(16.dp)) {
            // Top bar
            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                IconButton(onBack) { Icon(Icons.Default.ArrowBack, null, tint = EthiopianGold) }
                Text("🧩 የቃላት ጦርነት", color = EthiopianGold, fontWeight = FontWeight.Bold, fontSize = 22.sp)
                Text("⭐ $score", color = EthiopianGold, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }

            Spacer(Modifier.height(20.dp))

            // Instruction card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Black.copy(0.4f)),
                border = androidx.compose.foundation.BorderStroke(1.dp, EthiopianGold.copy(0.4f))
            ) {
                Column(Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(if (language == "am") "ቃሉን አግኝ!" else "Find the Word!", 
                        color = EthiopianGold, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                    Spacer(Modifier.height(8.dp))
                    Text(currentWord.english, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Medium)
                    Text("(${currentWord.hint})", color = Color.White.copy(0.6f), fontSize = 14.sp)
                }
            }

            Spacer(Modifier.height(20.dp))

            // Selected letters display
            Card(
                modifier = Modifier.fillMaxWidth().height(80.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Black.copy(0.5f)),
                border = androidx.compose.foundation.BorderStroke(2.dp, 
                    when (isCorrect) {
                        true -> Color.Green
                        false -> Color.Red
                        null -> EthiopianGold
                    }
                )
            ) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        selectedLetters.joinToString(" "),
                        color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // Hint button
            if (showHint) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = EthiopianGold.copy(0.2f))
                ) {
                    Text("💡 ${currentWord.amharic.first()}...", 
                        Modifier.padding(12.dp), color = EthiopianGold, fontSize = 16.sp)
                }
            } else {
                OutlinedButton(
                    onClick = { showHint = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = EthiopianGold)
                ) {
                    Icon(Icons.Default.Lightbulb, null)
                    Spacer(Modifier.width(8.dp))
                    Text(if (language == "am") "ጠቃሚ ምክር" else "Show Hint")
                }
            }

            Spacer(Modifier.height(20.dp))

            // Letter grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier.fillMaxWidth().weight(1f),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(allLetters) { letter ->
                    val isSelected = selectedLetters.contains(letter.toString())
                    LetterTile(
                        letter = letter.toString(),
                        isSelected = isSelected,
                        onClick = {
                            if (isSelected) {
                                selectedLetters = selectedLetters.filter { it != letter.toString() }
                            } else {
                                selectedLetters = selectedLetters + letter.toString()
                            }
                        }
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // Action buttons
            Row(Modifier.fillMaxWidth(), Arrangement.spacedBy(12.dp)) {
                OutlinedButton(
                    onClick = { selectedLetters = emptyList(); isCorrect = null },
                    modifier = Modifier.weight(1f).height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                ) {
                    Icon(Icons.Default.Refresh, null)
                    Spacer(Modifier.width(6.dp))
                    Text(if (language == "am") "አጽዳ" else "Clear")
                }

                Button(
                    onClick = {
                        val answer = selectedLetters.joinToString("")
                        if (answer == currentWord.amharic) {
                            isCorrect = true
                            score += 10
                        } else {
                            isCorrect = false
                        }
                    },
                    modifier = Modifier.weight(1f).height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = EthiopianGold)
                ) {
                    Icon(Icons.Default.Check, null)
                    Spacer(Modifier.width(6.dp))
                    Text(if (language == "am") "አረጋግጥ" else "Check", color = Color(0xFF1A0A2E), fontWeight = FontWeight.Bold)
                }
            }

            // Next button if correct
            if (isCorrect == true) {
                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = {
                        currentPuzzleIndex = (currentPuzzleIndex + 1) % puzzleWords.size
                        selectedLetters = emptyList()
                        isCorrect = null
                        showHint = false
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                ) {
                    Icon(Icons.Default.ArrowForward, null)
                    Spacer(Modifier.width(8.dp))
                    Text(if (language == "am") "ቀጣይ ፈተና →" else "Next Puzzle →", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun LetterTile(letter: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (isSelected) EthiopianGold.copy(0.3f) else Color.Black.copy(0.4f)
            )
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) EthiopianGold else Color.White.copy(0.3f),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(letter, color = if (isSelected) EthiopianGold else Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
    }
}
