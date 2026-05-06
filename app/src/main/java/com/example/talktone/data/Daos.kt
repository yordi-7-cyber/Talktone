package com.example.talktone.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query("SELECT * FROM books ORDER BY addedAt DESC")
    fun getAllBooks(): Flow<List<BookEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: BookEntity): Long

    @Delete
    suspend fun deleteBook(book: BookEntity)

    @Query("UPDATE books SET lastReadPage = :page WHERE id = :bookId")
    suspend fun updateLastPage(bookId: Int, page: Int)

    @Query("SELECT * FROM books WHERE id = :id")
    suspend fun getBookById(id: Int): BookEntity?
}

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmarks WHERE bookId = :bookId ORDER BY page ASC")
    fun getBookmarksForBook(bookId: Int): Flow<List<BookmarkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity)

    @Delete
    suspend fun deleteBookmark(bookmark: BookmarkEntity)
}

@Dao
interface ReadingStreakDao {
    @Query("SELECT * FROM reading_streak WHERE id = 1")
    fun getStreak(): Flow<ReadingStreakEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertStreak(streak: ReadingStreakEntity)
}

@Dao
interface UserProfileDao {
    @Query("SELECT * FROM user_profile WHERE id = 1")
    fun getProfile(): Flow<UserProfile?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProfile(profile: UserProfile)
}

@Dao
interface CreatorSubmissionDao {
    @Query("SELECT * FROM creator_submissions ORDER BY submittedAt DESC")
    fun getAll(): Flow<List<CreatorSubmission>>

    @Query("SELECT * FROM creator_submissions WHERE status = 'approved' ORDER BY submittedAt DESC")
    fun getApproved(): Flow<List<CreatorSubmission>>

    @Query("SELECT * FROM creator_submissions WHERE authorPhone = :phone ORDER BY submittedAt DESC")
    fun getByAuthor(phone: String): Flow<List<CreatorSubmission>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(submission: CreatorSubmission)

    @Update
    suspend fun update(submission: CreatorSubmission)

    @Delete
    suspend fun delete(submission: CreatorSubmission)

    @Query("UPDATE creator_submissions SET likes = likes + 1 WHERE id = :id")
    suspend fun incrementLike(id: Int)
}
