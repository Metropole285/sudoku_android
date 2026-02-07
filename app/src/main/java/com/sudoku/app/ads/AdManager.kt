package com.sudoku.app.ads

interface AdManager {
    fun preload()
    fun showAd(onComplete: () -> Unit)
}
