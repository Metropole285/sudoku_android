package com.sudoku.app.domain.engine

class SudokuSolver {
    fun solve(grid: IntArray): Boolean {
        val emptyIndex = grid.indexOfFirst { it == 0 }
        if (emptyIndex == -1) return true

        val row = emptyIndex / 9
        val col = emptyIndex % 9

        for (num in 1..9) {
            if (isSafe(grid, row, col, num)) {
                grid[emptyIndex] = num
                if (solve(grid)) return true
                grid[emptyIndex] = 0
            }
        }
        return false
    }

    fun isValidSolution(grid: IntArray): Boolean {
        if (grid.size != 81 || grid.any { it !in 1..9 }) return false
        for (row in 0 until 9) {
            val rowSet = mutableSetOf<Int>()
            val colSet = mutableSetOf<Int>()
            for (col in 0 until 9) {
                val rowValue = grid[row * 9 + col]
                val colValue = grid[col * 9 + row]
                if (!rowSet.add(rowValue) || !colSet.add(colValue)) return false
            }
        }
        for (boxRow in 0 until 3) {
            for (boxCol in 0 until 3) {
                val boxSet = mutableSetOf<Int>()
                for (row in 0 until 3) {
                    for (col in 0 until 3) {
                        val value = grid[(boxRow * 3 + row) * 9 + (boxCol * 3 + col)]
                        if (!boxSet.add(value)) return false
                    }
                }
            }
        }
        return true
    }

    private fun isSafe(grid: IntArray, row: Int, col: Int, num: Int): Boolean {
        for (index in 0 until 9) {
            if (grid[row * 9 + index] == num) return false
            if (grid[index * 9 + col] == num) return false
        }
        val startRow = row - row % 3
        val startCol = col - col % 3
        for (r in 0 until 3) {
            for (c in 0 until 3) {
                if (grid[(startRow + r) * 9 + (startCol + c)] == num) return false
            }
        }
        return true
    }
}
