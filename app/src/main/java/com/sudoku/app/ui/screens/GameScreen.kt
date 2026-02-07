package com.sudoku.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sudoku.app.domain.model.Difficulty
import com.sudoku.app.viewmodel.CellPosition
import com.sudoku.app.viewmodel.GameUiState
import kotlinx.coroutines.delay
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
    onSelectCell: (Int, Int) -> Unit,
    onNumberInput: (Int) -> Unit,
    onErase: () -> Unit,
    onUndo: () -> Unit,
    onRedo: () -> Unit,
    onExit: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val elapsedSeconds by produceState(
        initialValue = 0,
        key1 = uiState.puzzle?.startTimeEpochMs
    ) {
        while (true) {
            val startTime = uiState.puzzle?.startTimeEpochMs
            value = if (startTime == null) {
                0
            } else {
                ((System.currentTimeMillis() - startTime) / 1000).toInt().coerceAtLeast(0)
            }
            delay(1000)
        }
    }
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
        val filledCells = uiState.cells.count { it.value != 0 }
        GameHud(
            difficulty = difficulty,
            elapsedSeconds = elapsedSeconds,
            filledCells = filledCells,
            errorCount = uiState.incorrectCells.size
        )
        SudokuGrid(
            cells = uiState.cells,
            selectedRow = uiState.selectedRow,
            selectedCol = uiState.selectedCol,
            incorrectCells = uiState.incorrectCells,
            modifier = Modifier.size(320.dp),
            onCellSelected = onSelectCell
        )
        NumberPad(onNumberInput = onNumberInput)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onErase) {
                Text(text = "Erase")
            }
            Button(onClick = onUndo) {
                Text(text = "Undo")
            }
            Button(onClick = onRedo) {
                Text(text = "Redo")
            }
        }
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
        if (uiState.isCompleted) {
            Text(text = "Puzzle completed!", fontWeight = FontWeight.Bold)
        }
        Button(onClick = onExit) {
            Text(text = "Back")
        }
    }
}

@Composable
private fun GameHud(
    difficulty: Difficulty,
    elapsedSeconds: Int,
    filledCells: Int,
    errorCount: Int
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Difficulty: ${difficulty.name}")
        Text(text = "Time: ${formatSeconds(elapsedSeconds)}")
        Text(text = "Filled cells: $filledCells")
        Text(text = "Errors: $errorCount")
    }
}

@Composable
private fun SudokuGrid(
    cells: List<com.sudoku.app.domain.model.Cell>,
    selectedRow: Int?,
    selectedCol: Int?,
    incorrectCells: Set<CellPosition>,
    modifier: Modifier = Modifier,
    onCellSelected: (Int, Int) -> Unit
) {
    val borderColor = MaterialTheme.colorScheme.primary
    val selectedColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.35f)
    val relatedColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f)
    val errorColor = MaterialTheme.colorScheme.error.copy(alpha = 0.35f)
    LazyVerticalGrid(
        columns = GridCells.Fixed(9),
        modifier = modifier
            .border(2.dp, borderColor)
    ) {
        itemsIndexed(cells) { index, cell ->
            val isThickRight = (index % 9 == 2 || index % 9 == 5)
            val isThickBottom = (index / 9 == 2 || index / 9 == 5)
            val isSelected = cell.row == selectedRow && cell.col == selectedCol
            val isRelatedRow = selectedRow != null && cell.row == selectedRow
            val isRelatedCol = selectedCol != null && cell.col == selectedCol
            val isRelatedBox = if (selectedRow == null || selectedCol == null) {
                false
            } else {
                cell.row / 3 == selectedRow / 3 && cell.col / 3 == selectedCol / 3
            }
            val hasError = incorrectCells.contains(CellPosition(cell.row, cell.col))
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
                    .background(
                        when {
                            hasError -> errorColor
                            isSelected -> selectedColor
                            isRelatedRow || isRelatedCol || isRelatedBox -> relatedColor
                            else -> MaterialTheme.colorScheme.surface
                        }
                    )
                    .clickable { onCellSelected(cell.row, cell.col) },
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

@Composable
private fun NumberPad(onNumberInput: (Int) -> Unit) {
    LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = Modifier.size(220.dp)) {
        items(9) { index ->
            val number = index + 1
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary)
                    .clickable { onNumberInput(number) },
                contentAlignment = Alignment.Center
            ) {
                Text(text = number.toString(), fontWeight = FontWeight.Medium)
            }
        }
    }
}

private fun formatSeconds(totalSeconds: Int): String {
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%02d:%02d".format(minutes, seconds)
}
