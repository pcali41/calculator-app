package com.example.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class CalculatorViewModel(private val calculator: StringCalculator) : ViewModel() {

    private val _expression = MutableLiveData<String>()
    val expression: LiveData<String>
        get() = _expression

    val resultPreview: LiveData<String> = Transformations.map(_expression) {
        calculator.compute(it)
    }

    fun onAddDecimal() {
        _expression.value = calculator.addDecimal(_expression.value ?: "")
    }

    fun onAddDigit(digit: Char) {
        _expression.value = calculator.addDigit(_expression.value ?: "", digit)
    }

    fun onAddOperation(operation: Operation) {
        _expression.value = calculator.addOperation(_expression.value ?: "", operation)
    }

    fun onClear() {
        _expression.value = calculator.clear(_expression.value ?: "")
    }

    fun onDelete() {
        _expression.value = calculator.delete(_expression.value ?: "")
    }

    fun onSubmit() {
        _expression.value = calculator.submit(_expression.value ?: "")
    }
}
