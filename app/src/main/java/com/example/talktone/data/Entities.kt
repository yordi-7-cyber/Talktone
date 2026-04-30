package com.example.talktone.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val uri: String,
    val fileType: String, // "pdf" or "txt"
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
    val lastReadDate: String = "", // yyyy-MM-dd
    val longestStreak: Int = 0,
    val totalDaysRead: Int = 0
)
