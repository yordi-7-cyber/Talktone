package com.example.talktone.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AdminPoemDao {
    @Query("SELECT * FROM admin_poems ORDER BY createdAt DESC")
    fun getAll(): Flow<List<AdminPoem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: AdminPoem)

    @Delete
    suspend fun delete(item: AdminPoem)

    @Update
    suspend fun update(item: AdminPoem)
}

@Dao
interface AdminTeretDao {
    @Query("SELECT * FROM admin_terets ORDER BY createdAt DESC")
    fun getAll(): Flow<List<AdminTeret>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: AdminTeret)

    @Delete
    suspend fun delete(item: AdminTeret)

    @Update
    suspend fun update(item: AdminTeret)
}

@Dao
interface AdminMisaleDao {
    @Query("SELECT * FROM admin_misale ORDER BY createdAt DESC")
    fun getAll(): Flow<List<AdminMisale>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: AdminMisale)

    @Delete
    suspend fun delete(item: AdminMisale)

    @Update
    suspend fun update(item: AdminMisale)
}

@Dao
interface AdminQuizDao {
    @Query("SELECT * FROM admin_quiz ORDER BY createdAt DESC")
    fun getAll(): Flow<List<AdminQuiz>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: AdminQuiz)

    @Delete
    suspend fun delete(item: AdminQuiz)

    @Update
    suspend fun update(item: AdminQuiz)
}

@Dao
interface AdminQuoteDao {
    @Query("SELECT * FROM admin_quotes ORDER BY createdAt DESC")
    fun getAll(): Flow<List<AdminQuote>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: AdminQuote)

    @Delete
    suspend fun delete(item: AdminQuote)

    @Update
    suspend fun update(item: AdminQuote)
}
