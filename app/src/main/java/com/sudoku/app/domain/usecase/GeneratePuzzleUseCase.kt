package com.sudoku.app.domain.usecase

import com.sudoku.app.data.repository.PuzzleRepository
import com.sudoku.app.domain.model.Difficulty
import com.sudoku.app.domain.model.Puzzle

class GeneratePuzzleUseCase(
    private val puzzleRepository: PuzzleRepository
) {
    suspend operator fun invoke(difficulty: Difficulty): Puzzle {
        return puzzleRepository.generatePuzzle(difficulty)
    }
}
