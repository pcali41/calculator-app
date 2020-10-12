package com.example.calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CalculatorViewModelFactory(
        private val calculator: StringCalculator) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalculatorViewModel::class.java)) {
            return CalculatorViewModel(calculator) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}