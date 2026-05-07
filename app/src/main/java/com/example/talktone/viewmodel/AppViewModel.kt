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
    val isOnboarded: StateFlow<Boolean> = prefs.isOnboarded
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val userProfile: StateFlow<UserProfile?> = db.userProfileDao().getProfile()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val books: StateFlow<List<BookEntity>> = db.bookDao().getAllBooks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val streak: StateFlow<ReadingStreakEntity?> = db.streakDao().getStreak()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // Quiz
    private val _currentQuizIndex = MutableStateFlow(0)
    val currentQuizIndex: StateFlow<Int> = _currentQuizIndex
    private val _quizScore = MutableStateFlow(0)
    val quizScore: StateFlow<Int> = _quizScore
    private val _selectedAnswer = MutableStateFlow<Int?>(null)
    val selectedAnswer: StateFlow<Int?> = _selectedAnswer
    private val _quizFinished = MutableStateFlow(false)
    val quizFinished: StateFlow<Boolean> = _quizFinished
    private val _shuffledQuestions = MutableStateFlow(AmharicContent.quizQuestions.shuffled())
    val shuffledQuestions: StateFlow<List<QuizQuestion>> = _shuffledQuestions

    // Combine admin quizzes with default quizzes
    val allQuizQuestions: StateFlow<List<QuizQuestion>> = combine(
        flowOf(AmharicContent.quizQuestions),
        adminQuizzes
    ) { default, admin ->
        val combined = default.toMutableList()
        admin.forEach { q ->
            combined.add(QuizQuestion(
                id = 1000 + q.id,
                questionAm = q.questionAm,
                questionEn = q.questionEn,
                options = listOf(q.option0, q.option1, q.option2, q.option3),
                correctIndex = q.correctIndex,
                explanationAm = q.explanationAm,
                explanationEn = q.explanationEn
            ))
        }
        combined.shuffled()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AmharicContent.quizQuestions.shuffled())

    // Quotes
    private val _currentQuoteIndex = MutableStateFlow(0)
    val currentQuoteIndex: StateFlow<Int> = _currentQuoteIndex

    // Combine admin quotes with default quotes
    val allQuotes: StateFlow<List<QuoteItem>> = combine(
        flowOf(AmharicContent.quotes),
        adminQuotes
    ) { default, admin ->
        val combined = default.toMutableList()
        admin.forEach { q ->
            combined.add(QuoteItem(
                id = 1000 + q.id,
                textAm = q.textAm,
                textEn = q.textEn,
                author = q.author,
                backgroundGradient = listOf(0xFF1A0A2E, 0xFF0D0221)
            ))
        }
        combined
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AmharicContent.quotes)

    // Streak congrats
    private val _showCongrats = MutableStateFlow(false)
    val showCongrats: StateFlow<Boolean> = _showCongrats

    // Creator submissions
    val allSubmissions: StateFlow<List<CreatorSubmission>> = db.creatorSubmissionDao().getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val approvedSubmissions: StateFlow<List<CreatorSubmission>> = db.creatorSubmissionDao().getApproved()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun getMySubmissions(phone: String): Flow<List<CreatorSubmission>> =
        db.creatorSubmissionDao().getByAuthor(phone)

    // ── Prefs ──────────────────────────────────────────────────────────────────
    fun toggleDarkMode() = viewModelScope.launch { prefs.setDarkMode(!isDarkMode.value) }
    fun setLanguage(lang: String) = viewModelScope.launch { prefs.setLanguage(lang) }
    fun completeOnboarding() = viewModelScope.launch { prefs.setOnboarded(true) }

    // ── User Profile ───────────────────────────────────────────────────────────
    fun saveUserProfile(name: String, phone: String, level: String, role: String, profilePicUri: String = "") =
        viewModelScope.launch {
            db.userProfileDao().upsertProfile(
                UserProfile(name = name, phone = phone, level = level, role = role, profilePicUri = profilePicUri, isLoggedIn = true)
            )
            prefs.setOnboarded(true)
        }

    fun logout() = viewModelScope.launch {
        db.userProfileDao().upsertProfile(UserProfile(isLoggedIn = false))
        prefs.setOnboarded(false)
    }

    // ── Books ──────────────────────────────────────────────────────────────────
    fun addBook(title: String, uri: String, fileType: String) = viewModelScope.launch {
        db.bookDao().insertBook(BookEntity(title = title, uri = uri, fileType = fileType))
    }
    fun deleteBook(book: BookEntity) = viewModelScope.launch { db.bookDao().deleteBook(book) }
    fun updateLastPage(bookId: Int, page: Int) = viewModelScope.launch { db.bookDao().updateLastPage(bookId, page) }
    fun getBookmarksForBook(bookId: Int): Flow<List<BookmarkEntity>> = db.bookmarkDao().getBookmarksForBook(bookId)
    fun addBookmark(bookId: Int, page: Int, note: String = "") = viewModelScope.launch {
        db.bookmarkDao().insertBookmark(BookmarkEntity(bookId = bookId, page = page, note = note))
    }
    fun deleteBookmark(bookmark: BookmarkEntity) = viewModelScope.launch { db.bookmarkDao().deleteBookmark(bookmark) }

    // ── Streak with 60-min timer logic ─────────────────────────────────────────
    private var sessionStartMs: Long = 0L
    private val requiredMinutes = 60

    fun startReadingSession() {
        sessionStartMs = System.currentTimeMillis()
    }

    fun endReadingSession() = viewModelScope.launch {
        if (sessionStartMs == 0L) return@launch
        val elapsedMinutes = ((System.currentTimeMillis() - sessionStartMs) / 60000).toInt()
        if (elapsedMinutes < requiredMinutes) {
            sessionStartMs = 0L
            return@launch
        }
        val today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
        val current = streak.value ?: ReadingStreakEntity()
        if (current.lastReadDate == today) {
            val updated = current.copy(todayMinutes = current.todayMinutes + elapsedMinutes)
            db.streakDao().upsertStreak(updated)
            sessionStartMs = 0L
            return@launch
        }
        val yesterday = LocalDate.now().minusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE)
        val newStreak = if (current.lastReadDate == yesterday) current.currentStreak + 1 else 1
        val newLongest = maxOf(current.longestStreak, newStreak)
        if (newStreak == 7 || newStreak % 7 == 0) _showCongrats.value = true
        db.streakDao().upsertStreak(current.copy(
            currentStreak = newStreak, lastReadDate = today,
            longestStreak = newLongest, totalDaysRead = current.totalDaysRead + 1,
            todayMinutes = elapsedMinutes, sessionStart = sessionStartMs
        ))
        sessionStartMs = 0L
    }

    fun recordReadingToday() = viewModelScope.launch {
        val today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
        val current = streak.value ?: ReadingStreakEntity()
        if (current.lastReadDate == today) return@launch
        val yesterday = LocalDate.now().minusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE)
        val newStreak = if (current.lastReadDate == yesterday) current.currentStreak + 1 else 1
        val newLongest = maxOf(current.longestStreak, newStreak)
        if (newStreak == 7 || newStreak % 7 == 0) _showCongrats.value = true
        db.streakDao().upsertStreak(current.copy(
            currentStreak = newStreak, lastReadDate = today,
            longestStreak = newLongest, totalDaysRead = current.totalDaysRead + 1
        ))
    }
    fun dismissCongrats() { _showCongrats.value = false }

    // ── Quiz ───────────────────────────────────────────────────────────────────
    fun selectAnswer(index: Int) {
        if (_selectedAnswer.value != null) return
        _selectedAnswer.value = index
        if (index == _shuffledQuestions.value[_currentQuizIndex.value].correctIndex) _quizScore.value++
    }
    fun nextQuestion() {
        val next = _currentQuizIndex.value + 1
        if (next >= _shuffledQuestions.value.size) _quizFinished.value = true
        else { _currentQuizIndex.value = next; _selectedAnswer.value = null }
    }
    fun restartQuiz() {
        _currentQuizIndex.value = 0; _quizScore.value = 0
        _selectedAnswer.value = null; _quizFinished.value = false
    }

    // ── Quotes ─────────────────────────────────────────────────────────────────
    fun nextQuote() { _currentQuoteIndex.value = (_currentQuoteIndex.value + 1) % AmharicContent.quotes.size }
    fun prevQuote() { _currentQuoteIndex.value = (_currentQuoteIndex.value - 1 + AmharicContent.quotes.size) % AmharicContent.quotes.size }

    // ── Creator ────────────────────────────────────────────────────────────────
    fun submitWork(submission: CreatorSubmission) = viewModelScope.launch { db.creatorSubmissionDao().insert(submission) }
    fun approveSubmission(s: CreatorSubmission) = viewModelScope.launch {
        // Update submission status
        db.creatorSubmissionDao().update(s.copy(status = "approved"))
        
        // Auto-add to appropriate category table
        when (s.category.lowercase()) {
            "poem" -> db.adminPoemDao().insert(AdminPoem(
                titleAm = s.titleAm, titleEn = s.titleEn,
                contentAm = s.contentAm, contentEn = s.contentEn,
                author = s.authorName
            ))
            "teret" -> db.adminTeretDao().insert(AdminTeret(
                titleAm = s.titleAm, titleEn = s.titleEn,
                contentAm = s.contentAm, contentEn = s.contentEn,
                author = s.authorName
            ))
            "misale" -> db.adminMisaleDao().insert(AdminMisale(
                titleAm = s.titleAm, titleEn = s.titleEn,
                contentAm = s.contentAm, contentEn = s.contentEn,
                author = s.authorName
            ))
        }
    }
    fun rejectSubmission(s: CreatorSubmission) = viewModelScope.launch { db.creatorSubmissionDao().update(s.copy(status = "rejected")) }
    fun deleteSubmission(s: CreatorSubmission) = viewModelScope.launch { db.creatorSubmissionDao().delete(s) }
    fun likeSubmission(id: Int) = viewModelScope.launch { db.creatorSubmissionDao().incrementLike(id) }

    // ── Admin ──────────────────────────────────────────────────────────────────
    fun adminLogin(password: String): Boolean = password == "@Admin1996"

    val adminPoems: StateFlow<List<AdminPoem>> = db.adminPoemDao().getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val adminTerets: StateFlow<List<AdminTeret>> = db.adminTeretDao().getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val adminMisale: StateFlow<List<AdminMisale>> = db.adminMisaleDao().getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val adminQuizzes: StateFlow<List<AdminQuiz>> = db.adminQuizDao().getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val adminQuotes: StateFlow<List<AdminQuote>> = db.adminQuoteDao().getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun insertAdminPoem(i: AdminPoem) = viewModelScope.launch { db.adminPoemDao().insert(i) }
    fun updateAdminPoem(i: AdminPoem) = viewModelScope.launch { db.adminPoemDao().update(i) }
    fun deleteAdminPoem(i: AdminPoem) = viewModelScope.launch { db.adminPoemDao().delete(i) }
    fun insertAdminTeret(i: AdminTeret) = viewModelScope.launch { db.adminTeretDao().insert(i) }
    fun updateAdminTeret(i: AdminTeret) = viewModelScope.launch { db.adminTeretDao().update(i) }
    fun deleteAdminTeret(i: AdminTeret) = viewModelScope.launch { db.adminTeretDao().delete(i) }
    fun insertAdminMisale(i: AdminMisale) = viewModelScope.launch { db.adminMisaleDao().insert(i) }
    fun updateAdminMisale(i: AdminMisale) = viewModelScope.launch { db.adminMisaleDao().update(i) }
    fun deleteAdminMisale(i: AdminMisale) = viewModelScope.launch { db.adminMisaleDao().delete(i) }
    fun insertAdminQuiz(i: AdminQuiz) = viewModelScope.launch { db.adminQuizDao().insert(i) }
    fun updateAdminQuiz(i: AdminQuiz) = viewModelScope.launch { db.adminQuizDao().update(i) }
    fun deleteAdminQuiz(i: AdminQuiz) = viewModelScope.launch { db.adminQuizDao().delete(i) }
    fun insertAdminQuote(i: AdminQuote) = viewModelScope.launch { db.adminQuoteDao().insert(i) }
    fun updateAdminQuote(i: AdminQuote) = viewModelScope.launch { db.adminQuoteDao().update(i) }
    fun deleteAdminQuote(i: AdminQuote) = viewModelScope.launch { db.adminQuoteDao().delete(i) }
}
