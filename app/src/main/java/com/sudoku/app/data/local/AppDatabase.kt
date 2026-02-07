package com.sudoku.app.data.local

import com.sudoku.app.data.local.dao.GameStateDao
import com.sudoku.app.data.local.dao.SettingsDao

interface AppDatabase {
    fun gameStateDao(): GameStateDao
    fun settingsDao(): SettingsDao
}
