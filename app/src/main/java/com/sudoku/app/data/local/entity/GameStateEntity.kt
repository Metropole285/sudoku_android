package com.sudoku.app.data.local.entity

data class GameStateEntity(
    val id: String,
    val gridJson: String,
    val difficulty: String,
    val timerSeconds: Int,
    val movesJson: String,
    val updatedAtEpochMs: Long
)
