package com.sudoku.app.viewmodel

import com.sudoku.app.data.repository.LeaderboardRepository
import com.sudoku.app.domain.model.Difficulty
import com.sudoku.app.domain.model.LeaderboardEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LeaderboardViewModel(
    private val leaderboardRepository: LeaderboardRepository
) {
    private val _uiState = MutableStateFlow(LeaderboardUiState())
    val uiState: StateFlow<LeaderboardUiState> = _uiState

    suspend fun loadLeaderboard(difficulty: Difficulty, period: String) {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
        val entries = leaderboardRepository.fetchLeaderboard(difficulty, period)
        _uiState.value = _uiState.value.copy(entries = entries, isLoading = false)
    }
}

data class LeaderboardUiState(
    val entries: List<LeaderboardEntry> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
