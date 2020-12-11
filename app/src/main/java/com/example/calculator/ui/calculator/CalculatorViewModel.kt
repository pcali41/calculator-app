package com.example.calculator.ui.calculator

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculator.data.Operator
import com.example.calculator.data.Repository
import com.example.calculator.data.room.Calculation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A [ViewModel] for managing the main calculator interface.
 */
class CalculatorViewModel @ViewModelInject constructor(
    private val repository: Repository,
) : ViewModel() {

    val expression: MutableLiveData<String>
        get() = repository.currentExpression

    val result: LiveData<String>
        get() = repository.currentResult

    /**
     * Handles a request from the user to add a numerical digit to the expression.
     */
    fun onAddDigit(digit: Char) { repository.addDigit(digit) }

    /**
     * Handles a request from the user to add a decimal to the expression.
     */
    fun onAddDecimal() { repository.addDecimal() }

    /**
     * Handles a request from the user to add an operator to the expression.
     */
    fun onAddOperator(operator: Operator) { repository.addOperator(operator) }

    /**
     * Applies the current calculator expression and displays the result to the user.
     * If the result is valid, the calculation info is saved in the database.
     */
    fun onApply() {
        viewModelScope.launch {
            val newExpression = expression.value ?: ""
            val newResult = result.value ?: ""

            if (repository.apply() && newResult.isNotEmpty())
            {
                val calculation = Calculation(
                    expression = newExpression,
                    result = newResult
                )

                saveCalculation(calculation)
            }
        }
    }

    /**
     * Saves the given expression in the database.
     */
    private suspend fun saveCalculation(calculation: Calculation) {
        withContext(Dispatchers.IO) {
            repository.saveCalculation(calculation)
        }
    }

    /**
     * Handles a request from the user to remove the last character in the expression.
     */
    fun onDelete() { repository.delete() }

    /**
     * Handles a request from the user to clear the expression.
     */
    fun onClear() { repository.clear() }
}
