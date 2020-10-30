package com.example.calculator.calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.calculator.database.CalculationDatabaseDAO

/**
 * A [ViewModelProvider.Factory] for providing a [CalculatorViewModel] instance.
 */
class CalculatorViewModelFactory(
        private val databaseDAO: CalculationDatabaseDAO,
        private val calculator: StringCalculator
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalculatorViewModel::class.java)) {
            return CalculatorViewModel(databaseDAO, calculator) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}