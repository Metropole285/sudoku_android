package com.sudoku.app.domain.engine

import com.sudoku.app.domain.model.Cell
import com.sudoku.app.domain.model.Difficulty
import com.sudoku.app.domain.model.Puzzle
import java.util.UUID
import kotlin.random.Random

class SudokuGenerator(
    private val solver: SudokuSolver = SudokuSolver()
) {
    fun generate(difficulty: Difficulty): Puzzle {
        val grid = IntArray(81)
        fillDiagonalBoxes(grid)
        solver.solve(grid)
        removeCells(grid, difficulty)

        val cells = grid.mapIndexed { index, value ->
            val row = index / 9
            val col = index % 9
            Cell(row = row, col = col, value = value, isFixed = value != 0)
        }
        return Puzzle(
            id = UUID.randomUUID().toString(),
            difficulty = difficulty,
            cells = cells,
            startTimeEpochMs = System.currentTimeMillis()
        )
    }

    fun isSolvable(grid: IntArray): Boolean {
        val copy = grid.clone()
        return solver.solve(copy)
    }

    private fun fillDiagonalBoxes(grid: IntArray) {
        for (box in 0 until 3) {
            val nums = (1..9).shuffled(Random)
            for (i in 0 until 9) {
                val row = box * 3 + i / 3
                val col = box * 3 + i % 3
                grid[row * 9 + col] = nums[i]
            }
        }
    }

    private fun removeCells(grid: IntArray, difficulty: Difficulty) {
        val cellsToRemove = when (difficulty) {
            Difficulty.EASY -> 35
            Difficulty.MEDIUM -> 45
            Difficulty.HARD -> 55
        }
        val positions = (0 until 81).shuffled(Random).take(cellsToRemove)
        positions.forEach { grid[it] = 0 }
    }
}
