package com.example.calculator.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

/**
 * Provides an interface for performing calculator operations on an observable
 * string.
 */
interface StringCalculator {
    /**
     * Represents the current mathematical expression being edited.
     */
    val expression: MutableLiveData<String>

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

/**
 * A Dagger Hilt [Module] for providing a [StringCalculator] implementation to
 * dependent ViewModel Components.
 */
@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class StringCalculatorModule {

    @Binds
    abstract fun bindStringCalculator(
        stringCalculatorImpl: StringSplitCalculator
    ): StringCalculator
}
