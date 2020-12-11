package com.example.calculator.ui.calculator

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.calculator.data.Operator
import com.example.calculator.ui.theme.gray215
import com.example.calculator.ui.theme.gray233
import com.example.calculator.ui.theme.holoOrangeLight
import com.example.calculator.ui.theme.typography

@Preview(showBackground = true)
@Composable
private fun CalculatorButtonGridPreview() {
    CalculatorButtonGrid(
        onAddDigit = {},
        onAddDecimal = {},
        onAddOperator = {},
        onApply = {},
        onDelete = {},
        onClear = {})
}

@Composable
fun CalculatorButtonGrid(
    onAddDigit: (Char) -> Unit,
    onAddDecimal: () -> Unit,
    onAddOperator: (Operator) -> Unit,
    onApply: () -> Unit,
    onDelete: () -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ButtonGridColumn {
            DigitButton('7', onAddDigit)
            DigitButton('4', onAddDigit)
            DigitButton('1', onAddDigit)
            DecimalButton(onAddDecimal)
        }
        ButtonGridColumn {
            DigitButton('8', onAddDigit)
            DigitButton('5', onAddDigit)
            DigitButton('2', onAddDigit)
            DigitButton('0', onAddDigit)
        }
        ButtonGridColumn {
            DigitButton('9', onAddDigit)
            DigitButton('6', onAddDigit)
            DigitButton('3', onAddDigit)
            DeleteButton(onDelete, onClear)
        }
        ButtonGridColumn {
            OperatorButton(Operator.DIVIDE, onAddOperator)
            OperatorButton(Operator.MULTIPLY, onAddOperator)
            OperatorButton(Operator.SUBTRACT, onAddOperator)
            OperatorButton(Operator.ADD, onAddOperator)
            ApplyButton(onApply)
        }
    }
}

@Composable
private fun RowScope.ButtonGridColumn(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier.weight(1f).fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = content
    )
}

@Composable
private fun ColumnScope.CalculatorButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null,
    text: String,
    surfaceColor: Color = MaterialTheme.colors.surface
) {
    Surface(
        color = surfaceColor,
        modifier = modifier
            .clickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
            .weight(1f)
            .fillMaxSize()
    ) {
        Box {
            Text(
                text = text,
                style = typography.h4.copy(fontSize = 36.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
private fun ColumnScope.DigitButton(
    digit: Char,
    onAddDigit: (Char) -> Unit,
    modifier: Modifier = Modifier
) {
    require(digit.isDigit())

    CalculatorButton(
        onClick = { onAddDigit(digit) },
        text = digit.toString(),
        surfaceColor = gray233,
        modifier = modifier
    )
}

@Composable
private fun ColumnScope.DecimalButton(
    onAddDecimal: () -> Unit,
    modifier: Modifier = Modifier
) {
    CalculatorButton(
        onClick = onAddDecimal,
        text = ".",
        surfaceColor = gray233,
        modifier = modifier
    )
}

@Composable
private fun ColumnScope.OperatorButton(
    operator: Operator,
    onAddOperator: (Operator) -> Unit,
    modifier: Modifier = Modifier
) {
    CalculatorButton(
        onClick = { onAddOperator(operator) },
        text = operator.symbol.toString(),
        surfaceColor = gray215,
        modifier = modifier
    )
}

@Composable
private fun ColumnScope.DeleteButton(
    onDelete: () -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    CalculatorButton(
        onClick = onDelete,
        onLongClick = onClear,
        text = "DEL",
        surfaceColor = gray233,
        modifier = modifier
    )
}

@Composable
private fun ColumnScope.ApplyButton(
    onApply: () -> Unit,
    modifier: Modifier = Modifier
) {
    CalculatorButton(
        modifier = modifier,
        onClick = onApply,
        text = "=",
        surfaceColor = holoOrangeLight
    )
}
