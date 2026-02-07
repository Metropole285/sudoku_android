package com.sudoku.app.data.remote

import com.sudoku.app.domain.model.Difficulty
import com.sudoku.app.domain.model.LeaderboardEntry

interface FirestoreService {
    suspend fun fetchLeaderboard(difficulty: Difficulty, period: String): List<LeaderboardEntry>
    suspend fun submitScore(entry: LeaderboardEntry, difficulty: Difficulty, period: String)
}
