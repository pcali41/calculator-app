package com.example.calculator.calculator

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.calculator.database.Calculation
import com.example.calculator.database.CalculationDatabaseDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A [ViewModel] for managing the main calculator interface.
 */
class CalculatorViewModel @ViewModelInject constructor(
    private val database: CalculationDatabaseDAO,
    private val calculator: StringCalculator,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val expression: MutableLiveData<String>
        get() = calculator.expression

    val resultPreview: LiveData<String>
        get() = calculator.result

    /**
     * Applies the current calculator expression and displays the result to the user.
     * If the result is valid, the calculation info is saved in the database.
     */
    fun onApply() {
        viewModelScope.launch {
            val newExpression = expression.value ?: ""
            val newResult = resultPreview.value ?: ""

            if (calculator.apply() && newResult.isNotEmpty())
            {
                val calculation = Calculation(
                    expression = newExpression, result = newResult)

                saveCalculation(calculation)
            }
        }
    }

    /**
     * Saves the given expression in the database.
     */
    private suspend fun saveCalculation(calculation: Calculation) {
        withContext(Dispatchers.IO) {
            database.insert(calculation)
        }
    }

    /**
     * Handles a request from the user to add a decimal to the expression.
     */
    fun onAddDecimal() { calculator.addDecimal() }

    /**
     * Handles a request from the user to add a numerical digit to the expression.
     */
    fun onAddDigit(digit: Char) { calculator.addDigit(digit) }

    /**
     * Handles a request from the user to add an operator to the expression.
     */
    fun onAddOperator(operator: Operator) { calculator.addOperator(operator) }

    /**
     * Handles a request from the user to remove the last character in the expression.
     */
    fun onDelete() { calculator.delete() }

    /**
     * Handles a request from the user to clear the expression.
     */
    fun onClear() { calculator.clear() }
}
