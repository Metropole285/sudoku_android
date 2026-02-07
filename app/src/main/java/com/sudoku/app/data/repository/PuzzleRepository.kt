package com.sudoku.app.data.repository

import com.sudoku.app.domain.model.Difficulty
import com.sudoku.app.domain.model.Puzzle

interface PuzzleRepository {
    suspend fun generatePuzzle(difficulty: Difficulty): Puzzle
    suspend fun validateSolution(puzzle: Puzzle): Boolean
    suspend fun saveGameState(puzzle: Puzzle)
    suspend fun loadLatestGame(): Puzzle?
}
