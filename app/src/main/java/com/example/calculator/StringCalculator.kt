package com.example.calculator

import androidx.lifecycle.LiveData

interface StringCalculator {
    val expression: LiveData<String>
    val result: LiveData<String>

    fun addDigit(digit: Char)

    fun addDecimal()

    fun addOperator(operator: Operator)

    fun delete()

    fun clear()

    fun applyResult()
}
