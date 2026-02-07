package com.sudoku.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sudoku.app.domain.model.Difficulty

@Composable
fun HomeScreen(
    onStartGame: (Difficulty) -> Unit,
    onOpenLeaderboard: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Sudoku")
        Button(onClick = { onStartGame(Difficulty.EASY) }) {
            Text(text = "New Game (Easy)")
        }
        Button(onClick = { onStartGame(Difficulty.MEDIUM) }) {
            Text(text = "New Game (Medium)")
        }
        Button(onClick = { onStartGame(Difficulty.HARD) }) {
            Text(text = "New Game (Hard)")
        }
        Button(onClick = onOpenLeaderboard) {
            Text(text = "Leaderboards")
        }
    }
}
