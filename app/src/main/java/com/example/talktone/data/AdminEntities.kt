package com.example.talktone.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "admin_poems")
data class AdminPoem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titleAm: String,
    val titleEn: String,
    val contentAm: String,
    val contentEn: String,
    val author: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "admin_terets")
data class AdminTeret(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titleAm: String,
    val titleEn: String,
    val contentAm: String,
    val contentEn: String,
    val author: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "admin_misale")
data class AdminMisale(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titleAm: String,
    val titleEn: String,
    val contentAm: String,
    val contentEn: String,
    val author: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "admin_quiz")
data class AdminQuiz(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val questionAm: String,
    val questionEn: String,
    val option0: String,
    val option1: String,
    val option2: String,
    val option3: String,
    val correctIndex: Int,
    val explanationAm: String = "",
    val explanationEn: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "admin_quotes")
data class AdminQuote(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val textAm: String,
    val textEn: String,
    val author: String,
    val gradientStart: Long = 0xFF1A0A2E,
    val gradientEnd: Long = 0xFF16213E,
    val createdAt: Long = System.currentTimeMillis()
)
