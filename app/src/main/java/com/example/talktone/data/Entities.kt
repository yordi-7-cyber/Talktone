package com.example.talktone.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val uri: String,
    val fileType: String,
    val addedAt: Long = System.currentTimeMillis(),
    val lastReadPage: Int = 0,
    val totalPages: Int = 0
)

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val bookId: Int,
    val page: Int,
    val note: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "reading_streak")
data class ReadingStreakEntity(
    @PrimaryKey val id: Int = 1,
    val currentStreak: Int = 0,
    val lastReadDate: String = "",
    val longestStreak: Int = 0,
    val totalDaysRead: Int = 0
)

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey val id: Int = 1,
    val name: String = "",
    val phone: String = "",
    val level: String = "beginner", // beginner, intermediate, advanced
    val role: String = "reader",    // reader, creator
    val isLoggedIn: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "creator_submissions")
data class CreatorSubmission(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val authorName: String,
    val authorPhone: String,
    val category: String, // poem, teret, misale, novel
    val titleAm: String,
    val titleEn: String = "",
    val contentAm: String,
    val contentEn: String = "",
    val status: String = "pending", // pending, approved, rejected
    val likes: Int = 0,
    val submittedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "content_likes")
data class ContentLike(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val submissionId: Int,
    val likedAt: Long = System.currentTimeMillis()
)
