package com.example.talktone.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        BookEntity::class,
        BookmarkEntity::class,
        ReadingStreakEntity::class,
        UserProfile::class,
        CreatorSubmission::class,
        ContentLike::class,
        AdminPoem::class,
        AdminTeret::class,
        AdminMisale::class,
        AdminQuiz::class,
        AdminQuote::class
    ],
    version = 5,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun bookmarkDao(): BookmarkDao
    abstract fun streakDao(): ReadingStreakDao
    abstract fun userProfileDao(): UserProfileDao
    abstract fun creatorSubmissionDao(): CreatorSubmissionDao
    abstract fun adminPoemDao(): AdminPoemDao
    abstract fun adminTeretDao(): AdminTeretDao
    abstract fun adminMisaleDao(): AdminMisaleDao
    abstract fun adminQuizDao(): AdminQuizDao
    abstract fun adminQuoteDao(): AdminQuoteDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, "biir_db"
                ).fallbackToDestructiveMigration().build().also { INSTANCE = it }
            }
    }
}
