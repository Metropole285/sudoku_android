package com.sudoku.app.data.local.dao

data class SettingsEntity(
    val theme: String,
    val soundEnabled: Boolean,
    val statisticsJson: String
)

interface SettingsDao {
    suspend fun loadSettings(): SettingsEntity?
    suspend fun saveSettings(settings: SettingsEntity)
}
