package com.sudoku.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sudoku.app.domain.model.Difficulty
import com.sudoku.app.viewmodel.GameUiState
import kotlinx.coroutines.launch

@Composable
fun GameScreen(
    difficulty: Difficulty,
    uiState: GameUiState,
    onStartGame: suspend (Difficulty) -> Unit,
    onValidate: suspend () -> Unit,
    onExit: () -> Unit
) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(difficulty) {
        onStartGame(difficulty)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val puzzle = uiState.puzzle
        val filledCells = puzzle?.cells?.count { it.value != 0 } ?: 0
        Text(text = "Difficulty: ${difficulty.name}")
        Text(text = "Filled cells: $filledCells")
        uiState.errorMessage?.let { message ->
            Text(text = message)
        }
        Button(onClick = { scope.launch { onValidate() } }) {
            Text(text = "Check Solution")
        }
        Button(onClick = onExit) {
            Text(text = "Back")
        }
    }
}
