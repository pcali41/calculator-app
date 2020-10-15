package com.example.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class CalculatorViewModel(private val calculator: StringCalculator) : ViewModel() {

    val expression: LiveData<String>
        get() = calculator.expression

    val resultPreview: LiveData<String>
        get() = calculator.result

    fun onApply() { calculator.applyResult() }

    fun onAddDecimal() { calculator.addDecimal() }

    fun onAddDigit(digit: Char) { calculator.addDigit(digit) }

    fun onAddOperator(operator: Operator) { calculator.addOperator(operator) }

    fun onDelete() { calculator.delete() }

    fun onClear() { calculator.clear() }
}
