package com.sudoku.app.domain.model

data class Puzzle(
    val id: String,
    val difficulty: Difficulty,
    val cells: List<Cell>,
    val solution: IntArray,
    val startTimeEpochMs: Long
)
