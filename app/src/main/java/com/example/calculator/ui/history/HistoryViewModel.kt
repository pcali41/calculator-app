package com.example.calculator.ui.history

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculator.data.Repository
import com.example.calculator.data.room.Calculation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel for the HistoryScreen.
 */
class HistoryViewModel @ViewModelInject constructor(
    private val repository: Repository,
) : ViewModel() {

    /**
     * Provides an observable list of all Calculations contained by the database.
     */
    val historyItems: LiveData<List<Calculation>> = repository.getAllCalculations()

    /**
     * Executes a "Clear History" event when the CLEAR button is clicked.
     */
    fun clearHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearHistory()
        }
    }

    /**
     * Sets the current expression to a new value
     */
    fun recallExpression(recalledExpression: String) {
        if (recalledExpression.isNotEmpty()) {
            repository.currentExpression.value = recalledExpression
        }
    }
}