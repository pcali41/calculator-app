package com.example.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.calculator.calculator.Operator
import com.example.calculator.calculator.StringCalculator
import com.example.calculator.database.Calculation
import com.example.calculator.database.CalculationDatabaseDAO
import javax.inject.Inject

class Repository @Inject constructor(
    private val calculator: StringCalculator,
    private val database: CalculationDatabaseDAO
) {

    val currentExpression: MutableLiveData<String>
        get() = calculator.expression

    val currentResult: LiveData<String>
        get() = calculator.result

    fun apply(): Boolean = calculator.apply()

    fun addDigit(digit: Char) = calculator.addDigit(digit)

    fun addDecimal() = calculator.addDecimal()

    fun addOperator(operator: Operator) = calculator.addOperator(operator)

    fun delete() = calculator.delete()

    fun clear() = calculator.clear()


    fun saveCalculation(calculation: Calculation) = database.insert(calculation)

    fun getAllCalculations(): LiveData<List<Calculation>> = database.getAllCalculations()

    fun getCalculation(id: Long): Calculation? = database.get(id)

    fun clearHistory() = database.clear()
}
