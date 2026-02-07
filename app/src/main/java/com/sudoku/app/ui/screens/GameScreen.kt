package com.sudoku.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
        SudokuGrid(
            cells = puzzle?.cells.orEmpty(),
            modifier = Modifier.size(320.dp)
        )
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

@Composable
private fun SudokuGrid(
    cells: List<com.sudoku.app.domain.model.Cell>,
    modifier: Modifier = Modifier
) {
    val borderColor = MaterialTheme.colorScheme.primary
    LazyVerticalGrid(
        columns = GridCells.Fixed(9),
        modifier = modifier
            .border(2.dp, borderColor)
    ) {
        itemsIndexed(cells) { index, cell ->
            val isThickRight = (index % 9 == 2 || index % 9 == 5)
            val isThickBottom = (index / 9 == 2 || index / 9 == 5)
            val cellBorder = Modifier
                .border(1.dp, borderColor)
                .then(
                    if (isThickRight) Modifier.border(2.dp, borderColor) else Modifier
                )
                .then(
                    if (isThickBottom) Modifier.border(2.dp, borderColor) else Modifier
                )

            Box(
                modifier = cellBorder
                    .size(32.dp)
                    .background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ) {
                if (cell.value != 0) {
                    Text(
                        text = cell.value.toString(),
                        fontWeight = if (cell.isFixed) FontWeight.Bold else FontWeight.Normal,
                        color = if (cell.isFixed) MaterialTheme.colorScheme.onSurface else Color.Gray
                    )
                }
            }
        }
    }
}
