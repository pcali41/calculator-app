package com.example.calculator.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.calculator.database.CalculationDatabaseDAO

/**
 * A [ViewModelProvider.Factory] for providing a [HistoryViewModel] instance.
 */
class HistoryViewModelFactory(
        private val database: CalculationDatabaseDAO
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(database) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}