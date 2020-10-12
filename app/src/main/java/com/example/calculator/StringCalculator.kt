package com.example.calculator

interface StringCalculator {
    fun addDigit(expression: String, digit: Char): String

    fun addDecimal(expression: String): String

    fun addOperation(expression: String, operation: Operation): String

    fun clear(expression: String): String

    fun compute(expression: String): String

    fun delete(expression: String): String

    fun submit(expression: String): String
}
