package com.sudoku.app.data.repository

import com.sudoku.app.domain.model.Difficulty
import com.sudoku.app.domain.model.LeaderboardEntry

interface LeaderboardRepository {
    suspend fun fetchLeaderboard(difficulty: Difficulty, period: String): List<LeaderboardEntry>
    suspend fun submitScore(entry: LeaderboardEntry, difficulty: Difficulty, period: String)
}
