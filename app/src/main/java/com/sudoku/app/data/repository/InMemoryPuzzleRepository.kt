package com.sudoku.app.data.repository

import com.sudoku.app.domain.engine.SudokuGenerator
import com.sudoku.app.domain.engine.SudokuSolver
import com.sudoku.app.domain.model.Difficulty
import com.sudoku.app.domain.model.Puzzle

class InMemoryPuzzleRepository(
    private val generator: SudokuGenerator = SudokuGenerator(),
    private val solver: SudokuSolver = SudokuSolver()
) : PuzzleRepository {
    private var latestPuzzle: Puzzle? = null

    override suspend fun generatePuzzle(difficulty: Difficulty): Puzzle {
        val puzzle = generator.generate(difficulty)
        latestPuzzle = puzzle
        return puzzle
    }

    override suspend fun validateSolution(puzzle: Puzzle): Boolean {
        val grid = puzzle.toGrid()
        return solver.isValidSolution(grid)
    }

    override suspend fun saveGameState(puzzle: Puzzle) {
        latestPuzzle = puzzle
    }

    override suspend fun loadLatestGame(): Puzzle? = latestPuzzle

    private fun Puzzle.toGrid(): IntArray {
        val grid = IntArray(81)
        cells.forEach { cell ->
            grid[cell.row * 9 + cell.col] = cell.value
        }
        return grid
    }
}
