package com.example.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import java.math.BigDecimal

class StringCalculatorImpl(initialExpression: String = "") : StringCalculator {
    private val _expression = MutableLiveData(initialExpression)

    override val expression: LiveData<String>
        get() = _expression

    private val _result = MediatorLiveData<String>().apply {
        addSource(_expression) { value = compute() }
    }

    override val result: LiveData<String>
        get() = _result

    private var errorMessage = ""

    override fun applyResult() {
        if (errorMessage.isEmpty()) {
            _expression.value = _result.value
        } else {
            _result.value = errorMessage
        }
    }

    private fun compute(allowErrors: Boolean = false): String {
        var result = ""

        // Compute the result and error message using the order of operations
        errorMessage = ""
        try {
            val orderOfOperations = Operator.values().asList()
            _expression.value?.let {
                result = compute(it, orderOfOperations)?.toEngineeringString() ?: ""
            }

            if (result.isEmpty()) errorMessage = INVALID_EXPRESSION_MESSAGE

        } catch (e: ArithmeticException) {
            e.printStackTrace()
            errorMessage = DIVIDE_BY_ZERO_MESSAGE
        }

        if (result.isEmpty() && allowErrors) result = errorMessage

        return result
    }

    // TODO: Modify this function to apply operations on equivalent OoO levels simultaneously
    private fun compute(
        subExpression: String,
        orderOfOperations: List<Operator>,
    ): BigDecimal? {

        // Get the next operator to be split the given expression by
        val operator = orderOfOperations.last()

        // Split the expression based on the current operator's symbol
        val subExpressions = subExpression.split(operator.symbol)

        // Recursively break down each sub-expression until only numbers remain
        val terms: List<BigDecimal?> =
            if (orderOfOperations.size > 1) {
                subExpressions.map { compute(it, orderOfOperations.dropLast(1)) }
            } else {
                subExpressions.map { it.toBigDecimalOrNull() }
            }

        // Perform the operation on each term to produce the result
        return if (terms.contains(null)) null else terms.reduce(operator.function)
    }

    private fun getLastTerm(): String {
        val operatorSymbols = Operator.values().map { it.symbol }
        return _expression.value?.takeLastWhile { !operatorSymbols.contains(it) } ?: ""
    }

    override fun addDecimal() {
        if (!getLastTerm().contains(DECIMAL_SIGN)) {
            _expression.value += DECIMAL_SIGN
        }
    }

    override fun addDigit(digit: Char) {
        val lastTerm = getLastTerm()
        val hasRedundantZero = !lastTerm.contains(DECIMAL_SIGN)
                    && lastTerm.toBigDecimalOrNull() == BigDecimal.ZERO

        _expression.value = if (hasRedundantZero) {
            _expression.value?.dropLast(1).plus(digit)
        } else {
            _expression.value?.plus(digit)
        }
    }

    override fun addOperator(operator: Operator) {
        var newExpression = _expression.value ?: ""

        // Remove the trailing decimal point, if present
        if (newExpression.endsWith(DECIMAL_SIGN)) {
            newExpression = newExpression.dropLast(1)
        }

        var operatorToAdd = operator.symbol

        when (newExpression.lastOrNull()) {
            null, Operator.MULTIPLY.symbol, Operator.DIVIDE.symbol -> {
                if (operator == Operator.SUBTRACT) operatorToAdd = NEGATIVE_SIGN
            }
        }

        if (operatorToAdd != NEGATIVE_SIGN) {
            newExpression = newExpression.dropLastWhile { !it.isDigit() }
        }

        if (operatorToAdd == NEGATIVE_SIGN || newExpression.isNotEmpty()) {
            newExpression = newExpression.plus(operatorToAdd)
        }

        _expression.value = newExpression
    }

    override fun delete() {
        _expression.value = _expression.value?.dropLast(1) ?: ""
    }

    override fun clear() {
        _expression.value = ""
    }

    private companion object {
        const val NEGATIVE_SIGN = '-'
        const val DECIMAL_SIGN = '.'

        const val INVALID_EXPRESSION_MESSAGE = "Invalid expression"
        const val DIVIDE_BY_ZERO_MESSAGE = "Can't divide by zero"
    }
}
