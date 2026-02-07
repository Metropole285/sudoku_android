package com.sudoku.app.di

import com.sudoku.app.data.repository.InMemoryPuzzleRepository
import com.sudoku.app.data.repository.LeaderboardRepository
import com.sudoku.app.data.repository.PuzzleRepository
import com.sudoku.app.domain.usecase.GeneratePuzzleUseCase
import com.sudoku.app.domain.usecase.ValidateSolutionUseCase

object AppModule {
    fun providePuzzleRepository(): PuzzleRepository {
        return InMemoryPuzzleRepository()
    }

    fun provideGeneratePuzzleUseCase(puzzleRepository: PuzzleRepository): GeneratePuzzleUseCase {
        return GeneratePuzzleUseCase(puzzleRepository)
    }

    fun provideValidateSolutionUseCase(puzzleRepository: PuzzleRepository): ValidateSolutionUseCase {
        return ValidateSolutionUseCase(puzzleRepository)
    }

    fun provideLeaderboardRepository(implementation: LeaderboardRepository): LeaderboardRepository {
        return implementation
    }
}
