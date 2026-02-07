package com.sudoku.app.billing

interface BillingManager {
    fun isSubscribed(productId: String): Boolean
    fun launchBillingFlow(productId: String)
    fun restorePurchases()
}
