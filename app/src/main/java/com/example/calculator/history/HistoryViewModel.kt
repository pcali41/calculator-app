package com.example.calculator.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculator.database.Calculation
import com.example.calculator.database.CalculationDatabaseDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel for HistoryFragment.
 */
class HistoryViewModel(val database: CalculationDatabaseDAO) : ViewModel() {

    /**
     * Provides an observable list of all Calculations contained by the database.
     */
    val calculations: LiveData<List<Calculation>> = database.getAllCalculations()

    /**
     * Tells the Fragment to navigate to the CalculatorFragment with the selected
     * [Calculation]'s expression.
     */
    private val _loadSelectedCalculation = MutableLiveData<String>()

    /**
     * If this is non-null, navigate to CalculatorFragment and call [doneLoadingCalculation].
     */
    val loadSelectedCalculation: LiveData<String>
        get() = _loadSelectedCalculation

    /**
     * Shows a snackbar to the user to notify them that the history list has been
     * cleared out.
     */
    private val _showSnackbarEvent = MutableLiveData<Boolean>()

    /**
     * If set to true, immediately show a snackbar and call [doneShowingSnackbar].
     */
    val showSnackbarEvent: LiveData<Boolean>
        get() = _showSnackbarEvent

    /**
     * Executes a "Load Calculation" event for the calculation with the given id.
     */
    fun onLoadCalculation(id: Long) {
        viewModelScope.launch {
            _loadSelectedCalculation.value = getSelectedExpression(id)
        }
    }

    /**
     * Gets the expression of the [Calculation] with the given id from the database.
     * If the calculation id is invalid it returns an empty string.
     */
    private suspend fun getSelectedExpression(id: Long): String {
        return withContext(Dispatchers.IO) {
            database.get(id)?.expression ?: ""
        }
    }

    /**
     * Clears the "load calculation" event to avoid repeating it if the Fragment is reloaded.
     */
    fun doneLoadingCalculation() {
        _loadSelectedCalculation.value = null
    }

    /**
     * Executes a "Clear History" when the CLEAR button is clicked
     */
    fun onClearHistory() {
        viewModelScope.launch { clear() }

        // Notify the user that the history list has been successfully cleared
        _showSnackbarEvent.value = true
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) { database.clear() }
    }

    /**
     * Clears the "show snackbar" event to avoid repeating it if the Fragment is reloaded.
     */
    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = false
    }
}