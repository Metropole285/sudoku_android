package com.sudoku.app.domain.model

data class Cell(
    val row: Int,
    val col: Int,
    val value: Int,
    val isFixed: Boolean
)
