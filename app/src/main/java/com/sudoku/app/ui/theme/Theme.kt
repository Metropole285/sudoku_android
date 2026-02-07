package com.sudoku.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Primary,
    secondary = Secondary,
    background = Background,
    surface = Surface
)

private val DarkColors = darkColorScheme(
    primary = Primary,
    secondary = Secondary,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E)
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
