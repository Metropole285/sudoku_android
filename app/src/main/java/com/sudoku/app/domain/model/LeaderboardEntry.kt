package com.sudoku.app.domain.model

data class LeaderboardEntry(
    val rank: Int,
    val playerName: String,
    val timeSeconds: Int,
    val score: Int
)
