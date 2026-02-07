package com.sudoku.app.viewmodel

import com.sudoku.app.domain.model.Difficulty
import com.sudoku.app.domain.model.Puzzle
import com.sudoku.app.domain.usecase.GeneratePuzzleUseCase
import com.sudoku.app.domain.usecase.ValidateSolutionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GameViewModel(
    private val generatePuzzleUseCase: GeneratePuzzleUseCase,
    private val validateSolutionUseCase: ValidateSolutionUseCase
) {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState

    suspend fun startNewGame(difficulty: Difficulty) {
        val puzzle = generatePuzzleUseCase(difficulty)
        _uiState.value = _uiState.value.copy(
            puzzle = puzzle,
            isCompleted = false,
            errorMessage = null
        )
    }

    suspend fun validateCurrentPuzzle() {
        val puzzle = _uiState.value.puzzle ?: return
        val isValid = validateSolutionUseCase(puzzle)
        _uiState.value = _uiState.value.copy(
            isCompleted = isValid,
            errorMessage = if (isValid) null else "Solution is not valid."
        )
    }
}

data class GameUiState(
    val puzzle: Puzzle? = null,
    val isCompleted: Boolean = false,
    val errorMessage: String? = null
)
