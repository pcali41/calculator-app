package com.example.calculator.calculator

import androidx.lifecycle.LiveData

/**
 * Provides an interface for performing calculator operations on an observable
 * string.
 */
interface StringCalculator {
    /**
     * Represents the current mathematical expression being edited.
     */
    val expression: LiveData<String>

    /**
     * Represents the computed numerical result of [expression].
     * If invalid, this variable will instead display the matching error message.
     */
    val result: LiveData<String>

    /**
     * Adds the provided digit to the current expression
     */
    fun addDigit(digit: Char)

    /**
     * Adds a decimal point to the current expression, if applicable
     */
    fun addDecimal()

    /**
     * Adds the provided operator to the expression, if applicable
     */
    fun addOperator(operator: Operator)

    /**
     * Deletes the last character in the current expression.
     */
    fun delete()

    /**
     * Clears the current expression.
     */
    fun clear()

    /**
     * Applies the current expression and displays the result.
     */
    fun apply(): Boolean
}
