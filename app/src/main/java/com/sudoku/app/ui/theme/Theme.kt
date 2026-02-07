package com.sudoku.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = Primary,
    secondary = Secondary,
    background = Background,
    surface = Surface
)

private val DarkColors = darkColorScheme(
    primary = Primary,
    secondary = Secondary
)

@Composable
fun SudokuTheme(
    isDarkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (isDarkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}
