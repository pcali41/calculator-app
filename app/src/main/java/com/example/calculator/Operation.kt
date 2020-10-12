package com.example.calculator

import java.math.BigDecimal
import java.math.RoundingMode.HALF_UP

/**
 * An enumeration of arithmetic operators organized by the order of operations
 */
enum class Operation(val symbol: Char, val function: (BigDecimal?, BigDecimal?) -> BigDecimal?) {
    MULTIPLY(symbol = '\u00D7', function = { left, right -> left?.multiply(right) }),

    DIVIDE(symbol = '\u00F7', function = { left, right -> left?.divide(right, 10, HALF_UP) }),

    SUBTRACT(symbol = '\u2212', function = { left, right -> left?.subtract(right) }),

    ADD(symbol = '\u002B', function = { left, right -> left?.add(right) })
}