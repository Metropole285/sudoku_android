package com.sudoku.app.domain.usecase

import com.sudoku.app.data.repository.PuzzleRepository
import com.sudoku.app.domain.model.Puzzle

class ValidateSolutionUseCase(
    private val puzzleRepository: PuzzleRepository
) {
    suspend operator fun invoke(puzzle: Puzzle): Boolean {
        return puzzleRepository.validateSolution(puzzle)
    }
}
