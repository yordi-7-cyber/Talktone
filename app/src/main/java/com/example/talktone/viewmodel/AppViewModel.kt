package com.example.talktone.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.talktone.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val prefs = PreferencesManager(application)

    val isDarkMode: StateFlow<Boolean> = prefs.isDarkMode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val language: StateFlow<String> = prefs.language
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "am")

    val books: StateFlow<List<BookEntity>> = db.bookDao().getAllBooks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val streak: StateFlow<ReadingStreakEntity?> = db.streakDao().getStreak()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // Quiz state
    private val _currentQuizIndex = MutableStateFlow(0)
    val currentQuizIndex: StateFlow<Int> = _currentQuizIndex

    private val _quizScore = MutableStateFlow(0)
    val quizScore: StateFlow<Int> = _quizScore

    private val _selectedAnswer = MutableStateFlow<Int?>( null)
    val selectedAnswer: StateFlow<Int?> = _selectedAnswer

    private val _quizFinished = MutableStateFlow(false)
    val quizFinished: StateFlow<Boolean> = _quizFinished

    private val _shuffledQuestions = MutableStateFlow(AmharicContent.quizQuestions.shuffled())
    val shuffledQuestions: StateFlow<List<QuizQuestion>> = _shuffledQuestions

    // Quote state
    private val _currentQuoteIndex = MutableStateFlow(0)
    val currentQuoteIndex: StateFlow<Int> = _currentQuoteIndex

    // Streak congrats
    private val _showCongrats = MutableStateFlow(false)
    val showCongrats: StateFlow<Boolean> = _showCongrats

    fun toggleDarkMode() {
        viewModelScope.launch {
            prefs.setDarkMode(!isDarkMode.value)
        }
    }

    fun setLanguage(lang: String) {
        viewModelScope.launch {
            prefs.setLanguage(lang)
        }
    }

    fun addBook(title: String, uri: String, fileType: String) {
        viewModelScope.launch {
            db.bookDao().insertBook(BookEntity(title = title, uri = uri, fileType = fileType))
        }
    }

    fun deleteBook(book: BookEntity) {
        viewModelScope.launch {
            db.bookDao().deleteBook(book)
        }
    }

    fun updateLastPage(bookId: Int, page: Int) {
        viewModelScope.launch {
            db.bookDao().updateLastPage(bookId, page)
        }
    }

    fun getBookmarksForBook(bookId: Int): Flow<List<BookmarkEntity>> =
        db.bookmarkDao().getBookmarksForBook(bookId)

    fun addBookmark(bookId: Int, page: Int, note: String = "") {
        viewModelScope.launch {
            db.bookmarkDao().insertBookmark(BookmarkEntity(bookId = bookId, page = page, note = note))
        }
    }

    fun deleteBookmark(bookmark: BookmarkEntity) {
        viewModelScope.launch {
            db.bookmarkDao().deleteBookmark(bookmark)
        }
    }

    fun recordReadingToday() {
        viewModelScope.launch {
            val today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
            val current = streak.value ?: ReadingStreakEntity()
            if (current.lastReadDate == today) return@launch

            val yesterday = LocalDate.now().minusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE)
            val newStreak = if (current.lastReadDate == yesterday) current.currentStreak + 1 else 1
            val newLongest = maxOf(current.longestStreak, newStreak)

            if (newStreak == 7 || newStreak % 7 == 0) {
                _showCongrats.value = true
            }

            db.streakDao().upsertStreak(
                current.copy(
                    currentStreak = newStreak,
                    lastReadDate = today,
                    longestStreak = newLongest,
                    totalDaysRead = current.totalDaysRead + 1
                )
            )
        }
    }

    fun dismissCongrats() { _showCongrats.value = false }

    // Quiz functions
    fun selectAnswer(index: Int) {
        if (_selectedAnswer.value != null) return
        _selectedAnswer.value = index
        val question = _shuffledQuestions.value[_currentQuizIndex.value]
        if (index == question.correctIndex) {
            _quizScore.value++
        }
    }

    fun nextQuestion() {
        val next = _currentQuizIndex.value + 1
        if (next >= _shuffledQuestions.value.size) {
            _quizFinished.value = true
        } else {
            _currentQuizIndex.value = next
            _selectedAnswer.value = null
        }
    }

    fun restartQuiz() {
        _shuffledQuestions.value = AmharicContent.quizQuestions.shuffled()
        _currentQuizIndex.value = 0
        _quizScore.value = 0
        _selectedAnswer.value = null
        _quizFinished.value = false
    }

    fun nextQuote() {
        _currentQuoteIndex.value = (_currentQuoteIndex.value + 1) % AmharicContent.quotes.size
    }

    fun prevQuote() {
        val size = AmharicContent.quotes.size
        _currentQuoteIndex.value = (_currentQuoteIndex.value - 1 + size) % size
    }
}
