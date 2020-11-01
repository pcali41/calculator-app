package com.example.calculator.history

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.calculator.Repository
import com.example.calculator.database.Calculation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel for HistoryFragment.
 */
class HistoryViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    /**
     * Provides an observable list of all Calculations contained by the database.
     */
    val calculations: LiveData<List<Calculation>> = repository.getAllCalculations()

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
            repository.getCalculation(id)?.expression ?: ""
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
        viewModelScope.launch { clearHistory() }

        // Notify the user that the history list has been successfully cleared
        _showSnackbarEvent.value = true
    }

    private suspend fun clearHistory() {
        withContext(Dispatchers.IO) { repository.clearHistory() }
    }

    /**
     * Clears the "show snackbar" event to avoid repeating it if the Fragment is reloaded.
     */
    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = false
    }
}