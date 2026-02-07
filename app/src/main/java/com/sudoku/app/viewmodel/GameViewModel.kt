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
    private val undoStack = ArrayDeque<Move>()
    private val redoStack = ArrayDeque<Move>()

    suspend fun startNewGame(difficulty: Difficulty) {
        val puzzle = generatePuzzleUseCase(difficulty)
        undoStack.clear()
        redoStack.clear()
        _uiState.value = _uiState.value.copy(
            puzzle = puzzle,
            cells = puzzle.cells,
            selectedRow = null,
            selectedCol = null,
            isCompleted = false,
            errorMessage = null
        )
    }

    suspend fun validateCurrentPuzzle() {
        val puzzle = _uiState.value.puzzle ?: return
        val currentPuzzle = puzzle.copy(cells = _uiState.value.cells)
        val isValid = validateSolutionUseCase(currentPuzzle)
        _uiState.value = _uiState.value.copy(
            isCompleted = isValid,
            errorMessage = if (isValid) null else "Solution is not valid."
        )
    }

    fun selectCell(row: Int, col: Int) {
        val cell = _uiState.value.cells.firstOrNull { it.row == row && it.col == col } ?: return
        if (cell.isFixed) return
        _uiState.value = _uiState.value.copy(selectedRow = row, selectedCol = col)
    }

    fun inputNumber(value: Int) {
        val row = _uiState.value.selectedRow ?: return
        val col = _uiState.value.selectedCol ?: return
        updateCell(row, col, value)
    }

    fun erase() {
        val row = _uiState.value.selectedRow ?: return
        val col = _uiState.value.selectedCol ?: return
        updateCell(row, col, 0)
    }

    fun undo() {
        val move = undoStack.removeLastOrNull() ?: return
        applyMove(move.row, move.col, move.previousValue)
        redoStack.addLast(move)
    }

    fun redo() {
        val move = redoStack.removeLastOrNull() ?: return
        applyMove(move.row, move.col, move.newValue)
        undoStack.addLast(move)
    }

    private fun updateCell(row: Int, col: Int, newValue: Int) {
        val current = _uiState.value.cells.firstOrNull { it.row == row && it.col == col } ?: return
        if (current.isFixed || current.value == newValue) return
        val move = Move(row, col, current.value, newValue)
        undoStack.addLast(move)
        redoStack.clear()
        applyMove(row, col, newValue)
    }

    private fun applyMove(row: Int, col: Int, value: Int) {
        val updatedCells = _uiState.value.cells.map { cell ->
            if (cell.row == row && cell.col == col) {
                cell.copy(value = value)
            } else {
                cell
            }
        }
        val updatedPuzzle = _uiState.value.puzzle?.copy(cells = updatedCells)
        _uiState.value = _uiState.value.copy(cells = updatedCells, puzzle = updatedPuzzle)
    }
}

data class GameUiState(
    val puzzle: Puzzle? = null,
    val cells: List<com.sudoku.app.domain.model.Cell> = emptyList(),
    val selectedRow: Int? = null,
    val selectedCol: Int? = null,
    val isCompleted: Boolean = false,
    val errorMessage: String? = null
)

private data class Move(
    val row: Int,
    val col: Int,
    val previousValue: Int,
    val newValue: Int
)
