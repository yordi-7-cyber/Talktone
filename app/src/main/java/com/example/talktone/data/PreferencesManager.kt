package com.example.talktone.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "biir_settings")

class PreferencesManager(private val context: Context) {
    companion object {
        val DARK_MODE = booleanPreferencesKey("dark_mode")
        val LANGUAGE = stringPreferencesKey("language")
        val IS_ONBOARDED = booleanPreferencesKey("is_onboarded")
    }

    val isDarkMode: Flow<Boolean> = context.dataStore.data.map { it[DARK_MODE] ?: false }
    val language: Flow<String> = context.dataStore.data.map { it[LANGUAGE] ?: "am" }
    val isOnboarded: Flow<Boolean> = context.dataStore.data.map { it[IS_ONBOARDED] ?: false }

    suspend fun setDarkMode(enabled: Boolean) { context.dataStore.edit { it[DARK_MODE] = enabled } }
    suspend fun setLanguage(lang: String) { context.dataStore.edit { it[LANGUAGE] = lang } }
    suspend fun setOnboarded(done: Boolean) { context.dataStore.edit { it[IS_ONBOARDED] = done } }
}
