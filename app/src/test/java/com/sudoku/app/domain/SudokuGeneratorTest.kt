package com.sudoku.app.domain

import com.sudoku.app.domain.engine.SudokuGenerator
import com.sudoku.app.domain.engine.SudokuSolver
import com.sudoku.app.domain.model.Difficulty
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SudokuGeneratorTest {
    @Test
    fun `generator creates solvable puzzle for each difficulty`() {
        val generator = SudokuGenerator()
        val solver = SudokuSolver()

        Difficulty.values().forEach { difficulty ->
            val puzzle = generator.generate(difficulty)
            assertEquals(81, puzzle.cells.size)
            val grid = IntArray(81)
            puzzle.cells.forEach { cell ->
                grid[cell.row * 9 + cell.col] = cell.value
            }
            assertTrue(generator.isSolvable(grid))
            assertEquals(1, solver.countSolutions(grid.clone(), limit = 2))
            val solved = grid.clone()
            assertTrue(solver.solve(solved))
        }
    }
}
