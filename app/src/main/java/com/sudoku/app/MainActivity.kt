package com.sudoku.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.sudoku.app.di.AppModule
import com.sudoku.app.domain.model.Difficulty
import com.sudoku.app.ui.screens.GameScreen
import com.sudoku.app.ui.screens.HomeScreen
import com.sudoku.app.ui.screens.LeaderboardScreen
import com.sudoku.app.ui.theme.SudokuTheme
import com.sudoku.app.viewmodel.GameViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SudokuTheme {
                val navController = rememberNavController()
                val puzzleRepository = remember { AppModule.providePuzzleRepository() }
                val gameViewModel = remember {
                    GameViewModel(
                        generatePuzzleUseCase = AppModule.provideGeneratePuzzleUseCase(puzzleRepository),
                        validateSolutionUseCase = AppModule.provideValidateSolutionUseCase(puzzleRepository)
                    )
                }
                val uiState by gameViewModel.uiState.collectAsState()

                NavHost(navController = navController, startDestination = Routes.Home) {
                    composable(Routes.Home) {
                        HomeScreen(
                            onStartGame = { difficulty ->
                                navController.navigate("${Routes.Game}/$difficulty")
                            },
                            onOpenLeaderboard = {
                                navController.navigate(Routes.Leaderboard)
                            }
                        )
                    }
                    composable(
                        route = "${Routes.Game}/{difficulty}",
                        arguments = listOf(navArgument("difficulty") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val difficulty = Difficulty.valueOf(
                            backStackEntry.arguments?.getString("difficulty") ?: Difficulty.EASY.name
                        )
                        GameScreen(
                            difficulty = difficulty,
                            uiState = uiState,
                            onStartGame = { selected -> gameViewModel.startNewGame(selected) },
                            onValidate = { gameViewModel.validateCurrentPuzzle() },
                            onExit = { navController.popBackStack() }
                        )
                    }
                    composable(Routes.Leaderboard) {
                        LeaderboardScreen(onBack = { navController.popBackStack() })
                    }
                }
            }
        }
    }
}

private object Routes {
    const val Home = "home"
    const val Game = "game"
    const val Leaderboard = "leaderboard"
}
