package com.sudoku.app.data.local.dao

import com.sudoku.app.data.local.entity.GameStateEntity

interface GameStateDao {
    suspend fun upsertGameState(state: GameStateEntity)
    suspend fun getLatestGameState(): GameStateEntity?
    suspend fun deleteAll()
}
