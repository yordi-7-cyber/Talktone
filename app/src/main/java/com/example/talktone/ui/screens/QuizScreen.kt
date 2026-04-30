package com.example.talktone.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.talktone.data.QuizQuestion
import com.example.talktone.ui.theme.EthiopianGold
import com.example.talktone.ui.theme.EthiopianGreen
import com.example.talktone.ui.theme.EthiopianRed
import com.example.talktone.viewmodel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    viewModel: AppViewModel,
    language: String,
    isDark: Boolean,
    onBack: () -> Unit
) {
    val questions by viewModel.shuffledQuestions.collectAsState()
    val currentIndex by viewModel.currentQuizIndex.collectAsState()
    val score by viewModel.quizScore.collectAsState()
    val selectedAnswer by viewModel.selectedAnswer.collectAsState()
    val quizFinished by viewModel.quizFinished.collectAsState()

    val bgColors = if (isDark)
        listOf(Color(0xFF1A0A2E), Color(0xFF0D1B2A))
    else
        listOf(Color(0xFF8B0000), Color(0xFFDC143C))

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
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Text(
                    text = if (language == "am") "🧠 ፈተና" else "🧠 Quiz",
                    style = MaterialTheme.typography.headlineMedium,
                    color = EthiopianGold,
                    fontWeight = FontWeight.Bold
                )
            }

            if (quizFinished) {
                QuizResultScreen(
                    score = score,
                    total = questions.size,
                    language = language,
                    onRestart = { viewModel.restartQuiz() },
                    onBack = onBack
                )
            } else if (questions.isNotEmpty()) {
                QuizQuestionView(
                    question = questions[currentIndex],
                    questionNumber = currentIndex + 1,
                    total = questions.size,
                    score = score,
                    selectedAnswer = selectedAnswer,
                    language = language,
                    onSelectAnswer = { viewModel.selectAnswer(it) },
                    onNext = { viewModel.nextQuestion() }
                )
            }
        }
    }
}

@Composable
fun QuizQuestionView(
    question: QuizQuestion,
    questionNumber: Int,
    total: Int,
    score: Int,
    selectedAnswer: Int?,
    language: String,
    onSelectAnswer: (Int) -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        // Progress
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$questionNumber / $total",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White.copy(alpha = 0.8f)
            )
            Text(
                text = if (language == "am") "ነጥብ: $score" else "Score: $score",
                style = MaterialTheme.typography.titleMedium,
                color = EthiopianGold,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = questionNumber.toFloat() / total,
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = EthiopianGold,
            trackColor = Color.White.copy(alpha = 0.2f)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Question card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f)),
            border = BorderStroke(1.dp, EthiopianGold.copy(alpha = 0.4f))
        ) {
            Text(
                text = if (language == "am") question.questionAm else question.questionEn,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(20.dp),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Options
        question.options.forEachIndexed { index, option ->
            val isSelected = selectedAnswer == index
            val isCorrect = index == question.correctIndex
            val showResult = selectedAnswer != null

            val bgColor = when {
                showResult && isCorrect -> EthiopianGreen.copy(alpha = 0.8f)
                showResult && isSelected && !isCorrect -> EthiopianRed.copy(alpha = 0.8f)
                isSelected -> EthiopianGold.copy(alpha = 0.3f)
                else -> Color.White.copy(alpha = 0.1f)
            }

            val borderColor = when {
                showResult && isCorrect -> EthiopianGreen
                showResult && isSelected && !isCorrect -> EthiopianRed
                else -> Color.White.copy(alpha = 0.3f)
            }

            Card(
                onClick = { if (selectedAnswer == null) onSelectAnswer(index) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = bgColor),
                border = BorderStroke(1.5.dp, borderColor)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(Color.White.copy(alpha = 0.2f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        if (showResult && isCorrect) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                        } else if (showResult && isSelected && !isCorrect) {
                            Icon(Icons.Default.Close, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                        } else {
                            Text(
                                text = ('A' + index).toString(),
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = option,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                        fontWeight = if (isSelected || (showResult && isCorrect)) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }

        // Explanation
        if (selectedAnswer != null) {
            Spacer(modifier = Modifier.height(16.dp))
            val explanation = if (language == "am") question.explanationAm else question.explanationEn
            if (explanation.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.08f))
                ) {
                    Text(
                        text = "💡 $explanation",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.85f),
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = onNext,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = EthiopianGold)
            ) {
                Text(
                    text = if (language == "am") "ቀጣይ ጥያቄ →" else "Next Question →",
                    color = Color(0xFF1A0A2E),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun QuizResultScreen(
    score: Int,
    total: Int,
    language: String,
    onRestart: () -> Unit,
    onBack: () -> Unit
) {
    val percentage = (score.toFloat() / total * 100).toInt()
    val emoji = when {
        percentage >= 90 -> "🏆"
        percentage >= 70 -> "🌟"
        percentage >= 50 -> "👍"
        else -> "📚"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = emoji, fontSize = 80.sp)
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = if (language == "am") "ፈተናው ተጠናቀቀ!" else "Quiz Complete!",
            style = MaterialTheme.typography.headlineLarge,
            color = EthiopianGold,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "$score / $total",
            style = MaterialTheme.typography.displayLarge,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "$percentage%",
            style = MaterialTheme.typography.headlineMedium,
            color = EthiopianGold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = when {
                percentage >= 90 -> if (language == "am") "አስደናቂ! ብሩህ ተማሪ ነህ! 🎉" else "Amazing! You're brilliant! 🎉"
                percentage >= 70 -> if (language == "am") "በጣም ጥሩ! ቀጥል! 🌟" else "Very good! Keep it up! 🌟"
                percentage >= 50 -> if (language == "am") "ጥሩ ሙከራ! ተጨማሪ አጥና! 👍" else "Good try! Study more! 👍"
                else -> if (language == "am") "ተጨማሪ አጥና! ትሳካልሃል! 📚" else "Study more! You'll get it! 📚"
            },
            style = MaterialTheme.typography.titleLarge,
            color = Color.White.copy(alpha = 0.9f),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = onRestart,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = EthiopianGold)
        ) {
            Text(
                text = if (language == "am") "እንደገና ሞክር" else "Try Again",
                color = Color(0xFF1A0A2E),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f))
        ) {
            Text(
                text = if (language == "am") "ወደ ቤት ተመለስ" else "Back to Home",
                color = Color.White,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}
