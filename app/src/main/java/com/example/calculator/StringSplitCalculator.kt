package com.example.calculator

import java.math.BigDecimal

class StringSplitCalculator : StringCalculator {
    private companion object {
        const val NEGATIVE_SIGN = '-'
        const val DECIMAL_SIGN = '.'

        const val INVALID_EXPRESSION_MESSAGE = "Invalid expression"
        const val DIVIDE_BY_ZERO_MESSAGE = "Can't divide by zero"
    }

    private var _lastValidResult: String? = null
    private var _useErrors = false

    override fun addDecimal(expression: String): String {
        return if (!getLastTerm(expression).contains(DECIMAL_SIGN)) {
            expression + DECIMAL_SIGN
        } else {
            expression
        }
    }

    override fun addDigit(expression: String, digit: Char): String {
        val lastTerm = getLastTerm(expression)
        val isRedundantZero = (digit == '0')
            && !lastTerm.contains(DECIMAL_SIGN)
            && lastTerm.toBigDecimalOrNull() == BigDecimal.ZERO

        return if (!isRedundantZero) expression + digit else expression
    }

    override fun addOperation(expression: String, operation: Operation): String {
        var newExpression = expression

        // Remove the trailing decimal point, if any
        if (newExpression.lastOrNull() == DECIMAL_SIGN) {
            newExpression = newExpression.dropLast(1)
        }

        var numToReplace = 0
        var newOperation = operation.symbol

        when (newExpression.lastOrNull()) {
            // If we're trying to subtract with an empty expression use a negative sign
            null -> if (operation == Operation.SUBTRACT) newOperation = NEGATIVE_SIGN

            // Replace the negative sign and its preceding operation, if any
            NEGATIVE_SIGN -> {
                numToReplace = 2

                if (operation == Operation.SUBTRACT && expression.length <= 2) {
                    newOperation = NEGATIVE_SIGN
                }
            }

            // Replace the last operator if the last character is an add/subtract sign
            Operation.ADD.symbol, Operation.SUBTRACT.symbol -> numToReplace = 1

            // Handle when the last character is one of the remaining operators
            Operation.MULTIPLY.symbol, Operation.DIVIDE.symbol -> {
                if (operation == Operation.SUBTRACT) {
                    newOperation = NEGATIVE_SIGN
                } else {
                    numToReplace = 1
                }
            }
        }

        newExpression = newExpression.dropLast(numToReplace) + newOperation

        return newExpression
    }

    override fun clear(expression: String): String {
        return ""
    }

    override fun compute(expression: String): String {
        var result = ""
        var errorMessage = INVALID_EXPRESSION_MESSAGE

        // Compute the result and error message using the order of operations
        try {
            val orderOfOperations = Operation.values().asList()
            result = compute(expression, orderOfOperations)?.toEngineeringString() ?: ""

        } catch (e: ArithmeticException) {
            e.printStackTrace()
            errorMessage = DIVIDE_BY_ZERO_MESSAGE
        }

        _lastValidResult = if (result.isEmpty()) null else result

        if (result.isEmpty() && _useErrors) result = errorMessage

        _useErrors = false

        return result
    }

    // TODO: Modify this function to apply operations on equivalent OoO levels simultaneously
    private fun compute(expression: String, orderOfOperations: List<Operation>) : BigDecimal? {
        // Get the next operation to be split the given expression by
        val operation = orderOfOperations.last()

        // Split the expression based on the current operation's symbol
        val subExpressions = expression.split(operation.symbol)

        // Recursively break down each sub-expression until only numbers remain
        val terms: List<BigDecimal?> =
            if (orderOfOperations.size > 1) {
                subExpressions.map { compute(it, orderOfOperations.dropLast(1)) }
            } else {
                subExpressions.map { it.toBigDecimalOrNull() }
            }


        // Perform the operation on each term to produce the result
        return if (terms.contains(null)) null else terms.reduce(operation.function)
    }

    override fun delete(expression: String): String {
        return expression.dropLast(1)
    }

    private fun getLastTerm(expression: String): String {
        var lastTerm: String = expression

        for (operation in Operation.values()) {
            if (lastTerm.isEmpty()) break
            lastTerm = lastTerm.split(operation.symbol).lastOrNull() ?: ""
        }

        return lastTerm
    }

    override fun submit(expression: String): String {
        _useErrors = true
        return _lastValidResult ?: expression
    }
}
